<template>
  <div class="chat">
    <v-app>
      <Header/>
        <v-container fluid style="padding-left: 150px; padding-right: 150px;"> 
          <v-row style="padding: 60px 0 10px 20px">
            <h1 style="padding-right: 10px">마이리틀댕댕 TV</h1>
            <img v-if="onair" src="@/assets/image/on-air.png" alt="" width="40px">
            <img v-else src="@/assets/image/off-air.png" alt="" width="40px">
          </v-row>

          <v-row>
            <v-col cols="8" style="padding-top: 0; padding-right: 20px">
              <div class="video-play" style="width: 100%; height: 90%; background-color: black">
                <h2 v-show="!onair" style="color: white; text-align: center; padding-top: 230px;">현재 방송중이 아닙니다.</h2>
                <video v-show="onair" id="localVideo" autoplay playsinline style=""></video>
              </div>
              <div>
              <v-row>
                <v-col>
                  <i class="far fa-user fa-s" style="padding-right: 5px"></i>
                  {{ viewer_number }}명 시청중
                </v-col>

                <v-col style="display: flex; justify-content: flex-end;">
                  <v-dialog v-model="dialog" @click="StartBtn" persistent max-width="600px">
                    <template v-slot:activator="{ on, attrs }">
                      <div v-bind="attrs" v-on="on" class="onair-btn">
                        <v-row style="display: flex; align-items: center; margin: 0;">
                          <v-col style="padding: 0" cols="5">
                            <img src="@/assets/image/startbutton.png" alt="" width="40px" style="margin-top: 3px">
                          </v-col>
                          <v-col style="padding: 0">
                            <h4 v-if="onair" style="display: inline">방송 종료</h4>
                            <h4 v-else style="">방송 시작</h4>
                          </v-col>
                        </v-row>
                      </div>
                    </template>
                    <v-card>
                      <v-card-title>
                        <h2>Live Streaming</h2>
                      </v-card-title>
                      <v-card-text>
                        <v-container>
                          <v-row>
                            <v-col cols="12">
                              <v-text-field
                                v-model="title"
                                label="제목"
                                required
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                              <v-text-field
                                v-model="content"
                                :rules="rules"
                                counter="100"
                                label="내용"
                                required
                              ></v-text-field>
                            </v-col>
                          </v-row>
                        </v-container>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="dialog = false"
                        >
                          Close
                        </v-btn>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="submitStreamingInfo"
                        >
                          Submit
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-col>
              </v-row>
              </div>
            </v-col>
            <!-- 채팅 -->
            <v-col cols="4" style="height: 550px; border: 1px solid black;">
              <div><h3>실시간 채팅</h3></div>
              <div style="height: 82%; margin-top: 7px; border-radius: 10px; background-color: rgba(0, 0, 0, 0.06);">
              </div>
              <div class="chat" style="margin-top: 10px;">
                <textarea class="chat-input" @keyup.enter="sendChat" label="Filled" filled rounded dense append-icon="mdi-map-marker" placeholder="메시지 보내기"></textarea>
                <!-- <span><i class="fas fa-paper-plane submit"></i></span> -->
              </div>
            </v-col>
          </v-row>
          
        </v-container>
    </v-app>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import axios from 'axios'
import io from 'socket.io-client'
import SERVER from '@/api/url'

export default {
  components: {
    Header,
  },
  data() {
    return {
      onair: false,
      dialog: false,
      rules: [v => v.length <= 100 || '최대 100글자'],
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

      title:"",
      content:"",

      newUser:true,
      firstNameIdx: Math.floor(Math.random()*57),
      nickName : '관리자',
      tmpColor : (Math.round((Math.random() * 0x44) + 0xaa)).toString(16) + (Math.round((Math.random() * 0x44) + 0xaa)).toString(16) + (Math.round((Math.random() * 0x44) + 0xaa)).toString(16),

    }
  },
  created(){
  },
  methods: {
    StartBtn() {
      this.submitStreamingInfo();
      // this.connectSocket();
      // this.addListener();
    },
    submitStreamingInfo() {
      this.dialog = false;
      this.onair = !this.onair;
      if(this.onair){
          SERVER.tokenCheck(()=>{
          axios
          .post(
            SERVER.URL + "/user/streaming",
            {
              title: this.title, 
              contents: this.content
            },
            {
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            }
          )
          .then((res)=>
          {
            this.connectSocket();
            this.addListener();
          })
          .catch((err) =>
          console.log('제출 에러', err)
          )
        })
      }else{
        SERVER.tokenCheck(()=>{
          axios
          .get(
            SERVER.URL + "/user/streaming",
            {
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            }
          )
          .then((res)=>
          {
            this.connectSocket();
          })
          .catch((err) =>
          console.log('제출 에러', err)
          )
        })
      }
      
      
    },
    sendChat() {
      alert()
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
  width: 30%;
  padding: 1px 3px 1px 3px;
}
.onair-btn:hover {
  background-color: #eeeeee;
}
.chat {
  width: 100%;
  height: 50px;
  background-color: rgba(0, 0, 0, 0.06);
  border-radius: 10px;
}
.chat-input {
  width: 70%;
  height: 50px;
  padding: 11px 9px 9px 12px;
  resize: none;
}
textarea:focus {
  outline: none;
}
</style>