<template>
  <div v-infinite-scroll="loadMore"
    infinite-scroll-disabled="loading"
    infinite-scroll-distance="6">
    <v-row style="display: flex; justify-content: center;">
    <div class="search">
      <input type="text" class="searchTerm" placeholder="검색어를 입력하세요.">
      <button type="submit" @click="searchButton" class="searchButton">
        <i class="fa fa-search"></i>
      </button>
    </div>
    </v-row>
    <hr>
    <v-row v-for="video in videos" :key="video">
      <v-col v-for="n in 3" :key="n" style="padding-bottom: 30px;">
        <vue-plyr>
          <video poster="poster.png">
            <source :src="require(`@/assets/videos/${video.video}.mp4`)" type="video/mp4">
            <track kind="captions" label="English" srclang="en" src="captions-en.vtt" default>
          </video>  
        </vue-plyr>
        <div class="videoTitle" style="text-align:center;" @click="moveToVideoDetail(video.id)">동영상 제목{{video.id}}</div>
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
  methods: {
    moveToVideoDetail(videoIndex) {
      this.$router.push(
        {
          name: 'VideoDetail',
          params: {
            videoId: videoIndex
          }
        }
      )
    }
  },
  mounted() {
    console.log(1)
    console.log(this.$refs.plyr.player)
    
  },

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

div.videoTitle:hover {
  cursor: pointer;
}

</style>