<template>
  <div>
    <Header />
    <div class="container" style="margin-top: 100px;">
      <div style="display: flex; justify-content: space-between; height: 50px;">
        <h2><strong>입양신청목록</strong></h2>
        <div class="adoption-search">
          <v-text-field
            v-model="searchWord"
            @keyup.enter="searchAdoptList()"
            @click="searchAdoptList()"
            append-icon="mdi-magnify"
            label="검색"
        ></v-text-field>
        </div>
      </div>
      <b-table hover head-variant='dark' :items="items" :fields="fields" :per-page="perPage" :currentPage="currentPage"></b-table>
      <b-pagination
        pills
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        :currentPage="currentPage"
        align="center"
        aria-controls="adoption-table"
      ></b-pagination>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
export default {
  name: "AdoptionList",
  data() {
    return {
      searchWord: "",
      perPage: 5,
      currentPage: 1,
      fields: [
        {
          key: 'adoptionNumber',
          label: '번호',
        },
        {
          key: 'title',
          label: '제목',
        },
        {
          key: 'writer',
          label: '글쓴이'
        },
        {
          key: 'adoptionDay',
          label: '작성일'
        }
      ],
      items: [
      {
        adoptionNumber: 11,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 10,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 9,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 8,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 7,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 6,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 5,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 4,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 3,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 2,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      },
      {
        adoptionNumber: 1,
        title: "이 아이를 입양하고 싶습니다",
        writer: "김싸피",
        adoptionDay: "201029",
      }]
    }
  },
  components: {
    Header,
  },
  methods: {
    searchAdoptList() {
      console.log('클릭')
    }
  },
  created: {
   
  },
  computed: {
    rows() {
      return this.items.length
    },
  }
}
</script>

<style>
  .table {
    margin-top: 20px;
  }

  .adoption-search {
    cursor: pointer;
  }

  #adoption-table {
    border-top: 1px solid black;
    border-collapse: collapse;
    width: 100%;
  }

  .errspan {
    border: 1px solid black;
    float: right;
    margin-right: 6px;
    margin-top: -20px;
    position: relative;
    z-index: 2;
    width: 50%;
  }

  .input-wrapper {
    display:inline-block;
    position: relative;
    border: 1px solid black;
  }

  .input-wrapper:after {
      font-family: 'FontAwesome';
      content: '\f128';
      position: absolute;
      right: 6px;
    }
</style>