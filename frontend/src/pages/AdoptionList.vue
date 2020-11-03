<template>
  <div>
    <Header />
    <div class="container" style="margin-top: 65px;">
      <h1>입양신청목록</h1>
      <PaginatedListTest :items="items" />
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue"
import axios from 'axios'
import PaginatedListTest from "../components/PaginatedListTest.vue"
import SERVER from '@/api/url'

export default {
  name: 'AdoptionList',
  components: {
    PaginatedListTest
  },
  data () {
    return {
      items: [],
      user_id: "",
    }
  },
  created () {
    axios.get(SERVER.URL + '/user/adopt/read',
      {
        headers: {
          Authorization: this.$cookies.get("accessToken")
        }
      })
      .then((res) => {
        console.log(res.data)
        this.items = res.data.list
        this.userId = res.data.user_id
      })
      .catch((err) => {
        console.log('cat')
        console.log(err)
      })
  }
}
</script>

<style>
h1 {
  color: #454545;
  text-align: center;
}
</style>