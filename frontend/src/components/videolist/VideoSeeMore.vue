<template>
  <!-- <div v-infinite-scroll="loadMore"
    infinite-scroll-disabled="loading"
    infinite-scroll-distance="6"> -->
  <div>
    <v-row style="display: flex; justify-content: center;">
    <div class="search">
      <input type="text" class="searchTerm" placeholder="검색어를 입력하세요.">
      <button type="submit" @click="clickedsearchBtn" class="searchButton">
        <i class="fa fa-search"></i>
      </button>
    </div>
    </v-row>
    <hr>
    <v-row style="margin: 10px 0 5px 20px">
      <h3>총 동영상 ??개</h3>
    </v-row>
    <v-row v-for="video in videos" :key="video.id" style="padding: 0 20px 0 20px">
      <v-col cols="5">
        <vue-plyr>
          <video poster="poster.png">
            <source :src="require(`@/assets/videos/${video.video}.mp4`)" type="video/mp4">
            <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
          </video>  
        </vue-plyr>
      </v-col>
      <v-col cols="6" style="margin-left: 7px">
        <h2 style="margin-bottom: 10px">{{ video.title }}</h2>
        <p style="color: gray; font-size: 0.7rem; margin-bottom: 10px">조회수 1109만회 | {{ video.date }}</p>
        <!-- content 제한된 글자 수 이내로 표시-->
        <p style="line-height: 150%">{{ video.content }}</p>
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
      ]
    }
  },
  mounted() {
    console.log(this.$refs.plyr.player)
  },
  methods: {
    clickedsearchBtn() {
      console.log('SEARCH')
    }
  }

}
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Open+Sans);

.search {
  width: 40%;
  position: relative;
  display: flex;
  justify-content: center;
  margin: 20px 0px
}

.searchTerm {
  width: 100%;
  border: 3px solid rgb(1, 118, 72);
  border-right: none;
  padding: 5px;
  height: 36px;
  border-radius: 5px 0 0 5px;
  outline: none;
  color: #9DBFAF;
  background-color: white;
  font-size: 13px;
}

.searchTerm:focus{
  color:black;
}

.searchButton {
  width: 40px;
  height: 36px;
  border: 1px solid rgb(1, 118, 72);
  background:rgb(1, 118, 72);
  text-align: center;
  color: #fff;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
  font-size: 20px;
}

/*Resize the wrap to see the search bar change!*/
/* .wrap{
  width: 30%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
} */
</style>