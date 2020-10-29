<template>
  <div>
    <Header />
    <div class="container" style="margin-top: 100px;">
      <div style="display: flex; justify-content: space-between; height: 50px;">
        <h2>입양신청목록</h2>
        <!-- <div class="input-wrapper">
          <input type="text" placeholder="검색" />
        </div> -->
          <div class="adoption-search">
            <!-- <v-text-field
              label="검색"
              append-icon="mdi-magnify"
              @click="searchAdoptList()"
            ></v-text-field> -->
            <v-text-field
            v-model="searchWord"
            @keyup.enter="searchAdoptList()"
            @click="searchAdoptList()"
            append-icon="mdi-magnify"
            label="검색"
          ></v-text-field>
          </div>
      </div>
      <b-table hover :items="items" :fields="fields" :per-page="perPage" :currentPage="currentPage"></b-table>
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        :currentPage="currentPage"
        align="center"
        aria-controls="adoption-table"
      ></b-pagination>
        <!-- <div style="display: flex; justify-content: space-between;">
          <h1>입양신청목록</h1>
          <div class="display: flex;"> -->
            <!-- <input type="text" style="border: 1px solid black; width: 90%; placeholder: '검색'; margin-right: 5px;"/>
            <span class="fas fa-question"></span> -->
            <!-- <input name="txtName" id="txtName">
            <span class="fas fa-question errspan"></span> -->
            <!-- <div class="input-wrapper">
              <input type="text" placeholder="검색" />
            </div>
          </div>
        </div> -->

          <!-- <b-table
            id="adoption-table"
            :adopts="adopts"
            :per-page="perPage"
            :current-page="currentPage"
            small
          ></b-table> -->
          <!-- <table id="adoption-table">
            <thead class="adoption-thead">
              <tr>
                <th style="width: 20%;">번호</th>
                <th style="width: 60%;">제목</th>
                <th style="width: 10%;">글쓴이</th>
                <th style="width: 10%;">작성일</th>
              </tr>
            </thead>
            <tbody class="adoption-tbody" v-for="adopt in adoptsList" :key="adopt.adoptionNumbe">
              <tr>
                <td>{{ adopt.adoptionNumbe }}</td>
                <td>{{ adopt.titl }}</td>
                <td>{{ adopt.write }}</td>
                <td>{{ adopt.adoptionDa }}</td>
              </tr>
            </tbody>
          </table> -->
          <!-- <ul id="adoption-table">
            <li v-for="item in adoptsList" :key="item.titl">
              <h1>{{ item.titl }}</h1>
              <span>{{ item.write }}</span>
            </li>
          </ul> -->
      </div>
  </div>
</template>

<script>
import Header from '../components/Header.vue'
export default {
  name: 'AdoptionList',
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
    // adoptsList() {
    //   return this.adopts.slice(
    //     (this.currentPage - 1) * this.perPage,
    //     this.currentPage * this.perPage,
    //   )
    // }
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
  
  /* th, td {
    border-bottom: 1px solid black;
  }

  th {
    text-align: left;
  } */

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