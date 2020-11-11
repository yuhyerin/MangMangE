<template>
    <div>
        <div style="margin: 50px">
            <!-- <button id="startButton" @click="roomOpen" style="float: left">Start버튼</button>
            <button id="callButton" @click="doCall" style="margin-left: 100px">Call버튼</button>
            <button id="hangupButton" @click="hangup" style="margin-left: 200px">Hang Up버튼</button> -->
        </div>
        <!-- <div>
            <h1> local Video </h1>
            <video id="localVideo" autoplay playsinline></video>
        </div> -->
        <div>
            <h2> remote Video </h2>
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
        isChannelReady: false,
        isStarted: false,

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
  mounted(){
      this.roomOpen()
  },
  created(){
      
  },
  methods:{
        maybeStart(){

            console.log('>>>>>>> 프론트 : maybeStart()호출  isStarted : ', this.isStarted,'isChannelReady : ', this.isChannelReady);
            if (!this.isStarted && this.isChannelReady) {
                console.log('>>>>>>> 프론트 : creating peer connection');
                this.createPeerConnection(); // createPeerConnection함수로 peerconnection 을 만들어준다.
                this.isStarted = true;
                
            }else if(!this.isStarted && this.isChannelReady){
                alert("maybeStart에서 else if!!! ")
            }
        },
        roomOpen(){
            // window.room = prompt("Enter room name:");
            this.socket = io.connect('http://localhost:8002');
            alert("접속시도")

            if (this.room !== '') {
                this.socket.emit('create or join', this.room);
                this.socket.emit('role', 'User')
                console.log('>>>>>>> 프론트 : 일반사용자가 방에 참여하려고 요청합니다. ', this.room);
            }

            this.socket.on('join', ((room)=>{
                console.log('>>>>>>> 프론트 : Another peer made a request to join room ' + room);
                console.log('>>>>>>> 프론트 : This peer is the initiator of room ' + room + '!');
                this.isChannelReady = true;
            }));

            this.socket.on('joined', ((room)=> {
                console.log('>>>>>>> 프론트 : joined: ' + room);
                this.isChannelReady = true;
            }));

            this.socket.on('log', ((array)=> {
                console.log.apply('>>>>>>> 프론트 : ',console, array);
            }));

            this.socket.on('message',((message) => { 
                console.log('>>>>>>> 프론트 : Client received message:', message);
                if (message === 'got user media') {
                    this.maybeStart();
                } else if (message.type === 'offer') {
                    alert('message.type이 offer!!!!')
                    if (!this.isStarted) {
                        this.maybeStart();
                    }
                    this.pc.setRemoteDescription(new RTCSessionDescription(message));
                    this.doAnswer();
                } else if (message.type === 'answer' && this.isStarted) {
                    alert('message.type이 answer!!!!')
                    this.pc.setRemoteDescription(new RTCSessionDescription(message));
                } else if (message.type === 'candidate' && this.isStarted) {
                    alert('message.type이 candidate!!!!')
                    var candidate = new RTCIceCandidate({
                        sdpMLineIndex: message.label,
                        candidate: message.candidate
                    });
                    this.pc.addIceCandidate(candidate);
                } else if (message === 'bye' && this.isStarted) {
                    this.handleRemoteHangup();
                }
            }));

            alert("끝!")
            // element들 가져오기 
            this.remoteVideo = document.getElementById('remoteVideo');

            // 이벤트리스너 등록

            this.remoteVideo.addEventListener("loadedmetadata", ( ()=> {
                console.log('>>>>>>> 프론트 : right: gotStream with width and height:');
            }));

            this.remoteVideo.addEventListener('resize', (() => {
                console.log('>>>>>>> 프론트 : Remote video size changed to');
            }));

            //  navigator.mediaDevices.getUserMedia(this.mediaStreamConstraints)
            // .then(this.gotLocalMediaStream)
            // .catch();

        },

        gotLocalMediaStream(mediaStream) {
            console.log(">>>>>>> 프론트 : gotLocalMediaStream : Adding local stream. ")
            this.sendMessage('got user media');
            
        },
        

        createPeerConnection(){
            try {
                
                this.pc = new RTCPeerConnection(null);
                // pc에 icecandidate, addstream, removestream 이벤트를 등록해준다. 
                this.pc.onicecandidate = this.handleIceCandidate; // icecandidate는 서로 통신채널을 확립하기 위한 방법.
                this.pc.onaddstream = this.handleRemoteStreamAdded; // onaddstream은 remote 스트림이 들어오면 발생하는 이벤트. 
                this.pc.onremovestream = this.handleRemoteStreamRemoved;
                console.log('>>>>>>> 프론트 : Created RTCPeerConnnection');
            } catch (e) {
                console.log('>>>>>>> 프론트 : Failed to create PeerConnection, exception: ' + e.message);
                alert('Cannot create RTCPeerConnection object.');
                return;
            }
        },
        
        //
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

        //
        hangup(){
            console.log('>>>>>>> 프론트 : Hanging up.');
            this.stop();
            this.sendMessage('bye');
        },

        //
        handleRemoteHangup(){
            console.log('>>>>>>> 프론트 : Session terminated.');
            this.stop();
        },

        //
        stop() {
            this.isStarted = false;
            this.pc.close();
            this.pc = null;
        },

        // pc.createOffer를 통해 통신 요청을 하게 됩니다.
        doCall(){
            console.log('>>>>>>> 프론트 : Sending offer to peer');
            this.pc.createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
        },

        //
        doAnswer() {
            console.log('>>>>>>> 프론트 : Sending answer to peer.');
            this.pc.createAnswer()
            .then(
                this.setLocalAndSendMessage,
                this.onCreateSessionDescriptionError
            );
        },


        //
        sendMessage(message) {
            console.log('>>>>>>> 프론트 : Client sending message: ', message);
            this.socket.emit('message', message);
        },

        //
        setLocalAndSendMessage(sessionDescription){
            this.pc.setLocalDescription(sessionDescription);
            console.log('>>>>>>> 프론트 : setLocalAndSendMessage sending message', sessionDescription);
            this.sendMessage(sessionDescription);
        },

        //
        onCreateSessionDescriptionError(error){
            trace('Failed to create session description: ' + error.toString());
        },

        //
        handleCreateOfferError(event){
            console.log('>>>>>>> 프론트 : createOffer() error: ', event);
        },

        handleConnection(event) {
            this.peerConnection = event.target;
            this.iceCandidate = event.candidate;

            if (this.iceCandidate) {
                const newIceCandidate = new RTCIceCandidate(this.iceCandidate);
                const otherPeer = getOtherPeer(this.peerConnection);

                otherPeer.addIceCandidate(newIceCandidate)
                .then(() => {
                    handleConnectionSuccess(this.peerConnection);
                }).catch((error) => {
                    handleConnectionFailure(this.peerConnection, error);
                });

                trace(`${getPeerName(this.peerConnection)} ICE candidate:\n` +
                    `${event.candidate.candidate}.`);
            }
        },

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
                }).catch(setSessionDescriptionError);

            trace('remotePeerConnection createAnswer start.');
            this.remotePeerConnection.createAnswer()
                .then(createdAnswer)
                .catch(setSessionDescriptionError);
        },

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
                }).catch(setSessionDescriptionError);
        },


    },

}
</script>

<style>
</style>