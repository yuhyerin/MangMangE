<template>
  <div class="video-detail">
    <Header />
      <v-container fluid style="margin-top: 70px; padding-left: 100px;">
        <v-layout col wrap>
          <v-col cols="8">
            <div class="video-play">
              <vue-plyr>
                <video>
                  <source :src="require(`@/assets/videos/${video.filepath}.mp4`)" type="video/mp4"/>
                  <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
                </video>  
              </vue-plyr>
            </div>
            <v-row>
              <v-col style="padding-bottom: 0">
                <h2 style="padding-left: 5px;">{{ video.title }}</h2>
              </v-col>
              <v-col style="display: flex; justify-content: flex-end">
                  <img @click="moveToSupport" src="@/assets/image/kakaoBtn.png" alt="" style="width: 150px; cursor: pointer">
              </v-col>
            </v-row>
            <div class="video-info" style="padding-left: 5px;">
              <p style="color: darkgray; padding-bottom: 10px">
                <img src="@/assets/image/play.png" alt="" style="width: 10px; margin-right: 3px;">203,202 | {{ video.regtime }}
              </p>
              <p style="padding-bottom: 10px">{{ video.content }}</p>
              <hr>
              <p class="animal-info" @click="moveToAnimal(video.desertion_no)"># {{ video.desertion_no }}</p>
            </div>
          </v-col>
          
          <v-col cols="3" style="padding-left: 30px;">
            <div class="next-videolist">
              <p style="font-size: 1.1rem">추천 동영상</p>
              <v-row v-for="nextvideo in nextVideoList" :key="nextvideo.uid">
                <v-col style="padding-right: 8px;">
                  <vue-plyr>
                    <video>
                      <source :src="require(`@/assets/videos/${nextvideo.filepath}.mp4`)" type="video/mp4" height="80px"/>
                      <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
                    </video>  
                  </vue-plyr>
                  <!-- <div style="background-color: black; height: 100px;">썸네일</div> -->
                </v-col>
                <v-col>
                  <h4>{{ nextvideo.title }}</h4>
                  <p style="color: darkgray; font-size: 0.8rem;">{{ nextvideo.regtime }}</p>
                </v-col>
              </v-row>
            </div>
          </v-col>

        </v-layout>
      </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue"
import SERVER from "@/api/url"
import axios from "axios"
import qs from 'qs'
import { mapActions } from 'vuex'

const ADMIN_KEY = process.env.kakaopay_admin_key

export default {
  components: {
    Header,
  },
  data() {
    return {
      videoID: this.$route.params.videoId,
      video: [],
      nextVideoList: [],
    }
  },
  created() {
    this.getVideo()
    this.getVideoList()
  },
  methods: {
    moveToMainpage() {
      this.$router.push('/videos')
    },
    moveToSupport() {
      axios.post(SERVER.URL+'/newuser/kakaoPay',
        {
          videoid: 1
        },  
      )
      .then((res) => {
        console.log('요청성공', res.data)
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
        console.log(1)
        console.log(err)
        console.log('요청실패')
      }
      )
    },
    moveToAnimal(animalID) {
      this.$router.push(`/animaldetail/${animalID}`)
    },
    getVideo() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getVideo, {
          params: {
            uid: this.videoID
          }
        })
        .then(res => {
          this.video = res.data.VideoDetail
          // var item = this.video
          var someday = new Date(this.video.regtime)
          var year = someday.getFullYear()
          var month = someday.getMonth() + 1
          var date = someday.getDate()
          var regTime = year + '-' + month + '-' + date
          this.video.regtime = regTime
        })
    },
    getVideoList() {
      axios.get(SERVER.URL + SERVER.ROUTES.getAllVideos)
      .then(res => {
        res.data.VideoList.forEach(item => {
          if(item.uid !== Number(this.videoID)) 
          {
            this.nextVideoList = item
          }
        // this.nextVideoList = res.data.VideoList
        // for (let i = 0; i < this.nextVideoList.length; i++) {
        //   var item = this.nextVideoList[i]
        //   var someday = new Date(item.regtime)
        //   var year = someday.getFullYear()
        //   var month = someday.getMonth() + 1
        //   var date = someday.getDate()
        //   var regTime = year + '-' + month + '-' + date
        //   item.regtime = regTime
        })
      })
    }
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
.animal-info {
  cursor: pointer;
  color: red; 
  font-size: 1.2rem
}
</style>