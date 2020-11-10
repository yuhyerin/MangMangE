<template>
  <div>
    <Header />
    <div class="container" 
      style="
          margin-top: 75px; 
          background-color: rgb(244, 236, 225);
      "
    >
      <div 
        style="
          margin: 30px auto 30px auto;
          width: 70%;
          height: 100%;
          border: 5px solid rgb(1,118,72);
          border-radius: 15px;
          background: white
        "
      >
        <div style="
          display: flex; 
          justify-content: space-between;
          padding: 0 5px;
          "
        >
          <h2>쪼꼬미의 하루</h2>
          <h4>2020-11-08</h4>
        </div>
        <div style="width: 100%; height: 400px; margin: 20px auto;">
          <vue-plyr>
            <video poster="poster.png">
              <source :src="require(`@/assets/videos/${video.v}.mp4`)" type="video/mp4">
              <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
            </video>  
          </vue-plyr>
        </div>
        <div style="border: 1px solid green; border-radius: 5px;">
          <h3 style="text-align: center;">
            동물과 사람이 함께하는 삶을 위해 세상을 바꾸는 실천에 함께 해주세요
          </h3>
        </div>
      </div>
      <div style="text-align: center;" @click="moveToSupport">
        <h3>후원하기</h3>
        <img src="../assets/image/kakaopay/kakaopay.png">
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
// const qs = require('querystring')

const data = qs.stringify({
  "cid": "TC0ONETIME",
  "partner_order_id": "123",
  "partner_user_id": "kakaodev",
  "item_name": "item_name",
  "quantity": 1,
  "total_amount": 10000,
  "tax_free_amount": 0,
  "approval_url": "https://www.example.com",
  "fail_url": "https://www.example.com",
  "cancel_url": "https://www.example.com",
})

const config = {
  headers: {
    'Authorization': 'KakaoAK edbe89645b8d959181873871c2018383',
    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
  }
}

// const params = new URLSearchParams();

// params.append("cid", "TC0ONETIME")
// params.append("partner_order_id", "123")
// params.append("partner_user_id", "kakaodev")
// params.append("item_name", "applepie")
// params.append("quantity", 1)
// params.append("total_amount", 123)
// params.append("tax_free_amount", 0)
// params.append("approval_url", "http://localhost:3000")
// params.append("fail_url", "http://localhost:3000")
// params.append("tax_free_amount", "http://localhost:3000")


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
    moveToSupport() {
      // window.open("https://mockup-pg-web.kakao.com/v1/67644e3f16977c805c14ac769708eaac628bba819995a98cc1d13d11b459fe1d/info", 
      // 'window_name','width=430,height=500,location=no,status=no,scrollbars=yes')


      axios.post(SERVER.KakaopayURL,{
        form: {
          cid: "TC0ONETIME",
          partner_order_id: "123",
          partner_user_id: "kakaodev",
          item_name : "item_name",
          quantity: 1,
          total_amount: 10000,
          tax_free_amount: 0,
          approval_url: "http://localhost:3000",
          cancel_url: "http://localhost:3000",
          fail_url: "http://localhost:3000",
        }
      }, 
      {
        headers : {
          Authorization: "KakaoAK edbe89645b8d959181873871c2018383",
          "Content-type": "application/x-www-form-urlencoded;charset=utf-8", }
      }
        // data,
        // config
      )
      //   params,
      //   {
      //     headers: {
      //       "Authorization": "KakaoAK edbe89645b8d959181873871c2018383",
      //       "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
      //     }
      //   }
      // )
      .then((res) => {
        console.log(res.data)
      }
      )
      .catch((err) => {
        console.log(1)
        console.log(err)
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
.selectMoney {
  border: 1px solid green;
}
</style>