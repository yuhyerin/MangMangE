<template>
  <div>
    <div style="margin: 50px"></div>
    <div>
      <h2>저는 시청자 입니다. remote Video</h2>
      <video id="remoteVideo" autoplay playsinline></video>
      <button @click="StartBtn">날 눌러줘</button>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import io from "socket.io-client";

export default {
  data() {
    return {
      room: "hyerin",
      socket: null,
      remoteVideo: null,
      remoteStream: null,
      pc: null,
      onair: null,
    };
  },
  mounted() {},
  created() {},
  methods: {
    StartBtn() {
      this.connectSocket();
      this.addListener();
      this.onair = !this.onair;
    },
    connectSocket() {
      // this.socket = io.connect("http://localhost:8002");
      this.socket = io.connect("https://k3b306.p.ssafy.io:8002");

      alert("방송 봅니다!");
      this.socket.emit("join", this.room);
      this.enteringRoom();
    },
    addListener() {
      // After
      this.socket.on("message", (message) => {
        if (message.type === "offer") {
          this.pc.setRemoteDescription(new RTCSessionDescription(message));
          this.doAnswer();
        } else if (message.type === "answer" && this.pc) {
          this.pc.setRemoteDescription(new RTCSessionDescription(message));
        } else if (message.type === "candidate" && this.pc) {
          this.pc.addIceCandidate(
            new RTCIceCandidate({
              sdpMLineIndex: message.label,
              candidate: message.candidate,
            })
          );
        }
      });
    },
    // ******************************** Call me maybe ******************************** //
    sendMessage(message) {
      this.socket.emit("message", message);
    },
    async setLocalAndSendMessage(sessionDescription) {
      console.log("Create offer Start");
      await this.pc.setLocalDescription(sessionDescription);
      this.sendMessage(sessionDescription);
      console.log("Create offer End");
    },
    handleCreateOfferError(event) {
      console.log("[Error]\n", event);
    },
    onCreateSessionDescriptionError(error) {
      trace("Failed to create session description: " + error.toString());
    },
    doCall() {
      this.pc.createOffer(
        this.setLocalAndSendMessage,
        this.handleCreateOfferError
      );
    },
    doAnswer() {
      this.pc
        .createAnswer()
        .then(
          this.setLocalAndSendMessage,
          this.onCreateSessionDescriptionError
        );
    },
    // ******************************** Ice ******************************** //
    handleIceCandidate(event) {
      if (event.candidate) {
        this.sendMessage({
          type: "candidate",
          label: event.candidate.sdpMLineIndex,
          id: event.candidate.sdpMid,
          candidate: event.candidate.candidate,
        });
      } else {
        console.log("End of candidates");
      }
    },
    // ******************************** LDJ ******************************** //
    enteringRoom() {
      this.pc = new RTCPeerConnection(null);
      this.pc.onicecandidate = this.handleIceCandidate;
      this.pc.onaddstream = this.handleRemoteStreamAdded;
      // this.pc.onaddstream = null;
      this.pc.onremovestream = this.handleRemoteStreamRemoved;
      // this.pc.onremovestream = null;
      // this.pc.addStream(this.localStream);
      this.doCall();
    },
    handleRemoteStreamAdded(event) {
      document.querySelector("video").srcObject = event.stream;
    },
    handleRemoteStreamRemoved(event) {
      console.log("Remote stream removed. Event: ", event);
    },
  },
};
</script>

<style>
</style>