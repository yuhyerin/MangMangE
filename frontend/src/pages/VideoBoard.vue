<template>
  <div>
    <Header />
    <v-container style="padding-top: 90px">
      <div
        style="
          height: 80vh;
          background-color: rgb(244, 236, 225);
          border-radius: 15px;
          padding: 0px 20px 20px 20px;
        "
      >
        <!--만약 스트리밍중이면-->
        <StreamingVideo v-if="pageTrigger == 0" @changeVideo="changeVideo" />
        <!--스트리밍 X-->
        <NoneStreamingVideo
          v-if="pageTrigger == 1"
          @changeVideo="changeVideo"
        />

        <VideoSeeMore v-if="pageTrigger == 2" @changeVideo="changeVideo" />
        <UploadVideo />
      </div>
    </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import StreamingVideo from "../components/videolist/StreamingVideo";
import NoneStreamingVideo from "../components/videolist/NoneStreamingVideo";
import VideoSeeMore from "../components/videolist/VideoSeeMore";
import UploadVideo from "../components/UploadVideo.vue"

export default {
  components: {
    Header,
    StreamingVideo,
    NoneStreamingVideo,
    VideoSeeMore,
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
  },
};
</script>

<style>
</style>