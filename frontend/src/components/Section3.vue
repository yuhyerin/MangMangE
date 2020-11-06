<template>
  <div class="section s2">
    <div class="container s3">
      <div style="height: 100%">
        <!-- <div style="text-align: center">
            <h2>아이들을 영상으로 만나보세요</h2>
          </div>
        <div class="more-video-button" style="display: flex; justify-content: flex-end;">
          <v-btn
           @click="moveToVideos"
            elevation="10"
            style="background: green; color: white; margin-bottom: 5px;"
          >영상 더보기</v-btn>
        </div>

        <div>
          <object
              type="text/html"
              width="100%"
              height="500"
              data="//www.youtube.com/embed/OR90YT2BtH0"
              allowFullScreen="true"
            ></object>
        </div> -->
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
              data="//www.youtube.com/embed/OR90YT2BtH0"
              allowFullScreen="true"
            ></object>
          </div>
          <div style="width: 50vw">
            <!-- <p style="font-size: xxx-large; vertical-align: top;"><strong>아이들을</strong></p>
            <p style="font-size: xxx-large;"><strong>영상으로</strong></p>
            <p style="font-size: xxx-large;"><strong>만나보세요</strong></p> -->
            <div
              style="display:flex; justify-content: center; height: 100%"
            >
            <div style="margin-bottom: 15px;">
              <p style="font-size: xxx-large; color: white;"><strong>아이들을 영상으로</strong></p>
              <p style="font-size: xxx-large; color: white;"><strong>만나보세요</strong></p>
              <br>
              <p style="font-size: large; margin-bottom: 30px; color: white;"><strong>당신을 기다리고 있어요</strong></p>
              <br>
              <!-- <p style="font-size: xxx-large; color: white;"><strong>만나보세요</strong></p> -->
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
      this.$router.push('/videos')
    }
  },
  computed: {
    videoUrl() {
      return `https://youtube.com/embed/OrxmtDw4pVI?autoplay=1`;
    },
  },
};
</script>

<style>
.section.s2 {
  background-color: rgba(255, 165, 0, 1);
  /* background: darkgray; */
  /* background: #fcf7f0; */
  /* background: #fa8290; */
  /* background: #fc6c7c; */
  padding-top: 30px;
  /* background-color: rgb(244, 236, 225); */
}
.container.s3 {
  /* background: darkgray; */
  /* background: #fcf7f0; */
  /* background-color: rgb(244, 236, 225); */
  /* background: #fa8290; */
  /* background: #fc6c7c; */
  height: 85vh;
  margin-top: 20px;
  border-radius: 20px;
}


</style>