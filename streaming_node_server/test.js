'use strict';

var os = require('os');
var nodeStatic = require('node-static');
var http = require('http');
var socketIO = require('socket.io');

var fileServer = new(nodeStatic.Server)();
var app = http.createServer(function(req, res) {
  fileServer.serve(req, res);
}).listen(8002);

var onair = false;

var io = socketIO.listen(app);
io.sockets.on('connection', function(socket) {
  console.log('start node!!')
  socket.on('message', function(message) {
    socket.broadcast.emit('message', message);
  });
  socket.on('create', function(room){
    onair = true;
    socket.join(room);
    console.log(`On Air [${room}]`);
    socket.emit('created', room, socket.id);
  });
  socket.on('join', function(room){
    io.sockets.in(room).emit('join', room);
    socket.join(room);
    let clientsInRoom = io.sockets.adapter.rooms[room];
    let numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    console.log(`An user[${socket.id}] joined room[${room}]\t [${numClients}]`);
    socket.emit('joined', room, socket.id);
    // io.sockets.in(room).emit('ready');
    io.sockets.in(room).emit('viewer',socket.id);
  });
  socket.on('ipaddr', function() {
    var ifaces = os.networkInterfaces();
    for (var dev in ifaces) {
      ifaces[dev].forEach(function(details) {
        if (details.family === 'IPv4' && details.address !== '127.0.0.1') {
          socket.emit('ipaddr', details.address);
        }
      });
    }
  });
  socket.on('bye', function(){
    console.log('received bye');
  });
});