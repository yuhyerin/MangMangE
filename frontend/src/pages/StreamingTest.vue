<template>
    <div>
        <h1> 한페이지에 스트리머, 시청자 구현 </h1>
        <div style="margin: 50px">
            <button id="startButton" @click="roomOpen" style="float: left">Start버튼</button>
            <button id="callButton" @click="doCall" style="margin-left: 100px">Call버튼</button>
            <button id="hangupButton" @click="hangup" style="margin-left: 200px">Hang Up버튼</button>
        </div>
        <div>
            <h1> 나  </h1>
            <video id="localVideo" autoplay playsinline></video>
        </div>
        <div>
            <h2> 상대방 </h2>
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
        socket: null,

        room: 'testroom',

        isChannelReady: false,
        isInitiator: false,
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
  mounted(){
      
  },
  methods:{

      // RTCPeerConnection을 이용해서 peer connection 하기 
      // isStarted는 maybeStart 함수가 처음 실행될때 true가 된다.
        maybeStart(){
            if (!this.isStarted && typeof this.localStream !== 'undefined' && this.isChannelReady) {
                this.isStarted = true;
                // createPeerConnection함수로 peerconnection 을 만들어준다. 
                this.createPeerConnection();
                // 나의 peerconnection에 localstream을 붙인다.
                this.pc.addStream(this.localStream); 
                
                if (this.isInitiator) { //방을 만든사람이면 
                // doCall함수로 같은 방에 있는 client에게 rtc 요청
                    this.doCall(); 
                }
            }
        },

        roomOpen(){
            // this.socket = io.connect('https://k3b306.p.ssafy.io:8002');
            this.socket = io.connect('http://localhost:8002');
            // 방을 만들겠다고 or 참여하겠다고 서버에 알림
            this.socket.emit('create or join', this.room);
            
            // 방을 성공적으로 만들었으면 여기로 
            this.socket.on('created', ((room)=>{
                console.log('Vue : Created room [ ',room,' ]');
                this.isInitiator = true;
            }));

            // 
            this.socket.on('join', ((room)=>{
                console.log('Vue : Another peer made a request to join room ' + room);
                this.isChannelReady = true;
            }));

            // 방에 성공적으로 참여했으면 여기로 
            this.socket.on('joined', ((room)=> {
                console.log('>>>>>>> 프론트 : joined: ' + room);
                this.isChannelReady = true;
            }));


            this.socket.on('message',((message) => {
                console.log('message : ', message)
                if (message === 'got user media') {
                    this.maybeStart();
                } else if (message.type === 'offer') {
                    
                    if (!this.isInitiator && !this.isStarted) {
                        // 처음 들어온 참여자면 peerConnection부터 생성..
                        this.maybeStart();
                    }
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

            alert("끝!")
            // element들 가져오기 
            this.localVideo = document.getElementById('localVideo');
            this.remoteVideo = document.getElementById('remoteVideo');
            this.startButton = document.getElementById('startButton');
            this.callButton = document.getElementById('callButton');
            this.hangupButton = document.getElementById('hangupButton');

            navigator.mediaDevices.getUserMedia(this.mediaStreamConstraints)
            .then(this.gotLocalMediaStream)
            .catch();

        },
      
        // 전달받은 streamd을 localVideo에 붙인다.
        gotLocalMediaStream(mediaStream) {
            this.localStream = mediaStream;
            this.localVideo.srcObject = mediaStream;
            this.sendMessage('got user media');
            this.callButton.disabled = false;  // Enable call button.
            
            // 방을 최초로 만든 사람인 경우
            if(this.isInitiator){
                this.maybeStart();
            }
        },
        

        createPeerConnection(){
            var pcConfig = {
                'iceServers':[
                    {
                        urls: 'stun:stun.l.google.com:19302'
                    },
                    {
                        urls: "turn:numb.viagenie.ca",
                        credential: "muazkh",
                        username: "webrtc@live.com"
                    }
                ]
            }
            try {
                // peerconnection을 만든다.
                this.pc = new RTCPeerConnection(pcConfig);
                // pc에 icecandidate, addstream, removestream 이벤트를 등록해준다. 
                // icecandidate는 서로 통신채널을 확립하기 위한 방법.
                this.pc.onicecandidate = this.handleIceCandidate; 
                // onaddstream은 remote 스트림이 들어오면 발생하는 이벤트. 
                this.pc.onaddstream = this.handleRemoteStreamAdded; 
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
            console.log('Remote stream added.');
            this.remoteStream = event.stream;
            this.remoteVideo.srcObject = this.remoteStream;
        },

        //
        handleRemoteStreamRemoved(event) {
            console.log('Remote stream removed. Event: ', event);
        },

        //
        hangup(){
            console.log('Hanging up.');
            this.stop();
            this.sendMessage('bye');
        },

        //
        handleRemoteHangup(){
            console.log('>>>>>>> 프론트 : Session terminated.');
            this.stop();
            this.isInitiator = false;
        },

        //
        stop() {
            this.isStarted = false;
            this.pc.close();
            this.pc = null;
        },

        // pc.createOffer를 통해 통신 요청을 하게 됩니다.
        doCall(){
            console.log('Sending offer to peer');
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
            console.log('Client sending message: ', message);
            this.socket.emit('message', message);
        },

        //
        setLocalAndSendMessage(sessionDescription){
            this.pc.setLocalDescription(sessionDescription);
            console.log('setLocalAndSendMessage sending message', sessionDescription);
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