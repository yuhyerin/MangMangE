<template>
  <div>
    <Header />
    <v-container style="padding-top: 90px">
      <p>동영상 업로드 게시판</p>
      <vue-dropzone id="upload" :options="config"></vue-dropzone>

    </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import vueDropzone from "vue2-dropzone";

export default {
  components: {
    Header,
    vueDropzone
  },
  data() {
    return {
      config: {
        url: "http://localhost:3000/assets/videos" 
      },
      title: '',
      dogID: '',
    }
  },
  methods: {
    afterComplete(file) {
      console.log(file)
    }
  }

}
</script>

<style>

</style>