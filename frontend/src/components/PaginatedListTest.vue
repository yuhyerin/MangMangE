<template>
  <div>
    <table>
      <thead>
        <tr style="background: pink;">
          <th style="width: 20%;">NO</th>
          <th style="width: 55%;">제목</th>
          <th style="width: 15%;">아이디</th>
          <th style="width: 10%;">작성일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(p, idx) in paginatedData" :key="p.title">
          <td>{{ idx + 1 }}</td>
          <td>{{ p.title }}</td>
          <td>{{ p.user_id }}</td>
          <td>{{ p.regtime.slice(0, 10) }}</td>
        </tr>
      </tbody>
    </table>
    <div class="btn-cover">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
        이전
      </button>
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn">
        다음
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PaginatedListTest',
  data () {
    return {
      pageNum: 0
    }
  },
  props: {
    items: {
      type: Array,
      required: true
    },
    pageSize: {
      type: Number,
      required: false,
      default: 10
    }
  },
  methods: {
    nextPage () {
      this.pageNum += 1;
    },
    prevPage () {
      this.pageNum -= 1;
    }
  },
  computed: {
    pageCount () {
      let listLeng = this.items.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if (listLeng % listSize > 0) page += 1;
      
      /*
      아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
      이런식으로 if 문 없이 고칠 수도 있다!
      */

      return page;
    },
    paginatedData () {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.items.slice(start, end);
    }
  }
}
</script>

<style>
  table {
    width: 100%;
    border-collapse: collapse;
  }

  /* table th {
    font-size: 1.2rem;
  } */

  table tr {
    height: 2rem;
    text-align: center;
    border-bottom: 0.5px solid darkgray;
    /* border-bottom: 0.5px solid #505050; */
  }

  table tr:first-of-type {
    border-top: 0.5px solid pink;
  }

  table tr td {
    padding: 1rem 0;
    /* font-size: 1.1rem; */
  }

  .btn-cover {
    margin-top: 1.5rem;
    text-align: center;
  }

  .btn-cover .page-btn {
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
  }

  .btn-cover .page-count {
    padding: 0 1rem;
  }
</style>