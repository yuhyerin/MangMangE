<template>
  <div>
    <v-row style="margin: 10px 0 5px 20px">
      <h2>총 동영상 {{ videos_cnt }}개</h2>
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
        <v-col cols="5" style="margin-left: 10px">
          <h2 class="video-info" @click="moveToVideoDetail(video.uid)" style="margin-bottom: 10px;">{{ video.title }}</h2>
          <p style="color: gray; font-size: 0.8rem; margin-bottom: 10px">{{ video.writer }} | {{ video.regtime }}</p>
          <!-- content 제한된 글자 수 이내로 표시-->
          <p class="video-info" @click="moveToVideoDetail(video.uid)" style="line-height: 150%;">{{ video.content }}</p>
        </v-col>
    </v-row>
    <!-- <v-btn class="mx-2" fab dark small color="blue-gray" @click="scrollToTop" style="position: fixed; bottom: 70px; right: 15px;">
      <v-icon dark>mdi-chevron-up</v-icon>
    </v-btn>
    <infinite-loading @infinite="infiniteHandler" :identifier="tag" spinner="circles">
      <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading> -->
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios'
import router from '@/router'
import SERVER from '@/api/url'

export default {
  components: {
    InfiniteLoading,
  },
  data() {
    return {
      videos: [],
      videos_cnt: 0
    };
  },
  methods: {
    ...mapActions(["moveToVideoDetail"]),

    getAllVideos() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getAllVideos)
        .then(res => {
          this.videos = [...res.data.VideoList, ...res.data.VideoList]
          this.videos_cnt = this.videos.length
        })
    },

    scrollToTop() {
      scroll(0, 0)
    },

    // moveToVideoDetail(videoIndex) {
    //   this.$router.push(
    //     {
    //       name: 'VideoDetail',
    //       params: {
    //         videoId: videoIndex
    //       }
    //     }
    //   )
    // }
  },
  // mounted() {
  //   console.log(1)
  //   console.log(this.$refs.plyr.player)
    
  // },
  created() {
    this.getAllVideos()
  },
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