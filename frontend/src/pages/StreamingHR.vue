<template>
    <div>
        <div style="margin: 50px">
            <button @click="startBtn"> 방송 시작하기 </button>  
            <button @click="finishBtn" style="float: right"> 방송 종료하기 </button>
        </div>
        <div>
            <video id='hyerin' autoplay></video>
        </div>
    </div>
  
</template>

<script>
export default {
  data(){
    return {

    }
  },
  mounted(){
    
  },
  methods:{
      startBtn(){
        alert("방송시작!")
        navigator.mediaDevices.enumerateDevices()
        .then( devices =>{

            devices= devices.filter( v => (v.kind=="videoinput"));
            console.log("Found "+devices.length +" video devices");
            let lastDevice= devices[devices.length-1];
            console.log("devieces: ", devices)
            devices= devices.filter( v => (v.label.indexOf("back")>0));
            let device= null;
            if( devices.length > 0 ){
                console.log("Taking a 'back' camera");
                device= devices[devices.length-1];
            }
            else{
                console.log("Taking last camera");
                device= lastDevice;
            }
            if( !device ){
                console.log("No devices!");
                return;
            }
            let constraints = {
                audio: false,
                video: {
                    deviceId: { ideal: device.deviceId },
                    width: { ideal: window.innerWidth },
                    height: { ideal: window.innerHeight }
                }
            };
            navigator.mediaDevices.getUserMedia(constraints)
            .then( stream => {
                try {
                    const vv = document.getElementById('hyerin');
                    console.log("try")
                    // this.$el.srcObject = stream;
                    vv.srcObject = stream;
                } catch (error) {
                    console.log("catch")
                    this.$el.srcObject = URL.createObjectURL(stream);
                }
                console.log("DONE");
                
            }).catch( err => {
            console.log(err.name + ": " + err.message);
            })
        }).catch( err => {
            console.log(err.name + ": " + err.message);
        })
    },

    finishBtn(){
        alert("방송 종료!")
        const vv = document.getElementById('hyerin');
        vv.srcObject = null;
    }
  }
}
</script>

<style>
</style>