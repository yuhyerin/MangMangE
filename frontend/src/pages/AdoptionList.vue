<template>
  <div style="background: rgb(244, 236, 225)">
    <Header />
    <div class="container" style="margin-top: 75px;">
      <div class="row">
        <div class="adoption-list-div col-lg-8 col-md-8 col-sm-8">
          <h3 class="adoption-list"><strong>입양신청목록</strong></h3>
          <p class="adoption-mylist" @click="showMyList">내글보기</p>
          <PaginatedListTest :items="items" />
        </div>
      </div>
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
    Header,
    PaginatedListTest
  },
  data () {
    return {
      items: [],
      itemsOriginal: [],
      userId: "",
      flag: 0,
    }
  },

  methods: {
    showMyList() {
      if (this.flag === 0) {
        this.flag = 1
      } else {
        this.flag = 0
      }
    }
  },

  watch: {
    flag() {
      if (this.flag === 1) {
        var arr = []
        for (let i = 0; i < this.items.length - 1; i++) {
          if (this.items[i].user_id === this.userId) {
            arr.push(this.items[i])
          }
        }
        this.items = arr
      } else {
        this.items = this.itemsOriginal
      }
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
        this.items = res.data.list.reverse()
        this.itemsOriginal = res.data.list.reverse()
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
.adoption-list-div {
  margin: 0 auto;
}

.adoption-list {
  text-align: center;
  margin-bottom: 15px;
}

.adoption-mylist {
  text-align: right;
}

.adoption-mylist:hover {
  cursor: pointer;
}
</style>