<template>
  <div>
    <div
      class="backBtn"
      style="position: fixed; top: 90px; left: 5%"
      @click="moveToBack"
    >
      <div style="display: flex; justify-content: center; height: 50px">
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon x-large>mdi-arrow-left </v-icon>
        </div>
      </div>
      <div>돌아가기</div>
    </div>
    <v-row style="margin: 10px 0 5px 10px">
      <h2 style="margin-bottom: 10px">총 동영상 {{ videos_cnt }}개</h2>
    </v-row>
    <v-row>
      <v-col
        cols="4"
        v-for="video in videos"
        :key="video.id"
        style="padding: 0 20px 20px 20px"
      >
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: black;
          "
        >
          <video
            :src="`https://k3b306.p.ssafy.io/allVideos/${video.filepath}`"
            type="video/mp4"
            controls
            style="max-height: 200px; width: 100%"
          ></video>
        </div>
        <h2
          class="video-info"
          @click="moveToVideoDetail(video.uid)"
          style="margin-bottom: 5px"
        >
          {{ video.title }}
        </h2>
        <p style="color: gray; font-size: 0.9rem; margin-bottom: 10px">
          <i class="far fa-user fa-xs" style="margin-right: 5px"></i
          >{{ video.writer }} |
          <i class="fas fa-calendar-day fa-xs" style="margin-left: 5px"></i>
          {{ video.regtime }}
        </p>
      </v-col>
      <!-- <v-col cols="5" style="margin-left: 15px;">
        <h2 class="video-info" @click="moveToVideoDetail(video.uid)" style="margin-bottom: 10px;">{{ video.title }}</h2>
        <p style="color: gray; font-size: 0.9rem; margin-bottom: 10px">
          <i class="far fa-user fa-xs" style="margin-right: 5px;"></i>{{ video.writer }} |
          <i class="fas fa-calendar-day fa-xs" style="margin-left: 5px"></i> {{ video.regtime }}
        </p>
        <p class="video-info" @click="moveToVideoDetail(video.uid)" style="line-height: 150%;">{{ video.content }}</p>
      </v-col> -->
    </v-row>
    <ScrollToTop />
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from "axios";
import router from "@/router";
import SERVER from "@/api/url";
import ScrollToTop from "@/components/ScrollToTop.vue";

export default {
  components: {
    ScrollToTop,
  },
  data() {
    return {
      videos: [],
      videos_cnt: 0,
    };
  },
  methods: {
    ...mapActions(["moveToVideoDetail"]),

    getAllVideos() {
      axios.get(SERVER.URL + SERVER.ROUTES.getAllVideos).then((res) => {
        this.videos = res.data.VideoList.reverse();
        this.videos_cnt = this.videos.length;
        for (let i = 0; i < this.videos.length; i++) {
          var item = this.videos[i];
          var someday = new Date(item.regtime);
          var year = someday.getFullYear();
          var month = someday.getMonth() + 1;
          var date = someday.getDate();
          var regTime = year + "-" + month + "-" + date;
          item.regtime = regTime;
        }
      });
    },
    scrollToTop() {
      scroll(0, 0);
    },
    moveToBack() {
      this.$emit("changeVideo", 0);
    },
  },
  created() {
    this.getAllVideos();
  },
};
</script>

<style scoped>
.video-info {
  cursor: pointer;
}
.video-info:hover {
  color: #999;
}
</style>