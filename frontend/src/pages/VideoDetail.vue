<template>
  <div class="video-detail">
    <Header />
      <v-container fluid style="margin-top: 70px; padding-left: 100px;">
        <v-layout col wrap>
          <v-col cols="8">
            <div class="video-play">
              <!-- :src="require(`@/assets/videos/${video.video}.mp4`)" -->
              <!-- <video
          :src="require(`@/assets/videos/${video.filepath}.mp4`)"
          type="video/mp4"
          controls
          style="width: 100%"
        ></video> -->
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
                <img src="@/assets/image/play.png" alt="" style="width: 10px; margin-right: 3px;">203,202 | 2020.11.10
              </p>
              <p style="padding-bottom: 10px">{{ video.content }}</p>
              <hr>
              <p class="animal-info" @click="moveToAnimal(video.desertion_no)"># {{ video.desertion_no }}</p>
            </div>
          </v-col>
          
          <v-col cols="3" style="padding-left: 30px;">
            <div class="next-videolist">
              <p style="font-size: 1.1rem">다음 동영상</p>
              <v-row v-for="nextvideo in nextVideoList" :key="nextvideo.uid">
                <v-col>
                  <!-- <vue-plyr>
                    <video poster="poster.png">
                      <source :src="require(`@/assets/videos/${nextvideo.filepath}.mp4`)" type="video/mp4"/>
                      <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
                    </video>  
                  </vue-plyr> -->
                  <div style="background-color: black; height: 100px;">썸네일</div>
                </v-col>
                <v-col>
                  <h3>{{ nextvideo.title }}</h3>
                  <p style="color: darkgray; font-family: 'Do Hyeon';">{{ nextvideo.regtime }}</p>
                </v-col>
              </v-row>
              <!-- <ul v-for="nextVideo in nextVideoList" :key="nextVideo.uid">
                <li>
                  {{ nextVideo.title }}
                </li>
              </ul> -->
            </div>
          </v-col>

        </v-layout>
      </v-container>
    <!-- <div class="container" style="margin-top: 75px;">
      <div style="margin-top: 20px;">
        <h3 style="color: gray; text-align: center;">
          동물과 사람이 함께하는 삶을 위해 세상을 바꾸는 실천에 함께 해주세요
        </h3>
      </div>
      <div style="width: 70%; height: 50%; margin: 20px auto 0 auto;">
        <vue-plyr>
          <video poster="poster.png">
            <source :src="video.filepath" type="video/mp4"/>
            <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
          </video>  
        </vue-plyr>
      </div>

      <div style="margin: 0 auto; width: 70%; height: 100%; background: white">
        <div style="display: flex; justify-content: space-between; margin-top: 10px; padding: 0 5px;">
          <div>
            <h2>{{ video.title }}</h2>
            <h4>{{ video.regtime }}</h4>
            <p>{{ video.content }}</p>
          </div>
          <div class="kakaopaySupport" style="display: flex; text-align: center; margin-bottom: 5px;" @click="moveToSupport">
            <h4 style="margin: auto 0px;">후원하기</h4>
            <img src="../assets/image/kakaopay/kakaopay.png">
          </div>
        </div>
        <hr style="background-color: green;">

      </div>
    </div> -->
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
      this.$router.push(
        {
          name: 'AnimalDetail',
          params: {
            desertion_no: animalID
          }
        })
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
        })
    },
    getVideoList() {
      axios.get(SERVER.URL + SERVER.ROUTES.getAllVideos)
      .then(res => {
        // res.data.VideoList.forEach(item => {
        //   if(item.uid !== Number(this.videoID)) {
        //     this.nextVideoList = item
        //     console.log('filter?', this.nextVideoList)
        //   }
        this.nextVideoList = [...res.data.VideoList, ...res.data.VideoList, ...res.data.VideoList]
        // })
        // console.log('다음 영상?', this.nextVideoList)
      })
        // console.log('nextvideo', this.nextVideoList)
        // this.nextVideoList.forEach(function(val){
        //   if (val.uid == this.videoID) {
        //     this.nextVideoList.pop()
        //   }
        // })
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