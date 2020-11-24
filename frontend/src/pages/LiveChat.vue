<template>
  <div>
    <Header />
    <v-row style="padding: 90px 0 10px 50px">
      <h1 style="padding-right: 10px">마이리틀댕댕 TV - 채팅 테스트 페이지</h1>
      <img v-if="onair" src="@/assets/image/on-air.png" alt="" width="50px">
      <img v-else src="@/assets/image/off-air.png" alt="" width="50px">
    </v-row>
    <v-row style="display: flex; justify-content: center; align-items: center; background-color: rgb(193, 218, 204); height: 480px">
      <video v-show="onair" id="localVideo" style="width: 545px; height:295px;" autoplay playsinline></video>
      <!-- <video src="@/assets/videos/앞에여분있는먹방자른거.mp4" v-show="onair" id="localVideo" style="width: 545px; height:295px;" autoplay playsinline></video> -->
      <div v-show="!onair" style="width: 545px; height:295px; background-color: black;">
        <h3 style="color: white; text-align: center; margin-top:130px;">현재 방송중이 아닙니다.</h3>
      </div>
    </v-row>
    <!-- <v-row style="display: flex; justify-content: center; margin-top: 20px;">
        <div id = "chatarea" style="width: 545px; height:295px; background-color: pink;"></div>
    </v-row> -->
    <v-row style="display: flex; justify-content: center; margin-top: 20px;">
      <div class="p-5 mb-5" id = "chatarea" style="width: 545px; height:295px; background-color: pink;">
          <ul id="msglist" class="chat-list list-group">
              <li class="list-group-item"> &nbsp;</li>
          </ul>
          <ul class="chat-list list-group">
              <li class="list-group-item"> &nbsp;</li>
          </ul>
      </div>
    </v-row>
    <v-row style="display: flex; justify-content: center; margin-top: 20px;">
        <input v-model="inputText" id = "chat-text" type = "text" placeholder = "message" /> 
        <button id ="sendMsgBtn" class = "btn-success btn" @click="clickSendMsgBtn">메세지 전송</button>
    </v-row>
    <v-row style="display: flex; justify-content: center; margin-top: 20px;">
      <div @click="StartBtn" class="onair-btn">
        <v-row style="display: flex; align-items: center; margin: 0 0 0 3px;">
          <v-col style="padding: 0" cols="5">
            <img src="@/assets/image/startbutton.png" alt="" width="40px" style="margin-top:1px">
          </v-col>
          <v-col style="padding: 0">
            <h4 v-if="onair" style="display: inline">방송 종료</h4>
            <h4 v-else style="">방송 시작</h4>
          </v-col>
        </v-row>
      </div>
    </v-row>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import Axios from 'axios'
import io from 'socket.io-client'

export default {
  components: {
    Header
  },
  data() {
    return {
      room: 'hyerin',
      viewer_number: 0,
      socket: null,
      cur : null,
      localStream: null,
      onair: false,
      viewers: [],
      viewers_pc: {},
      viewers_channel: {},

      chatList:null,
      chatArea :null,
      dataChannel:null,
      sendMsgBtn:null,
      inputText: null,


      newUser:true,
      firstNameIdx: Math.floor(Math.random()*57),
      nickName : '관리자',
      tmpColor : (Math.round((Math.random() * 0x44) + 0xaa)).toString(16) + (Math.round((Math.random() * 0x44) + 0xaa)).toString(16) + (Math.round((Math.random() * 0x44) + 0xaa)).toString(16),

    }
  },
  created(){
  },
  methods: {
    StartBtn(){
      this.onair = !this.onair;
      this.connectSocket();
      this.addListener();
    },
    connectSocket(){
      this.socket = io.connect('http://localhost:8002');
    // this.socket = io.connect('https://k3b306.p.ssafy.io:8002');
      if(this.onair){//방송시작 

        this.socket.emit('create', this.room);
        navigator.mediaDevices.getUserMedia({audio: true, video: true})
        .then(mediaStream => {
          let localVideo = document.querySelector('video');
          this.localStream = mediaStream;
          localVideo.srcObject = mediaStream;
        
        })
        .catch(err => {
          console.log(err);
        });

      }else{//방송종료 
        const stream  = document.querySelector('video').srcObject;
        const tracks = stream.getTracks();
        tracks.forEach(function(track){
          track.stop();
        });
        this.viewers_pc[this.cur].close();
      }
      
    },
    addListener(){
       // chat
      this.socket.on('chat message', msg => {
        // this.sendMsgBtn = document.querySelector('#sendMsgBtn');
        // this.chatArea = document.querySelector('#chatarea');
        // // alert(this.inputText) 
        // var name = "관리자"
        // this.chatArea.innerHTML += name + ": " + this.inputText + "<br />"; 
        
        // //sending a message to a connected peer 
        // this.dataChannel.send(val);
        // this.inputText = "";
        /////////////////////////////////////////
        this.chatList = document.querySelector("#msglist");
        const newLine = document.createElement("li");
        newLine.innerHTML = msg.split("|USER_COLOR")[0]
        newLine.classList.add("list-group-item");
        newLine.style.backgroundColor = "#" + msg.split("|USER_COLOR")[1];
        this.chatList.append(newLine);
        // document.getElementById('msg').scrollTop = document.getElementById('msg').scrollHeight;
        setTimeout(function() {window.scrollTo(0, this.chatList.scrollHeight);},1)
        })
        this.socket.on("chat history", msg => {
            if(this.newUser){
                this.newUser = false;
                msg.forEach(el => {
                    const newLine = document.createElement("li");
                    newLine.innerHTML = el.split("|USER_COLOR")[0]
                    newLine.classList.add("list-group-item");
                    newLine.style.backgroundColor = "#" + el.split("|USER_COLOR")[1];
                    this.chatList.append(newLine);
                    setTimeout(function() {window.scrollTo(0, this.chatList.scrollHeight);},1)
                });
            }
        })
        this.socket.on("join chat", name => {
            const newLine = document.createElement("li");
            newLine.innerHTML = `${name}님이 채팅방에 입장하셨습니다.`;
            newLine.classList.add("list-group-item");
            this.chatList.append(newLine);
        })
        this.socket.on("exit chat", name => {
            const newLine = document.createElement("li");
            newLine.innerHTML = `${name}님이 채팅방에서 퇴장하셨습니다.`;
            newLine.classList.add("list-group-item");
            this.chatList.append(newLine);
        })


      // Streaming
      this.socket.on('created', ((room)=>{
      }));
      this.socket.on('message',((message) => {
        if (message.type === 'offer' && !this.onair) {
          console.log('if message.type = offer');
          this.viewers_pc[this.cur].setRemoteDescription(new RTCSessionDescription(message));
          this.doAnswer();
        }
        else if (message.type === 'answer' && this.viewers_pc[this.cur]) {
          console.log('else if message.type = answer');
          this.viewers_pc[this.cur].setRemoteDescription(new RTCSessionDescription(message));
        } 
        else if (message.type === 'candidate' && this.viewers_pc[this.cur]){
          console.log('else if message.type = candidate');
          this.viewers_pc[this.cur].addIceCandidate(new RTCIceCandidate({
            sdpMLineIndex: message.label,
            candidate: message.candidate
          }));
        }else if(message === 'viewer'){//참여자가 소켓 id 보냈을 때. 
          // alert('사용자 소켓 id : ',message);
          console.log('else if message = viewer');
          this.viewer_number++;
          this.doCall();
        }
      }));

      this.socket.on('viewer', ((socket_number) => {
        console.log('into socket.on(viewer)')
        this.viewer_number++;
        
        var tmp_pc = new RTCPeerConnection(null);
        tmp_pc.onicecandidate = this.handleIceCandidate;
        tmp_pc.onaddstream = null;
        tmp_pc.onremovestream = null;
        tmp_pc.addStream(this.localStream);
        
        this.cur = socket_number;
        this.viewers_pc[socket_number] = tmp_pc;

        // 채팅을 위한 channel 
        var tmp_channel = tmp_pc.createDataChannel(this.cur, {reliable:true});
        this.viewers_channel[this.cur] = tmp_channel;
        this.viewers_channel[this.cur].onerror = function(error){
            console.log("Ooops.... chat... error :", error);
        };

        //when we receive a message from the other peer, display it on the screen 
        this.viewers_channel[this.cur].onmessage = function (event) { 
            this.chatArea.innerHTML += connectedUser + ": " + event.data + "<br />"; 
        };
		
        this.viewers_channel[this.cur].onclose = function () { 
            console.log("data channel is closed"); 
        };  



        this.doCall();
        
      }));

    },
    clickSendMsgBtn(){
        
        // this.sendMsgBtn = document.querySelector('#sendMsgBtn');
        // this.chatArea = document.querySelector('#chatarea');
        // // alert(this.inputText) 
        // var name = "관리자"
        // this.chatArea.innerHTML += name + ": " + this.inputText + "<br />"; 
        
        // //sending a message to a connected peer 
        // // this.dataChannel.send(val);
        // this.inputText = "";

        let msg = `${this.firstNameIdx}|FIRST_NAME_IDX${this.nickName}|USER_NAME${this.inputText}|USER_COLOR${this.tmpColor}`;
        this.socket.emit('chat message', msg);
        this.inputText = "";

    },
    sendMessage(message) {
      this.socket.emit('message', message);
    },
    async setLocalAndSendMessage(sessionDescription){
      console.log('into setLocalAndSendMessage - ', sessionDescription.type)
      await this.viewers_pc[this.cur].setLocalDescription(sessionDescription);
      this.sendMessage(sessionDescription);
    },
    handleCreateOfferError(event){
      console.log('[Error]\n', event);
    },
    onCreateSessionDescriptionError(error){
      console.log('Failed to create session description: ' + error.toString());
    },
    doCall(){
      console.log('into doCall - createOffer ');
      this.viewers_pc[this.cur].createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
    },
    doAnswer() {
      console.log('into doAnswer - createAnswer ');
      this.viewers_pc[this.cur].createAnswer()
      .then(
        this.setLocalAndSendMessage,
        this.onCreateSessionDescriptionError
      );
    },
    
    handleIceCandidate(event){
      if (event.candidate) {
        this.sendMessage({
          type: 'candidate',
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate
        });
      } else {
      }
    },
  }
}
</script>

<style scoped>
.onair-btn {
  cursor: pointer;
  border: solid black;
  border-radius: 7px;
  width: 9%;
  padding: 1px 3px 1px 3px;
}
.onair-btn:hover {
  background-color: #eeeeee;
}
</style>