<template>
    <!-- list-group은 bootstrap -->
    <ul class="list-group col-lg-4">
      <VideoListItem 
        @video-select="onVideoSelect" 
        :video="video" 
        v-for="video in videos" 
        :key="video.etag" />
      <!-- <li v-for="video in videos" :key="video.etag">
        {{ video.snippet.description }}
      </li> -->
    </ul>
</template>

<script>
import VideoListItem from './VideoListItem.vue'

export default {
  name: 'VideoList',
  components: {
    VideoListItem
  },
  props: {
    videos: Array
  },
  methods: {
    onVideoSelect(video) {
      this.$emit('video-select', video)
    }
  }
}
</script>

<style>

</style>