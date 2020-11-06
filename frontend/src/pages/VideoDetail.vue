<template>
  <div>
    <Header />
    <div>
      동영상, 다음 재생목록, 제목, 내용, 후원하기 버튼, 강아지 상세페이지 이동
      <v-btn
        width="95%"
        height="70%"
        color="rgb(1,118,72)"
        @click="moveToSupport"
      >
        <div style="color: white">후원하기</div>
      </v-btn>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import SERVER from "@/api/url";
import axios from "axios";
import router from '@/router'

export default {
  components: {
    Header,
  },
  data() {
    return {

    }
  },
  methods: {
    moveToSupport() {
      router.push({name: 'Support'})
    }
  },
  created() {

  },
}
</script>

<style>

</style>