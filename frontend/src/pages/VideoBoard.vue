<template>
  <div>
    <Header/>
    <v-container style="padding-top: 90px">
      <div>
      <!-- <UploadVideo /> -->
        <StreamingVideo v-if="pageTrigger == 0" @changeVideo="changeVideo"/>
        <VideoSeeMore v-if="pageTrigger == 1" @changeVideo="changeVideo" style="margin-left: 70px"/>
      </div>
    </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import StreamingVideo from "../components/videolist/StreamingVideo";
import VideoSeeMore from "../components/videolist/VideoSeeMore";
// import UploadVideo from "../components/UploadVideo.vue"



export default {
  components: {
    Header,
    StreamingVideo,
    VideoSeeMore,
    // UploadVideo
  },
  data() {
    return {
     pageTrigger: 0,
    }
  },
  methods: {
    changeVideo(value) {
      this.pageTrigger = value;
    },
  }
}
</script>

<style>
</style>