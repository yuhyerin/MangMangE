<template>
  <div>
    <v-row>
      <v-col>
        <div style="max-width: 545px; height:295px; background-color: black;">
        </div>
      </v-col>
      <v-col style="padding: 15px;">
        <v-row style="display: flex; float: right;">
           <v-btn @click="uploadVideo" small outlined class="ma-2 upload-btn">
            Upload
            <v-icon
              right
              dark
            >
              mdi-cloud-upload
            </v-icon>
          </v-btn>
        </v-row>
        <v-row style="vertical-align: center;" >
          <div style="font-size: 30px; padding-bottom: 5px;">
            현재 스트리밍중인 동영상이 없습니다.
          </div>
        </v-row>
       
      </v-col>
    </v-row>
    <hr>
    <v-row>
      <v-col v-for="video in videos" :key="video.id">
        <vue-plyr>
          <video poster="poster.png">
            <source :src="require(`@/assets/videos/${video.video}.mp4`)" type="video/mp4">
            <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
          </video>  
        </vue-plyr>
        <div style="text-align:center">동영상 제목{{video.id}}</div>
      </v-col>
      <div class="more-videos">
        <i @click="videoSeeMore" class="fas fa-angle-double-right fa-2x"></i>
      </div>
    </v-row>
  </div>
</template>

<script>
import router from '@/router'

export default {
  data() {
    return {
      videos: [
        {
          id: 1,
          video: "video1"
        },
        {
          id: 2,
          video: "video1"
        },
        {
          id: 3,
          video: "video1"
        },
      ]
    }
  },
  mounted() {
    console.log(this.$refs.plyr.player)
  },
  methods: {
    videoSeeMore() {
      this.$emit("changeVideo", 2);
    },
    uploadVideo() {
      console.log("클릭")
      router.push({name: 'UploadVideo'})
    }
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
}
.upload-btn {
  font-weight: bold;
  color: rgb(1, 118, 72);
  float: right;
}
</style>