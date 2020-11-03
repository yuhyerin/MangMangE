<template>
  <div class="section s1">
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
            style="width: 70vw; height: ; display: flex; align-items: center"
          >
            <object
              type="text/html"
              width="100%"
              height="500"
              data="//www.youtube.com/embed/OR90YT2BtH0"
              allowFullScreen="true"
            ></object>
          </div>
          <div style="width: 30vw">
            <div
              style="display:flex; justify-content: center; align-center; height: 100%"
            >
              <div>더보기</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <div class="container s3">
      <SearchBar />
      <div class="row">
        <div class="col-lg-8">
          <div
            class="embed-responsive embed-responsive-4by3"
            style="margin-top: 30px"
          >
            <iframe
              class="embed-responsive-item"
              :src="videoUrl"
              allowfullscreen
            ></iframe>
          </div>
        </div>
        <div class="col-lg-3">
          <div style="height: 95%; margin-top: 28px; background-color: white">
            <p class="more-videos">
              <strong class="more-videos-strong"> </strong>
            </p>
          </div>
        </div>
        <div class="col-lg-1" style="margin-top: auto; margin-bottom: auto">
          <img
            class="right-arrow"
            src="../assets/image/mainpage/section3-right-arrow.png"
          />
        </div>
      </div>
    </div> -->
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
  computed: {
    videoUrl() {
      return `https://youtube.com/embed/OrxmtDw4pVI?autoplay=1`;
    },
  },
};
</script>

<style>
.container.s3 {
  background-color: rgb(244, 236, 225);
  height: 85vh;
  margin-top: 20px;
  border-radius: 20px;
}

.more-videos {
  cursor: pointer;
  font-size: x-large;
  text-align: center;
  margin-top: auto;
  margin-bottom: auto;
}

.more-videos-strong:hover {
  background-color: darkgray;
}

img.right-arrow {
  width: 25px;
  height: 25px;
}
</style>