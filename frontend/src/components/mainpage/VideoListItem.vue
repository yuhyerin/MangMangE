<template>
  <div>
    <!-- list-group-item은 bootstrap -->
    <li @click="onVideoSelect" class="video-list-item list-group-item">
      <img :src="thumbnailUrl" class="mr-3" alt="youtube-thumbnail-image">
      <div class="media-body">
        {{ video.snippet.title }}
      </div>
    </li>
  </div>
</template>

<script>
export default {
  name: 'VideoListItem',
  props: {
    video: Object,
  },
  methods: {
    onVideoSelect() {
      this.$emit('video-select', this.video)
    }
  },
  computed: {
    // 함수지만 명사 => 결국 리턴되는 값으로 사용되기 때문.
    thumbnailUrl() {
      // props의 video에 접근하는것. props도 data 다루듯이 this를 붙여야 한다.
      return this.video.snippet.thumbnails.default.url
    }
  }
}
</script>

<style>
  li.video-list-item {
    display: flex;
    cursor: pointer;
  }
  li.video-list-item:hover {
    background-color: #eee;
  }

.media-body {
  margin: auto 0;
}
</style>