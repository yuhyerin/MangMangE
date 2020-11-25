'use strict';

var os = require('os');
var nodeStatic = require('node-static');
var http = require('http');
var socketIO = require('socket.io');

var fileServer = new(nodeStatic.Server)();
var app = http.createServer(function(req, res) {
  fileServer.serve(req, res);
}).listen(8002);

var room_maker = '';
var room_viewer = '';

var io = socketIO.listen(app);
io.sockets.on('connection', function(socket) {
  // **************** start message ******************* //
  socket.on('message', function(message) {
    console.log('into message --- socke.id : ', socket.id ,' message.type : ',message.type)
    if ( message.type === 'offer' ){
      console.log('if offer')
      console.log(room_maker)
      socket.to(room_maker).emit('message', message);
    }else if(message.type === 'answer' ){
      console.log('else if answer')
      socket.to(room_viewer).emit('message', message);
    }else if(message.type === 'candidate' && socket.id===room_maker){
      console.log('else if candidate & im room_maker')
      socket.to(room_viewer).emit('message', message);
    }else if(message.type === 'candidate' && socket.id===room_viewer){
      console.log('else if candidate & im room_viewer')
      socket.to(room_maker).emit('message', message);
    }else{
      console.log('else')
    }
    
  });
  // **************** end message ******************* //

  // **************** start create ******************* //
  socket.on('create', function(room){
    room_maker = socket.id;
    console.log('방만든 사람 : ',room_maker)
    socket.join(room);
    console.log(`들어온 방 [${room}]`);
    socket.emit('created', room, room_maker);
  });
  // **************** end create ******************* //

  // **************** start join ******************* //
  socket.on('join', function(room){
    io.sockets.in(room).emit('join', room);
    socket.join(room);
    let clientsInRoom = io.sockets.adapter.rooms[room];
    let numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    console.log(`An user[${socket.id}] joined room[${room}]\t [${numClients}]`);
    room_viewer = socket.id;
    console.log('방에 들어오고 싶은사람 : ',room_viewer)
    socket.emit('joined', room, socket.id);
    // io.sockets.in(room).emit('ready');
    // io.sockets.in(room).emit('viewer',socket.id);
    // io.sockets.in(room).emit('iwantosee');
    socket.emit('iwantosee');
  });
  // **************** end join ******************* //

});