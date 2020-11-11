<template>
  <div>
    <v-row>
      <v-col>
        <video
          src="@/assets/videos/video1.mp4"
          type="video/mp4"
          controls
          style="width: 100%"
        ></video>
      </v-col>
      <v-col style="padding: 15px">
        <v-row>
          <div style="font-size: 30px; padding-bottom: 5px">동영상 타이틀</div>
          <v-spacer></v-spacer>
          <div v-show="upload">
            <v-btn @click="uploadVideo" small outlined class="ma-2 upload-btn">
              Upload
              <v-icon right dark> mdi-cloud-upload </v-icon>
            </v-btn>
          </div>
        </v-row>
        <v-row>
          <div style="padding-left: 5px; line-height: 150%">
            왼쪽에 있는 동영상은 2차 프로젝트 UCC 애니메이션입니다. <br />밑에
            있는 것들도 마찬가지입니다. <br />모두 다 같은 동영상 <br />스트리밍
            동영상 자리
          </div>
        </v-row>
      </v-col>
    </v-row>
    <hr />
    <v-row>
      <v-col v-for="video in videos" :key="video.uid">
        <vue-plyr>
          <video>
            <source :src="require(`@/assets/videos/${video.filepath}.mp4`)" type="video/mp4"/>
            <track
              kind="captions"
              label="English"
              srclang="en"
              src="captions-en.vtt"
              default
            />
          </video>
        </vue-plyr>
        <h3 class="videoTitle" style="text-align: center; cursor: pointer" @click="moveToVideoDetail(video.uid)">{{ video.title }}</h3>
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
import axios from 'axios';

export default {
  data() {
    return {
      upload: '',
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
          console.log(res.data)
          if(res.data.success)
            this.upload = true;
          else
            this.upload=false;
          console.log(this.upload)
        })
        .catch((err) => {
          console.log(err)
        });
    }
    else{
      this.upload= false;
    }
    this.getVideos()
  },
  methods: {
    videoSeeMore() {
      this.$emit("changeVideo", 2);
    },
    uploadVideo() {
      this.$router.push("/videos/upload");
    },
    moveToVideoDetail(videoIndex) {
      this.$router.push(
        {
          name: 'VideoDetail',
          params: {
            videoId: videoIndex
          }
        }
      )
    },
    getVideos() {
      axios
        .get(SERVER.URL + "/newuser/video/allvideo")
        .then((res) => {
          if(res.data.VideoList.length >= 4) {
            this.videos = res.data.VideoList.slice(-4)
          }
          else {
            this.videos = res.data.VideoList
          }
        
        })
    },
  }
}
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