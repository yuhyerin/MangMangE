<template>
  <div>
    <table class="pagination-table">
      <thead>
        <tr style="background: rgb(244, 236, 225)">
          <th style="width: 20%">NO</th>
          <th style="width: 55%">제목</th>
          <th style="width: 15%">아이디</th>
          <th style="width: 10%">작성일</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="tr-apply"
          v-for="p in paginatedData"
          :key="p.title"
          @click="showMyApply(p)"
        >
          <td>{{ p.uid }}</td>
          <td class="td-title">{{ p.title }}</td>
          <td>{{ p.user_id }}</td>
          <td>{{ p.regtime.slice(0, 10) }}</td>
        </tr>
      </tbody>
    </table>
    <!-- <v-card
      v-for="p in paginatedData"
      :key="p.no"
      style="margin-bottom: 10px;"
      @click="showMyApply(p)"
    >
      <v-card-title>
        <strong>{{ p.title }}</strong>
      </v-card-title>

      <v-card-subtitle>
        <strong>{{ p.user_id }}</strong>
      </v-card-subtitle>

      <v-card-actions>
        <v-btn
          class="ma-2"
          outlined
          style="background: green; color: white;"
        >
          <strong>{{ p.uid }}</strong>
        </v-btn>
        <v-btn
          class="ma-2"
          outlined
          style="color: green;"
        >
          <strong>{{ p.regtime.slice(0, 10) }}</strong>
        </v-btn>
      </v-card-actions>
    </v-card> -->
    <div class="btn-cover">
      <v-btn
        class="mx-2"
        style="background: black; color: white"
        fab
        :disabled="pageNum === 0"
        @click="prevPage"
      >
        <v-icon dark> mdi-arrow-left </v-icon>
      </v-btn>
      <span class="page-count"
        ><strong>{{ pageNum + 1 }} / {{ pageCount }} 페이지</strong></span
      >
      <v-btn
        class="mx-2"
        style="background: black; color: white"
        fab
        :disabled="pageNum >= pageCount - 1"
        @click="nextPage"
      >
        <v-icon dark> mdi-arrow-right </v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdoptionListPagination",
  data() {
    return {
      pageNum: 0,
    };
  },
  props: {
    items: {
      type: Array,
      required: true,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 5,
    },
    userId: {
      required: true,
    },
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    showMyApply(item) {
      console.log(this.userId);
      if (item.user_id === this.userId) {
        this.$router.push({
          name: "AdoptionUpdate",
          params: {
            uid: item.uid,
          },
        });
      } else {
        alert("본인의 글만 열람할 수 있습니다");
      }
    },
  },
  computed: {
    pageCount() {
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
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;

      return this.items.slice(start, end);
    },
  },
};
</script>

<style scoped>
.pagination-table {
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
  border-top: 0.5px solid darkgray;
}

table tr td {
  padding: 1rem 0;
  /* font-size: 1.1rem; */
}

.tr-apply:hover {
  background: lightgray;
}

.td-title:hover {
  cursor: pointer;
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