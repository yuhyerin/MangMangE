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

            this.socket.emit('')
            
        },

       
    },

}
</script>

<style>
</style>