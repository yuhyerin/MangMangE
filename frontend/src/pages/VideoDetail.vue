<template>
  <div>
    <Header />
    <div class="container" 
      style="
          margin-top: 75px; 
      "
    >
      <div style="margin-top: 20px;">
        <h3 style="color: gray; text-align: center;">
          동물과 사람이 함께하는 삶을 위해 세상을 바꾸는 실천에 함께 해주세요
        </h3>
      </div>
      <div style="width: 70%; height: 50%; margin: 20px auto 0 auto;">
        <vue-plyr>
          <video poster="poster.png">
            <source :src="require(`@/assets/videos/${video.v}.mp4`)" type="video/mp4">
            <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
          </video>  
        </vue-plyr>
      </div>
      <div 
        style="
          margin: 0 auto;
          width: 70%;
          height: 100%;
          background: white
        "
      >
        <div style="
          display: flex; 
          justify-content: space-between;
          margin-top: 10px;
          padding: 0 5px;
          "
        >
          <div>
            <h2>쪼꼬미의 하루</h2>
            <h4>2020-11-08</h4>
          </div>
          <div class="kakaopaySupport" style="display: flex; text-align: center; margin-bottom: 5px;" @click="moveToSupport">
            <h4 style="margin: auto 0px;">후원하기</h4>
            <img src="../assets/image/kakaopay/kakaopay.png">
          </div>
        </div>
        <hr style="background-color: green;">

      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue"
import SERVER from "@/api/url"
import axios from "axios"
import qs from 'qs'

const ADMIN_KEY = process.env.kakaopay_admin_key

export default {
  components: {
    Header,
  },
  data() {
    return {
      video: {
        id: this.$route.params.videoId,
        v: "video1"
      },
      selected: '',
      items: [1000, 5000, 10000, 30000, 50000]
    }
  },
  methods: {
    moveToMainpage() {
      this.$router.push('/videos')
    },

    moveToSupport() {
      axios.post(SERVER.URL+'/newuser/kakaoPay',
        {
          videoid:1
        },  
      )
      .then((res) => {
        console.log('요청성공',res.data)
        window.open(res.data, 'window_name', 'width=430, height=500, location=no, status=no, scrollbars=yes')

      }
      )
      .catch((err) => {
        console.log(1)
        console.log(err)
        console.log('요청실패')
      }
      )
    },
  },

  created() {
    console.log(this.$route.params.videoId)
  },
}
</script>

<style>
.kakaopaySupport {
  cursor: pointer;
}

.selectMoney {
  border: 1px solid green;
}
</style>