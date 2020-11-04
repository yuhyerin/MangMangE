<template>
  <div>
    <h1>TABLE PAGINATION</h1>
    <paginated-list :list-array="pageArray" />
  </div>
</template>

<script>
import axios from 'axios'
import PaginatedList from './PaginatedList'
import SERVER from '@/api/url'

export default {
  name: 'SimplePagination',
  components: {
    PaginatedList
  },
  data () {
    return {
      pageArray: []
    }
  },
  created () {
    axios.get('http://sample.bmaster.kro.kr/contacts')
    .then(response => {
      console.log(response.data);
      this.pageArray = response.data.contacts;
    })
    .catch(err => {
      console.log(err.response);
    });
  }
}
</script>

<style>
h1 {
  color: #454545;
  text-align: center;
}
</style>