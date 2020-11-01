<template>
  <div>
    <Header/>
    <v-container style="padding-top: 90px">
      <div
        style="
          height: 80vh;
          background-color: rgb(244, 236, 225);
          border-radius: 15px;
        "
      >
      </div>
    <!-- <button @click="moveTo('/upload')">동영상</button> -->
    </v-container>
    <!-- <UploadVideo /> -->
  </div>
</template>

<script>
import Header from "../components/Header.vue";
// import UploadVideo from "../components/UploadVideo.vue";

export default {
  components: {
    Header,
    // UploadVideo,
  },
  methods: {
    moveTo(page) {
      this.$router.push(page);
    }
  }
}
</script>

<style>

</style>