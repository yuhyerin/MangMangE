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
          <v-btn @click="uploadVideo" small outlined class="ma-2 upload-btn">
            Upload
            <v-icon right dark> mdi-cloud-upload </v-icon>
          </v-btn>
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
      <v-col v-for="video in videos" :key="video.id">
        <vue-plyr>
          <video poster="poster.png">
            <source
              :src="require(`@/assets/videos/${video.video}.mp4`)"
              type="video/mp4"
            />
            <track
              kind="captions"
              label="English"
              srclang="en"
              src="captions-en.vtt"
              default
            />
          </video>
        </vue-plyr>
        <div style="text-align: center">동영상 제목{{ video.id }}</div>
      </v-col>
      <div class="more-videos">
        <i @click="videoSeeMore" class="fas fa-angle-double-right fa-2x"></i>
      </div>
    </v-row>
  </div>
</template>

<script>
import router from "@/router";
export default {
  data() {
    return {
      videos: [
        {
          id: 1,
          video: "video1",
        },
        {
          id: 2,
          video: "video1",
        },
        {
          id: 3,
          video: "video1",
        },
      ],
    };
  },
  mounted() {
    console.log(this.$refs.plyr.player);
  },
  methods: {
    videoSeeMore() {
      this.$emit("changeVideo", 2);
    },
    uploadVideo() {
      console.log("클릭");
      router.push({ name: "UploadVideo" });
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
}
.upload-btn {
  font-weight: bold;
  color: rgb(1, 118, 72);
}
</style>