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
      <div v-show="!onair" style="width: 545px; height:295px; background-color: black;">
        <h3 style="color: white; text-align: center; margin-top:130px;">현재 방송중이 아닙니다.</h3>
      </div>
      
    </v-row>
    <v-row style="display: flex; justify-content: center; margin-top: 20px;">
      <div @click="onAir" class="onair-btn">
        <v-row style="display: flex; align-items: center; margin: 0 0 0 3px;">
          <v-col style="padding: 0" cols="5">
            <img src="@/assets/image/merong.png" alt="" width="40px" style="margin-top:1px">
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

      onair: false,
      socket: null,
      isChannelReady: false,
      isStarted: false,
      localStream: null,
      remoteStream:null,
      pc:null,
      turnReady:null,
      sdpConstraints : {
          offerToReceiveAudio: true,
          offerToReceiveVideo: true
      },
      

      localPeerConnection:null,
      remotePeerConnection:null,
      peerConnection:null,

      localVideo:null,
      remoteVideo:null,

      startButton:null,
      callButton:null,
      hangupButton:null,
        
      iceCandidate:null,
      mediaStreamConstraints:{
          audio: false,
          video: true
      },

    }
  },
  methods: {
    onAir() {

      // 토글 
      this.onair = !this.onair

      if(this.onair == true){
        this.socket = io.connect('http://localhost:8002');
        alert('방송 시작합니다!')

        // 방 만들겠다고 보냄
        this.socket.emit('create', this.room);
          
        // 방이 성공적으로 만들어졌다. 
        this.socket.on('created', ((room)=>{
          console.log('Vue : 성공적으로 룸 개설되었습니다.')
        }));

       

        this.socket.on('log', ((array)=>{
          console.log.apply('Vue : ',console,array);
        }))

        this.socket.on('message',((message) => { 
          console.log('Vue : 클라이언트가 보낸 message는 ', message);
          if (message === 'got user media') {
            this.maybeStart();
          } else if (message.type === 'offer') {
            this.pc.setRemoteDescription(new RTCSessionDescription(message));
            this.doAnswer();
          } else if (message.type === 'answer' && this.isStarted) {
            this.pc.setRemoteDescription(new RTCSessionDescription(message));
          } else if (message.type === 'candidate' && this.isStarted) {
            var candidate = new RTCIceCandidate({
              sdpMLineIndex: message.label,
              candidate: message.candidate
            });
            this.pc.addIceCandidate(candidate);
          } else if (message === 'bye' && this.isStarted) {
                      this.handleRemoteHangup();
          }
        }));

        // element들 가져오기 
        this.localVideo = document.getElementById('localVideo');

        navigator.mediaDevices.getUserMedia(this.mediaStreamConstraints)
        .then(this.gotLocalMediaStream)
        .catch();
      }else{
        alert("방송 종료!")
      }

      
    },

    // 파라미터 mediaStream는 스트리머의 영상Stream을 의미합니다.
    gotLocalMediaStream(mediaStream) {
      console.log("Vue : gotLocalMediaStream : Adding local stream. ")
      this.localStream = mediaStream;
      this.localVideo.srcObject = mediaStream;
      this.socket.emit('message', 'got user media');

    },

    maybeStart(){
      console.log('Vue : maybeStart()호출  isStarted: ', this.isStarted,'  localStream : ', this.localStream,' isChannelReady : ', this.isChannelReady);
      if (!this.isStarted && typeof this.localStream !== 'undefined' && this.isChannelReady) {
        console.log('Vue : creating peer connection');
        this.createPeerConnection(); // createPeerConnection함수로 peerconnection 을 만들어준다. 
        this.pc.addStream(this.localStream); // 나의 peerconnection에 localstream을 붙인다.
        this.isStarted = true;
        
        this.doCall(); // doCall함수로 같은 방에 있는 client에게 rtc 요청
        
      }
    },

    createPeerConnection(){
      try {
        this.pc = new RTCPeerConnection(null);
        // pc에 icecandidate, addstream, removestream 이벤트를 등록해준다. 
        this.pc.onicecandidate = this.handleIceCandidate; // icecandidate는 서로 통신채널을 확립하기 위한 방법.
        this.pc.onaddstream = this.handleRemoteStreamAdded; // onaddstream은 remote 스트림이 들어오면 발생하는 이벤트. 
        this.pc.onremovestream = this.handleRemoteStreamRemoved;
        console.log('Vue : Created RTCPeerConnnection');
      } catch (e) {
        console.log('Vue : Failed to create PeerConnection, exception: ' + e.message);
        alert('Cannot create RTCPeerConnection object.');
        return;
      }
    },

    handleIceCandidate(event){
      console.log('>>>>>>> 프론트 : icecandidate event: ', event);
      if (event.candidate) {
        this.sendMessage({
          type: 'candidate',
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate
        });
      } else {
        console.log('>>>>>>> 프론트 : End of candidates.');
      }
    },

    // remoteStream이 들어오면, remoteVideo에 remoteStream을 붙여줍니다.
    handleRemoteStreamAdded(event){
      console.log('>>>>>>> 프론트 : Remote stream added.');
      this.remoteStream = event.stream;
      console.log('>>>>>>> 프론트 : ',event);
      this.remoteVideo.srcObject = this.remoteStream;
    },

    //
    handleRemoteStreamRemoved(event) {
      console.log('>>>>>>> 프론트 : Remote stream removed. Event: ', event);
    },

    // pc.createOffer를 통해 통신 요청을 하게 됩니다.
    doCall(){
      console.log('Vue : Sending offer to peer');
      this.pc.createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
    },

    //
    doAnswer() {
      console.log('Vue : Sending answer to peer.');
      this.pc.createAnswer()
      .then(
        this.setLocalAndSendMessage,
        this.onCreateSessionDescriptionError
      );
    },

    //
    sendMessage(message) {
      console.log('Vue : Client sending message: ', message);
      console.log('Vue : ',this.socket)
      this.socket.emit('message', message);
    },

    //
    createdOffer(description) {
      trace(`Vue : Offer from localPeerConnection:\n${description.sdp}`);
      trace('Vue : localPeerConnection setLocalDescription start.');
      this.localPeerConnection.setLocalDescription(description)
      .then(() => {
        setLocalDescriptionSuccess(this.localPeerConnection);
      }).catch(setSessionDescriptionError);

      trace('Vue : remotePeerConnection setRemoteDescription start.');
      this.remotePeerConnection.setRemoteDescription(description)
      .then(() => {
        setRemoteDescriptionSuccess(this.remotePeerConnection);
      }).catch(setSessionDescriptionError);

      trace('remotePeerConnection createAnswer start.');
      this.remotePeerConnection.createAnswer()
      .then(createdAnswer)
      .catch(setSessionDescriptionError);
    },

    createdAnswer(description) {
      trace(`Vue : Answer from remotePeerConnection:\n${description.sdp}.`);
      trace('remotePeerConnection setLocalDescription start.');
      this.remotePeerConnection.setLocalDescription(description)
      .then(() => {
        setLocalDescriptionSuccess(this.remotePeerConnection);
      }).catch(setSessionDescriptionError);

      trace('localPeerConnection setRemoteDescription start.');
      this.localPeerConnection.setRemoteDescription(description)
      .then(() => {
        setRemoteDescriptionSuccess(this.localPeerConnection);
      }).catch(setSessionDescriptionError);
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