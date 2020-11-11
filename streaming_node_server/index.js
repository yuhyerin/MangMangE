'use strict';

var os = require('os');
var nodeStatic = require('node-static');
var http = require('http');
var socketIO = require('socket.io');

var fileServer = new(nodeStatic.Server)();
var app = http.createServer(function(req, res) {
  fileServer.serve(req, res);
}).listen(8002);

var io = socketIO.listen(app);
io.sockets.on('connection', function(socket) {

  socket.on('message', function(message) {
    console.log('클라이언트 쪽에서 보낸 message : ', message);
    socket.broadcast.emit('message', message);
  });

  socket.on('create', function(room){
    console.log('방을 만들고 싶다고 요청을 받았습니다. 방이름 : ' + room);
    var clientsInRoom = io.sockets.adapter.rooms[room];
    var numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    console.log('방금만들었기 때문에  방 [ ' + room + ' ] 에는 ' + numClients + ' 명의 시청자만 있습니다. ');
    socket.join(room);
    socket.emit('created', room, socket.id);
  })

  socket.on('join', function(room){
    console.log('방에 참여하고 싶다고 요청을 받았습니다 :)  ' + room);
    var clientsInRoom = io.sockets.adapter.rooms[room];
    var numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    console.log('클라이언트ID [ ' + socket.id + ' ] 는 방 [ ' + room + '] 에 참여했습니다. 현재 참여자 수 : ',numClients);
    io.sockets.in(room).emit('join', room);
    socket.join(room);
    socket.emit('joined', room, socket.id);
    io.sockets.in(room).emit('ready');
  })

  socket.on('create or join', function(room) {
    // log('Received request to create or join room ' + room);
    console.log('방을 만들고 싶다고 or 방에 참여하고 싶다고 요청을 받았습니다 :)  ' + room);

    var clientsInRoom = io.sockets.adapter.rooms[room];
    var numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    console.log('지금 방 [ ' + room + ' ] 은 ' + numClients + ' 명의 참가자가 있습니다. ');

    if (numClients === 0) {
      socket.join(room);
      console.log('클라이언트ID [ ' + socket.id + ' ] 는 방 [ ' + room + '] 을 만들었습니다. ');
      socket.emit('created', room, socket.id);

    } else if (numClients >= 1) {
      console.log('클라이언트ID [ ' + socket.id + ' ] 는 방 [ ' + room + '] 에 참여했습니다. ');
      io.sockets.in(room).emit('join', room);
      socket.join(room);
      socket.emit('joined', room, socket.id);
      io.sockets.in(room).emit('ready');
    } 
    // else { // max two clients
    //   socket.emit('full', room);
    // }
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