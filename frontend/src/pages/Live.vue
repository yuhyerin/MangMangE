<template>
  <div>
    <Header />
    <v-row style="padding: 90px 0 10px 50px">
      <h1 style="padding-right: 10px">마이리틀댕댕 TV</h1>
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
      pc: null,
      localStream: null,
      onair: false,
      viewers: [],
    }
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
      if(this.onair){
        alert('방송 시작합니다!')
        this.socket.emit('create', this.room);
        navigator.mediaDevices.getUserMedia({audio: true, video: true})
        .then(mediaStream => {
          let localVideo = document.querySelector('video');
          this.localStream = mediaStream;
          localVideo.srcObject = mediaStream;
          this.enteringRoom();
        })
        .catch(err => {
          console.log(err);
        });
      }else{
        const stream  = document.querySelector('video').srcObject;
        const tracks = stream.getTracks();
        tracks.forEach(function(track){
          track.stop();
        });
        this.pc.close();
      }
      
    },
    addListener(){
      this.socket.on('created', ((room)=>{
      }));
      // After
      this.socket.on('message',((message) => {
        if (message.type === 'offer' && !this.onair) {
          this.pc.setRemoteDescription(new RTCSessionDescription(message));
          this.doAnswer();
        }
        // else if(message.type === 'offer' && this.onair){
        //   this.doAnswer();
        // }
        else if (message.type === 'answer' && this.pc) {
          this.pc.setRemoteDescription(new RTCSessionDescription(message));
        } 
        else if (message.type === 'candidate' && this.pc){
          this.pc.addIceCandidate(new RTCIceCandidate({
            sdpMLineIndex: message.label,
            candidate: message.candidate
          }));
        }else if(message === 'viewer'){//참여자가 소켓 id 보냈을 때. 
          // alert('사용자 소켓 id : ',message);
          this.viewer_number++;
          this.doCall();
        }
      }));
      this.socket.on('ready', (() => {
        this.viewer_number++;
        this.doCall();
      }));

      this.socket.on('viewer', ((sk) => {
        this.viewer_number++;
        // viewers 배열에 있는지 검사. 
        if(this.viewers.includes(sk)){
          this.doCall();
        }else{
          this.viewers.push(sk);
          this.doCall();
        }
      }));
    },
    // ******************************** Call me maybe ******************************** //
    sendMessage(message) {
      this.socket.emit('message', message);
    },
    async setLocalAndSendMessage(sessionDescription){
      await this.pc.setLocalDescription(sessionDescription);
      this.sendMessage(sessionDescription);
    },
    handleCreateOfferError(event){
      console.log('[Error]\n', event);
    },
    onCreateSessionDescriptionError(error){
      console.log('Failed to create session description: ' + error.toString());
    },
    doCall(){
      this.pc.createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
    },
    doAnswer() {
      this.pc.createAnswer()
      .then(
        this.setLocalAndSendMessage,
        this.onCreateSessionDescriptionError
      );
    },
    // ******************************** Ice ******************************** //
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
    // ******************************** Custom ******************************** //
    enteringRoom(){
      this.pc = new RTCPeerConnection(null);
      this.pc.onicecandidate = this.handleIceCandidate;
      // this.pc.onaddstream = this.handleRemoteStreamAdded;
      this.pc.onaddstream = null;
      // this.pc.onremovestream = this.handleRemoteStreamRemoved;
      this.pc.onremovestream = null;
      this.pc.addStream(this.localStream);
      this.doCall();
    },
    // ******************************** Custom END ******************************** //
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