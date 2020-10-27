<template>
  <div class="section s2">
    <div class="container s3">
      <SearchBar />
      <div class="row">
        <div class="col-lg-8">
          <div class="embed-responsive embed-responsive-4by3" style="margin-top: 30px;">
            <iframe 
              class="embed-responsive-item" 
              :src="videoUrl"
              allowfullscreen></iframe>
          </div>
        </div>
        <div class="col-lg-3">
          <div style="height: 95%; margin-top: 28px; background-color: white;">
            <p class="more-videos">
              <strong class="more-videos-strong">
                
              </strong>
            </p>
          </div>
        </div>
        <div class="col-lg-1" style="margin-top: auto; margin-bottom: auto;">
          <img class="right-arrow" src="../assets/image/mainpage/section3-right-arrow.png">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SearchBar from '../components/mainpage/SearchBar.vue'
import axios from 'axios'

const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
const API_URL = 'https://www.googleapis.com/youtube/v3/search'

export default {
  name: 'Section3',
  components: {
    SearchBar,
  },
  computed: {
    videoUrl() {
      return `https://youtube.com/embed/OrxmtDw4pVI?autoplay=1`
    }
  }
}
</script>

<style>
  .s2 {
    background-color:  rgb(244, 236, 225);
  }

  .container.s3 {
    height: 754px;
    background-color: rgb(244, 236, 225);
  }

  .more-videos {
    cursor: pointer;
    font-size: x-large;
    text-align: center;
    margin-top: auto;
    margin-bottom: auto;
  }

  .more-videos-strong:hover {
    background-color: darkgray;
  }

  img.right-arrow {
    width: 25px;
    height: 25px;
  }
</style>