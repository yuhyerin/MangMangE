<template>
  <!-- <div v-infinite-scroll="loadMore"
    infinite-scroll-disabled="loading"
    infinite-scroll-distance="6"> -->
  <div>
    <v-row style="margin: 10px 0 5px 20px">
      <h3>총 동영상 {{ videos_cnt }}개</h3>
    </v-row>
    <v-row v-for="video in videos" :key="video.id" style="padding: 0 20px 0 20px">
      <v-col cols="5">
        <vue-plyr>
          <video poster="poster.png">
            <source
              :src="video.filepath"
              type="video/mp4"
            />
            <track
              kind="captions"
              label="English"
              srclang="en"
              src="captions-en.vtt"
              default
            />
          </video>
        </vue-plyr>
      </v-col>
      <v-col cols="6" style="margin-left: 7px">
        <h2 class="video-info" @click="moveToVideoDetail(video.uid)" style="margin-bottom: 10px;">{{ video.title }}</h2>
        <p style="color: gray; font-size: 0.7rem; margin-bottom: 10px">{{ video.writer }} | {{ video.regtime }}</p>
        <!-- content 제한된 글자 수 이내로 표시-->
        <p class="video-info" @click="moveToVideoDetail(video.uid)" style="line-height: 150%;">{{ video.content }}</p>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import router from '@/router'
import axios from 'axios'
import SERVER from '@/api/url'
import { mapMutations } from 'vuex'

export default {
  data() {
    return {
      videos: [],
      videos_cnt: 0
    };
  },
  methods: {
    moveToVideoDetail(videoIndex) {
      this.$router.push(
        {
          name: 'VideoDetail',
          params: {
            videoId: videoIndex
          }
        }
      )
    }
  },
  mounted() {
    console.log(1)
    console.log(this.$refs.plyr.player)
    
  },
  // created() {
  //   axios.get()
  // },
  created() {
    axios
      .get(SERVER.URL + "/newuser/video/allvideo")
      .then((res) => {
        console.log(res.data.VideoList)
        this.videos = res.data.VideoList
        this.videos_cnt = res.data.VideoList.length
      })
  },
  methods: {
    ...mapMutations(["moveToVideoDetail"]),
  
  }

}
</script>

<style scoped>
.video-info {
  cursor: pointer;
}
.video-info:hover {
  color: #999
}
</style>