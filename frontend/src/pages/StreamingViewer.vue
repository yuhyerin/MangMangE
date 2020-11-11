<template>
    <div>
        <div style="margin: 50px">
        </div>
        <div>
            <h2> 저는 시청자 입니다. remote Video </h2>
            <video id="remoteVideo" autoplay playsinline></video>
        </div>
    </div>
</template>

<script>
import Axios from 'axios'
import io from 'socket.io-client'

export default {
  data(){
    return {
        room: 'hyerin',
        socket: null,
        remoteVideo:null,
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
        
        iceCandidate:null,
        mediaStreamConstraints:{
            audio: false,
            video: true
        },
    }
  },
  mounted(){
      this.roomOpen()
  },
  created(){
      
  },
  methods:{
    roomOpen(){

        this.socket = io.connect('http://localhost:8002')
        alert('방송 시청준비 시작!')

        this.remoteVideo = document.getElementById('remoteVideo');

        this.socket.on('joined', ((room)=>{
            console.log('Vue : 시청자가 룸에 성공적으로 참여하였습니다. ')
            // this.socket.emit('message', 'got user media');
            this.maybeStart();
        }))

        this.socket.on('message',((message) => { 
          console.log('Vue : 시청자가 보낸 message는 ', message);
          if (message === 'got user media') {
                this.maybeStart();
          } else if (message.type === 'offer') {
            
                this.pc.setRemoteDescription(new RTCSessionDescription(message));
                this.doAnswer();
            
          } else if (message.type === 'answer') {
                this.pc.setRemoteDescription(new RTCSessionDescription(message));
          } else if (message.type === 'candidate') {
                console.log("candidate 로 진입! ")
                var candidate = new RTCIceCandidate({
                    sdpMLineIndex: message.label,
                    candidate: message.candidate
                });
                this.pc.addIceCandidate(candidate);
          } else if (message === 'bye') {
                this.handleRemoteHangup();
          }
        }));

        // 방에 참여하겠다고 서버에 알림
        this.socket.emit('join', this.room);
        
            
    },

    maybeStart(){
      
        console.log('Vue : creating peer connection');
        this.createPeerConnection(); // createPeerConnection함수로 peerconnection 을 만들어준다. 
        
        // 시청자가 자기 들어왔다고 알림
        this.socket.emit('message', 'iwantsee')
        
    },

    createPeerConnection(){
      try {
        console.log('Vue : Created RTCPeerConnnection');
        this.pc = new RTCPeerConnection(null);
        this.pc.onicecandidate = function(event){
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
        }
        this.pc.onaddstream = function(event){
              this.remoteStream = event.stream;
              this.remoteVideo.srcObject = this.remoteStream;
        }
        this.pc.onremovestream = function(event){
              console.log('[연결 해제] ', event);
        }
        // pc에 icecandidate, addstream, removestream 이벤트를 등록해준다. 
        // this.pc.onicecandidate = this.handleIceCandidate; // icecandidate는 서로 통신채널을 확립하기 위한 방법.
        // this.pc.onaddstream = this.handleRemoteStreamAdded; // onaddstream은 remote 스트림이 들어오면 발생하는 이벤트. 
        // this.pc.onremovestream = this.handleRemoteStreamRemoved;
        
      } catch (e) {
        console.log('Vue : Failed to create PeerConnection, exception: ' + e.message);
        alert('Cannot create RTCPeerConnection object.');
        return;
      }
    },

    handleIceCandidate(event){
      console.log('Vue : icecandidate event: ', event);
      if (event.candidate) {
        this.sendMessage({
          type: 'candidate',
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate
        });
      } else {
        console.log('Vue : End of candidates.');
      }
    },

    // remoteStream이 들어오면, remoteVideo에 remoteStream을 붙여줍니다.
    handleRemoteStreamAdded(event){
      console.log('Vue : Remote stream added.');
      this.remoteStream = event.stream;
      this.remoteVideo.srcObject = this.remoteStream;
    },

    //
    handleRemoteStreamRemoved(event) {
      console.log('Vue : Remote stream removed. Event: ', event);
    },

    // pc.createOffer를 통해 통신 요청을 하게 됩니다.
    doCall(){
      console.log('Vue : Sending offer to peer');
      this.pc.createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
    },

    //
    createdOffer(description) {
        trace(`Offer from localPeerConnection:\n${description.sdp}`);

        trace('localPeerConnection setLocalDescription start.');
        this.localPeerConnection.setLocalDescription(description)
        .then(() => {
            setLocalDescriptionSuccess(this.localPeerConnection);
        }).catch(setSessionDescriptionError);

        trace('remotePeerConnection setRemoteDescription start.');
        this.remotePeerConnection.setRemoteDescription(description)
        .then(() => {
            setRemoteDescriptionSuccess(this.remotePeerConnection);
        })
        .catch(setSessionDescriptionError);

        trace('remotePeerConnection createAnswer start.');
        this.remotePeerConnection.createAnswer()
        .then(createdAnswer)
        .catch(setSessionDescriptionError);

    },

    //
    createdAnswer(description) {
        trace(`Answer from remotePeerConnection:\n${description.sdp}.`);

        trace('remotePeerConnection setLocalDescription start.');
        this.remotePeerConnection.setLocalDescription(description)
        .then(() => {
            setLocalDescriptionSuccess(this.remotePeerConnection);
        }).catch(setSessionDescriptionError);

        trace('localPeerConnection setRemoteDescription start.');
        this.localPeerConnection.setRemoteDescription(description)
        .then(() => {
            setRemoteDescriptionSuccess(this.localPeerConnection);
        })
        .catch(setSessionDescriptionError)
        
    },

    doAnswer() {
            console.log('Vue : Sending answer to peer.');
            this.pc.createAnswer()
            .then(
                this.setLocalAndSendMessage,
                this.onCreateSessionDescriptionError
            );
    },

    //
    setLocalAndSendMessage(sessionDescription){
      console.log('Vue : setLocalAndSendMessage sending message', sessionDescription);
        this.pc.setLocalDescription(sessionDescription);
        this.socket.emit('message', sessionDescription);
        // 여기서 들어온 sessionDescription은 RTCSessionDescription인데
        // 내부를 까보면 type: "offer" 
        // this.doAnswer();

    },    

    //
    handleCreateOfferError(event){
        console.log('Vue : createOffer() error: ', event);
    },

    //
    onCreateSessionDescriptionError(error){
        console.log('Failed to create session description: ' + error);
    },



       
  },

}
</script>

<style>
</style>