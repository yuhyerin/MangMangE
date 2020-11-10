<template>
  <!-- <div v-infinite-scroll="loadMore"
    infinite-scroll-disabled="loading"
    infinite-scroll-distance="6"> -->
  <div>
    <v-row style="margin: 10px 0 5px 20px">
      <h3>총 동영상 ??개</h3>
    </v-row>
    <v-row v-for="video in videos" :key="video.id" style="padding: 0 20px 0 20px">
      <v-col cols="5">
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
      </v-col>
      <v-col cols="6" style="margin-left: 7px">
        <h2 class="video-info" @click="moveToVideoDetail" style="margin-bottom: 10px;">{{ video.title }}</h2>
        <p style="color: gray; font-size: 0.7rem; margin-bottom: 10px">조회수 1109만회 | {{ video.date }}</p>
        <!-- content 제한된 글자 수 이내로 표시-->
        <p class="video-info" @click="moveToVideoDetail" style="line-height: 150%;">{{ video.content }}</p>
      </v-col>
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
          video: "video1",
          title: "video1 title",
          content: "t is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy.",
          date: "2020-11-09"
          
        },
        {
          id: 2,
          video: "video1",
          title: "video2 title",
          content: "video2 content content yayayayayayayyoyoyoyoyo dhdufuekaishflaldsififi j",
          date: "2020-11-09"
        },
        {
          id: 3,
          video: "video1",
          title: "video3 title",
          content: "video1 content content yayayayayayayyoyoyoyoyo dhdufuekaishflaldsififi j",
          date: "2020-11-09"
        },
        {
          id: 4,
          video: "video1",
          title: "video4 title",
          content: "video1 content content yayayayayayayyoyoyoyoyo dhdufuekaishflaldsififi j",
          date: "2020-11-09"
        },
        {
          id: 5,
          video: "video1",
          title: "video5 title",
          content: "video1 content content yayayayayayayyoyoyoyoyo dhdufuekaishflaldsififi j",
          date: "2020-11-09"
        },
        {
          id: 6,
          video: "video1",
          title: "video6 title",
          content: "video1 content content yayayayayayayyoyoyoyoyo dhdufuekaishflaldsififi j",
          date: "2020-11-09"
        },
      ],
    };
  },
  mounted() {
    console.log(this.$refs.plyr.player);
  },
  methods: {
    moveToVideoDetail() {
      console.log('detail gogo')
      router.push({name: 'VideoDetail'})
    }
  }

}
</script>

<style scoped>
.video-info {
  cursor: pointer;
}
.video-info:hover {
  color: #999
}
</style>