<template>
  <div class="section s2">
    <div class="container s3">
      <div style="height: 100%">
        <div
          style="
            margin: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
          "
        >
          <div
            class="video-container"
            style="width: 50vw; display: flex; align-items: center"
          >
            <object
              type="text/html"
              width="100%"
              height="300"
              data="//www.youtube.com/embed/sP7sB3FKmJI"
              allowFullScreen="true"
            ></object>
          </div>
          <div style="width: 50vw">
            <div
              style="display:flex; justify-content: center; height: 100%"
            >
            <div style="margin-bottom: 25px;">
              <p style="font-size: xxx-large; color: black;"><strong>아이들을 영상으로<br>만나보세요</strong></p>
              <br>
              <p style="font-size: large; margin-bottom: 25px; color: black;"><strong>당신을 기다리고 있어요</strong></p>
              <br>
              <div @click="moveToVideos">
                <v-btn
                  elevation="5"
                  style="background: black; color: white;"
                >영상 더보기</v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import SearchBar from "../components/mainpage/SearchBar.vue";
import axios from "axios";

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY;
const API_URL = "https://www.googleapis.com/youtube/v3/search";

export default {
  name: "Section3",
  components: {
    SearchBar,
  },
  methods: {
    moveToVideos() {
      this.$router.push("/videos");
    },
  },
  computed: {
    videoUrl() {
      return `https://youtube.com/embed/OrxmtDw4pVI?autoplay=1`;
    },
  },
};
</script>

<style scoped>
.section.s2 {
  background: rgb(1, 118, 72);
  padding-top: 30px;
}
.container.s3 {
  height: 85vh;
  margin-top: 20px;
  border-radius: 20px;
}
</style>
