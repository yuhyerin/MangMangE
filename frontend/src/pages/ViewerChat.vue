<template>
  <div>
    <v-row>
      <v-col cols="5" style="padding: 15px; margin-left: 10px;">
        <v-row class="offair" v-show="!onair">
          <div class="live-btn">
            <h4 style="color: white; text-align: center; vertical-align: center;">LIVE</h4>
          </div>
          <div @click="StartBtn" class="start-btn blinking">
            <h2 style="color: white;">라이브 보기</h2>
          </div>
        </v-row>
        <v-row v-show="onair">
          <video
            controls
            id="remoteVideo"
            autoplay playsinline
            style="width: 100%; max-width: 439px; height:295px;"
          ></video>
        </v-row>
      </v-col>
      <v-col style="padding: 15px">
        <v-row>
          <div style="font-size: 30px; padding-bottom: 5px">라이브 스트리밍 중 </div>
          <v-spacer></v-spacer>
          <div v-show="upload">
            <v-btn @click="uploadVideo" small outlined class="ma-2 upload-btn">
              Upload
              <v-icon right dark> mdi-cloud-upload </v-icon>
            </v-btn>
          </div>
        </v-row>
        <v-row>
          <div style="line-height: 150%">
            실시간 라이브 중입니다 :) <br />
            새로온 댕수를 만나러 오세요 ~ <br />
            ^^ <br />
            <br/>
          </div>
        </v-row>
        <v-row style="margin-top: 115px">
          <v-col style="padding-left: 0;">
            <img @click="moveToSupport" src="@/assets/image/kakaoBtn.png" alt="" style="width: 150px; cursor: pointer">
          </v-col>
        </v-row>
        <v-row style="display: flex; justify-content: center; margin-top: 20px;">
            <div id = "chatarea" style="width: 545px; height:295px; background-color: pink;"></div>
        </v-row>
        <v-row style="display: flex; justify-content: center; margin-top: 20px;">
            <input v-model="inputText" id = "msgInput" type = "text" placeholder = "message" /> 
            <button id ="sendMsgBtn" class = "btn-success btn" @click="clickSendMsgBtn">메세지 전송</button>
        </v-row>
      </v-col>
    </v-row>
    <hr />
    
  </div>
</template>

<script>
import router from "@/router";
import SERVER from "@/api/url";
import axios from 'axios';
import io from 'socket.io-client'

export default {
  data() {
    return {
      upload: '',
      videos: [],
      room: 'hyerin',
      socket: null,
      remoteVideo:null,
      remoteStream:null,
      pc: null,
      onair: null,
    };
  },
  created() {
    if (this.$cookies.get("accessToken") != null) {
      axios
        .get(SERVER.URL + "/user/userId", {
          headers: {
            Authorization: this.$cookies.get("accessToken"), //the token is a variable which holds the token
          },
        })
        .then((res) => {
          if(res.data.success)
            this.upload = true;
          else
            this.upload=false;
        })
        .catch((err) => {
          console.log(err)
        });
    }
    else{
      this.upload= false;
    }
    
  },
  methods: {
    videoSeeMore() {
      this.$emit("changeVideo", 1);
    },
    uploadVideo() {
      this.$router.push("/videos/upload");
    },
    moveToVideoDetail(videoIndex) {
      this.$router.push(
        {
          name: 'VideoDetail',
          params: {
            videoId: videoIndex
          }
        }
      )
    },
    getVideos() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getAllVideos)
        .then((res) => {
          if(res.data.VideoList.length > 3) {
            this.videos = res.data.VideoList.slice(-4).reverse()
          }
          else {
            this.videos = res.data.VideoList
          }
        
        })
    },
    StartBtn(){
      this.connectSocket();
      this.addListener();
      this.onair = !this.onair;
    },
    connectSocket(){
      console.log('connectSocket');
      this.socket = io.connect('http://localhost:8002');
      // this.socket = io.connect('https://k3b306.p.ssafy.io:8002');
      this.socket.emit('join', this.room);
      this.enteringRoom();
    },
    addListener(){

      // chat
      this.socket.on("chat message", msg => {
            const newLine = document.createElement("li");
            newLine.innerHTML = msg.split("|USER_COLOR")[0]
            newLine.classList.add("list-group-item");
            newLine.style.backgroundColor = "#" + msg.split("|USER_COLOR")[1];
            chatList.append(newLine);
            // document.getElementById('msg').scrollTop = document.getElementById('msg').scrollHeight;
            setTimeout(function() {window.scrollTo(0, chatList.scrollHeight);},1)
        })
        socket.on("chat history", msg => {
            if(newUser){
                newUser = false;
                msg.forEach(el => {
                    const newLine = document.createElement("li");
                    newLine.innerHTML = el.split("|USER_COLOR")[0]
                    newLine.classList.add("list-group-item");
                    newLine.style.backgroundColor = "#" + el.split("|USER_COLOR")[1];
                    chatList.append(newLine);
                    setTimeout(function() {window.scrollTo(0, chatList.scrollHeight);},1)
                });
            }
        })
        socket.on("join chat", name => {
            const newLine = document.createElement("li");
            newLine.innerHTML = `${name}님이 채팅방에 입장하셨습니다.`;
            newLine.classList.add("list-group-item");
            chatList.append(newLine);
        })
        socket.on("exit chat", name => {
            const newLine = document.createElement("li");
            newLine.innerHTML = `${name}님이 채팅방에서 퇴장하셨습니다.`;
            newLine.classList.add("list-group-item");
            chatList.append(newLine);
        })

      // streaming
      this.socket.on('message',((message) => {
        if (message.type === 'offer') {
          console.log('if message.type = offer');
          this.pc.setRemoteDescription(new RTCSessionDescription(message));
          this.doAnswer();
        } 
        else if (message.type === 'answer' && this.pc) {
          console.log('else if message.type = answer');
          this.pc.setRemoteDescription(new RTCSessionDescription(message));
        } 
        else if (message.type === 'candidate' && this.pc){
          console.log('else if message.type = candidate');
          this.pc.addIceCandidate(new RTCIceCandidate({
            sdpMLineIndex: message.label,
            candidate: message.candidate
          }));
        }
      }));
    },
    // ******************************** Call me maybe ******************************** //
    sendMessage(message) {
      console.log("into sendMessage - ",message.type);
      this.socket.emit('message', message);
    },
    async setLocalAndSendMessage(sessionDescription){
      console.log("into setLocalAndSendMessage");
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
      console.log('into doCall');
      console.log('call createOffer');
      this.pc.createOffer(this.setLocalAndSendMessage, this.handleCreateOfferError);
    },
    doAnswer() {
      console.log('into doAnswer');
      console.log('call createAnswer');
      this.pc.createAnswer ()
      .then(
        this.setLocalAndSendMessage,
        this.onCreateSessionDescriptionError
      );
    },
    // ******************************** Ice ******************************** //
    handleIceCandidate(event){
      console.log('into handleIceCandidate');
      console.log('sendMessage : candidate ');
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
      console.log('into enteringRoom');
      console.log('create RTCPeerConnection Object');
      this.pc = new RTCPeerConnection(null);
      this.pc.onicecandidate = this.handleIceCandidate;
      this.pc.onaddstream = this.handleRemoteStreamAdded;
      this.pc.onremovestream = this.handleRemoteStreamRemoved;
      // doCall이 먼저 호출됨. (handleIceCandidate보다)
      this.doCall();
    },
    handleRemoteStreamAdded(event){
      document.querySelector("video").srcObject = event.stream;
    },
    handleRemoteStreamRemoved(event) {
      this.onair = !this.onair;

    },
    moveToSupport() {
      axios.post(SERVER.URL+'/newuser/kakaoPay',
        {
          videoid: 1
        },  
      )
      .then((res) => {
        async function openPopup() {
          window.open(res.data, 'window_name', 'width=430, height=500, location=no, status=no, scrollbars=yes')
          await function() {
            window.close("http://localhost:3000/video/0")
          }
        }
        openPopup()
      }
      )
      .catch((err) => {
        console.log(err)
      }
      )
    },
  }
}
</script>

<style scoped>
.more-videos {
  padding: 20px;
}
.more-videos:hover {
  color: silver;
  cursor: pointer;
}
.upload-btn {
  font-weight: bold;
  color: rgb(1, 118, 72);
}

.videoTitle:hover {
  cursor: pointer;
}
.offair {
  max-width: 439px; 
  width: 100%; 
  height:295px; 
  border: 1px solid black; 
  background-color: black;
}
.live-btn {
  height: 25px; 
  background-color: red; 
  padding: 2px 7px 2px 7px; 
  border-radius: 13px; 
  margin: 5px;
}
.start-btn {
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 110px;
  cursor: pointer;
  border: 2px solid white;
  padding: 7px;
  border-radius: 8px;
}
.blinking{
	-webkit-animation:blink 1.0s ease-in-out infinite alternate;
  -moz-animation:blink 1.0s ease-in-out infinite alternate;
  animation:blink 1.0s ease-in-out infinite alternate;
}
@-webkit-keyframes blink{
    0% {opacity:0;}
    100% {opacity:1;}
}
@-moz-keyframes blink{
    0% {opacity:0;}
    100% {opacity:1;}
}
@keyframes blink{
    0% {opacity:0;}
    100% {opacity:1;}
}

</style>