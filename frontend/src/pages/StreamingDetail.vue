<template>
  <div class="chat">
    <v-app>
      <Header/>
        <v-container fluid style="padding-left: 150px; padding-right: 150px;"> 
          <v-row style="margin-top: 90px;">
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
                <v-col style="display: flex; justify-content: flex-end">
                  <img
                    @click="moveToSupport"
                    src="@/assets/image/kakaoBtn.png"
                    alt=""
                    style="width: 150px; cursor: pointer"
                  />
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
import SERVER from "@/api/url";
import axios from 'axios';
import io from 'socket.io-client';

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
      chatMsg: '',
    }
  },
  methods: {
    StartBtn() {
      this.submitStreamingInfo();
      // this.connectSocket();
      // this.addListener();
    },
    submitStreamingInfo() {

      this.onair = !this.onair;
      this.title = ''
      this.content = ''
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
      this.socket.on('created', ((room)=>{
      }));
      // After
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
        this.doCall();
        
      }));
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

    moveToSupport() {
      axios
        .post(SERVER.URL + "/newuser/kakaoPay", {
          videoid: 1,
        })
        .then((res) => {
          async function openPopup() {
            window.open(
              res.data,
              "window_name",
              "width=430, height=500, location=no, status=no, scrollbars=yes"
            );
            await function () {
              window.close(SERVER.URL + "/video/0");
            };
          }
          openPopup();
        })
        .catch((err) => {
          console.log(err);
        });
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