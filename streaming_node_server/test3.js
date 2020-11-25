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
var room_maker = '';
var room_viewer = '';
const firstName = [
    "가냘픈", "가엾은", "거친", "게으른", "고운", "귀여운", "기쁜", "깨끗한", "나쁜", "날카로운", "너그러운", "느린", "동그란", "딱한", "뜨거운", "멋진", "메마른", "못난", "못된", "못생긴", "무서운", "무거운", "미끈미끈한", "미운", "밝은", "반가운", "붉은", "비싼", "빠른", "빨간", "뽀얀", "새로운", "성가신", "센", "수다스러운", "수줍은", "슬픈", "쌀쌀맞은", "아름다운", "아픈", "안쓰러운", "약은", "어두운", "어린", " 언짢은", "예쁜", "외로운", "우스운", "작은", "잘난", "잘생긴", "재미있는", "젊은", "조그만", "즐거운", "지혜로운", "한결같은"
] // 57개
const history = [];

var io = socketIO.listen(app);
io.sockets.on('connection', function(socket) {
    
    // chatroom history
    socket.emit('chat history', history);
    // message 전송
    socket.on('chat message', msg => {
        console.log(msg);
        let tmp = msg.split("|FIRST_NAME_IDX");
        let idx = Number(tmp[0]);
        tmp = tmp[1].split("|USER_NAME");
        let name = tmp[0];
        let message = tmp[1];
        history.push(`[${firstName[idx]} ${name}]: ${message}`);
        socket.emit('chat message', `[${firstName[idx]} ${name}]: ${message}`);
    })
    socket.on("join chat", msg => {
        socket.emit("join chat", msg);
    })
    socket.on("exit chat", msg => {
        socket.emit("exit chat", msg);
    })




  socket.on('message', function(message) {
    if(socket.id === room_maker){
        console.log('방장이 보낸 메세지')
        socket.to(room_viewer).emit('message', message);
    }else{
        socket.to(room_maker).emit('message', message);
        console.log('시청자가 보낸 메세지')
    }
    // socket.broadcast.emit('message', message);
  });
  socket.on('create', function(room){
    onair = true;
    room_maker = socket.id;
    socket.join(room);
    console.log(`On Air [${room}]`);
    socket.emit('created', room, socket.id);
  });
  // *********** join *********** //
  socket.on('join', function(room){
    room_viewer = socket.id;
    io.sockets.in(room).emit('join', room);
    socket.join(room);
    let clientsInRoom = io.sockets.adapter.rooms[room];
    let numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
    console.log(`An user[${socket.id}] joined room[${room}]\t [${numClients}]`);
    // socket.emit('joined', room, socket.id);
    // io.sockets.in(room).emit('ready');
    io.sockets.in(room).emit('joined',socket.id);
    io.sockets.in(room).emit('viewer',socket.id);
  });
  
  socket.on('bye', function(){
    console.log('received bye');
  });

});