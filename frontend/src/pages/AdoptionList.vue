<template>
  <div>
    <Header />
    <div class="container" style="margin-top: 75px">
      <div class="row">
        <div class="adoption-list-div col-lg-10 col-md-10 col-sm-10">
          <h2 class="adoption-list"><strong>입양신청목록</strong></h2>
          <div style="display: flex; justify-content: flex-end" v-show="user">
            <v-btn
              small
              @click="showMyList"
              style="margin-bottom: 7px; background: black; color: white"
            >
              <strong>내글보기</strong>
            </v-btn>
          </div>
          <AdoptionListPagination :items="items" :userId="userId" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import axios from "axios";
import AdoptionListPagination from "../components/AdoptionListPagination.vue";
import SERVER from "@/api/url";

export default {
  name: "AdoptionList",
  components: {
    Header,
    AdoptionListPagination,
  },
  data() {
    return {
      items: [],
      itemsOriginal: [],
      userId: "",
      flag: 0,
      user: true,
    };
  },

  methods: {
    showMyList() {
      if (this.flag === 0) {
        this.flag = 1;
      } else {
        this.flag = 0;
      }
    },
  },

  watch: {
    flag() {
      if (this.flag === 1) {
        const adminList = [
          "admin",
          "admin_hee",
          "admin_so",
          "admin_rin",
          "admin_hwan",
          "admin_kyu",
        ];
        var arr = [];
        if (adminList.includes(this.userId)) {
          arr = this.items;
        } else {
          for (let i = 0; i < this.items.length; i++) {
            if (this.items[i].user_id === this.userId) {
              arr.push(this.items[i]);
            }
          }
        }
        this.items = arr;
      } else {
        this.items = this.itemsOriginal;
      }
    },
  },

  created() {
    SERVER.tokenCheck(() => {
      axios
        .get(SERVER.URL + "/user/adopt/read", {
          headers: {
            Authorization: this.$cookies.get("accessToken"),
          },
        })
        .then((res) => {
          this.items = res.data.list.reverse();
          for (let i = 0; i < this.items.length; i++) {
            var item = this.items[i];
            var someday = new Date(item.regtime);
            var year = someday.getFullYear();
            var month = someday.getMonth() + 1;
            var date = someday.getDate();
            var regTime = year + "-" + month + "-" + date;
            item.regtime = regTime;
          }
          this.itemsOriginal = this.items;
          this.userId = res.data.user_id;
          if (this.userId.includes("admin")) this.user = false;
        })
        .catch((err) => {
          console.log(err);
        });
    });
  },
};
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