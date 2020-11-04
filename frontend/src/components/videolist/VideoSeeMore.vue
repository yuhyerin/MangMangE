<template>
  <div>
    <v-row>
      <v-col>
        <video src="@/assets/videos/video1.mp4" type="video/mp4" controls></video>
      </v-col>
      <v-col style="padding: 15px">
        <v-row>
          <div style="font-size:30px; padding-bottom:5px;">동영상 타이틀</div>
        </v-row>
        <v-row>
          <div style="padding-left: 5px; line-height:150%">왼쪽에 있는 동영상은 2차 프로젝트 UCC 애니메이션입니다. 
            <br>밑에 있는 것들도 마찬가지입니다.
            <br>모두 다 같은 동영상
            <br>스트리밍 동영상 자리
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
    </v-row>
  </div>
</template>

<script>
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
        {
          id: 4,
          video: "video1"
        },
                {
          id: 5,
          video: "video1"
        },
                {
          id: 6,
          video: "video1"
        },
      ]
    }
  },
  mounted() {
    console.log(this.$refs.plyr.player)
  },

}
</script>

<style scoped>

</style>