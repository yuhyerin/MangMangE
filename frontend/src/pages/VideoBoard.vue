<template>
  <div>
    <Header />
    <v-container style="padding-top: 90px">
      <div>
        <!-- <UploadVideo /> -->
        <StreamingVideo v-if="pageTrigger == 0" @changeVideo="changeVideo" />
        <VideoSeeMore v-if="pageTrigger == 1" @changeVideo="changeVideo" />
      </div>
    </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import StreamingVideo from "../components/videolist/StreamingVideo";
import VideoSeeMore from "../components/videolist/VideoSeeMore";
import { mapMutations, mapState } from "vuex";
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
    };
  },
  computed: {
    ...mapState(["eventListener"]),
  },
  created() {
    if (this.eventListener == 5) {
      this.changeVideo(1);
    } else {
      this.changeVideo(0);
    }
    this.setEventListener(0);
  },
  methods: {
    ...mapMutations(["setEventListener"]),
    changeVideo(value) {
      this.pageTrigger = value;
    },
  },
};
</script>

<style>
</style>