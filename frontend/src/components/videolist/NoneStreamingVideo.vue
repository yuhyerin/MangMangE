<template>
  <div>
    <v-row>
      <v-col>
        <div
          style="max-width: 545px; height: 295px; background-color: black"
        ></div>
      </v-col>
      <v-col style="padding: 15px">
        <v-row style="display: flex; float: right">
          <v-btn @click="uploadVideo" small outlined class="ma-2 upload-btn">
            Upload
            <v-icon right dark> mdi-cloud-upload </v-icon>
          </v-btn>
        </v-row>
        <v-row style="vertical-align: center">
          <div style="font-size: 30px; padding-bottom: 5px">
            현재 스트리밍중인 동영상이 없습니다.
          </div>
        </v-row>
      </v-col>
    </v-row>
    <hr />
    <v-row>
      <v-col v-for="video in videos" :key="video.uid">
        <video
          :src="`https://k3b306.p.ssafy.io/allVideos/${video.filepath}`"
          type="video/mp4"
          controls
          style="max-height: 150px; width: 100%; height: 100%"
        ></video>
        <!-- <vue-plyr>
          <video>
            <source :src="require(`@/assets/videos/${video.filepath}`)"/>
            <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
          </video>  
        </vue-plyr> -->
        <h3
          class="videoTitle"
          style="text-align: center; cursor: pointer"
          @click="moveToVideoDetail(video.uid)"
        >
          {{ video.title }}
        </h3>
      </v-col>
      <div class="more-videos">
        <i @click="videoSeeMore" class="fas fa-angle-double-right fa-2x"></i>
      </div>
    </v-row>
  </div>
</template>

<script>
import router from "@/router";
import SERVER from "@/api/url";
import axios from "axios";

export default {
  data() {
    return {
      upload: "",
      videos: [],
    };
  },
  created() {
    if (this.$cookies.get("accessToken") != null) {
      axios
        .get(SERVER.URL + "/user/userId", {
          headers: {
            Authorization: this.$cookies.get("accessToken"), //the token is a variable which holds the token
          },
        })
        .then((res) => {
          if (res.data.success) this.upload = true;
          else this.upload = false;
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      this.upload = false;
    }
    this.getVideos();
  },
  methods: {
    videoSeeMore() {
      this.$emit("changeVideo", 2);
    },
    uploadVideo() {
      this.$router.push("/videos/upload");
    },
    moveToVideoDetail(videoIndex) {
      this.$router.push({
        name: "VideoDetail",
        params: {
          videoId: videoIndex,
        },
      });
    },
    getVideos() {
      axios.get(SERVER.URL + SERVER.ROUTES.getAllVideos).then((res) => {
        if (res.data.VideoList.length > 3) {
          this.videos = res.data.VideoList.slice(-4);
        } else {
          this.videos = res.data.VideoList;
        }
      });
    },
  },
};
</script>

<style scoped>
.more-videos {
  padding: 20px;
  display: flex;
  align-items: center;
}
.more-videos:hover {
  color: silver;
  cursor: pointer;
}
.upload-btn {
  font-weight: bold;
  color: rgb(1, 118, 72);
}

div.videoTitle:hover {
  cursor: pointer;
}
</style>