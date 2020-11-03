<template>
  <!-- <v-app-bar id="app-toolbar" app color="white">
    <v-icon>mdi-view-list</v-icon>
    <v-spacer />
  </v-app-bar> -->
  <div
    style="
      position: fixed;
      height: 75px;
      padding: 1rem;
      color: black;
      background: white;
      font-weight: bold;
      display: flex;
      justify-content: space-between;
      align-items: center;
      top: 0;
      left: 0;
      right: 0;
      z-index: 5;
      border-bottom: 1px solid gray;
    "
  >
    <div @click="moveToMain">로고</div>
    <div>
      <div
        style="
          display: flex;
          justify-content: flex-end;
          align-items: center;
          font-size: 10px;
        "
      >
        <v-btn x-small text color="black" @click="register">
          <div>회원가입</div>
        </v-btn>
        <!-- <div style="margin: 2px 5px 2px 5px">회원가입</div> -->
        <v-btn x-small text @click="moveToLogin">
          <div v-if="isUser == false">로그인</div>
          <div v-else>로그아웃</div>
        </v-btn>
      </div>
      <div style="display: flex; justify-content: center; align-items: center">
        <v-btn text @click="test">
          <div>버어튼</div>
        </v-btn>
        <v-btn text @click="moveTo('/animals')">
          <div>동물 보기</div>
        </v-btn>
        <v-btn text @click="moveTo('/videos')">
          <div>동영상 게시판</div>
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapMutations, mapState } from "vuex";
import SERVER from "@/api/url";

export default {
  data() {
    return {
      isUser: this.$cookies.get("accessToken") == null ? false : true,
    };
  },
  watch: {
    isUser(newVal, oldVal) {
      console.log(newVal, oldVal);
    },
  },
  computed: {
    ...mapState(["eventListener"]),
  },
  created() {
    if (this.$cookies.get("accessToken") == null) {
      this.isUser = false;
    } else {
      this.isUser = true;
    }
  },
  methods: {
    ...mapMutations(["setEventListener"]),
    moveToMain() {
      location.href = "/";
    },
    register() {
      this.setEventListener(3);
      this.$router.push("/login");
    },
    moveToLogin() {
      if (this.isUser == false) {
        this.setEventListener(4);
        this.$router.push("/login");
      } else {
        this.logout();
      }
    },
    moveTo(page) {
      this.$router.push(page);
    },
    test() {
      axios
        .get(SERVER.URL + "/admin", {
          headers: {
            Authorization: this.$cookies.get("accessToken"),
            refreshToken: this.$cookies.get("refreshToken"),
          },
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log("err : ", err.response.status);
          if (err.response.status == 401) {
            //accessToken만료
            axios
              .post(
                SERVER.URL + "/newuser/refresh",
                {},
                {
                  headers: {
                    accessToken: this.$cookies.get("accessToken"),
                    refreshToken: this.$cookies.get("refreshToken"),
                  },
                }
              )
              .then((res) => {
                console.log(res);
                if (res.data.success)
                  this.$cookies.set("accessToken", res.data.accessToken);
                console.log(this.$cookies.get("accessToken"));
              })
              .catch((err) => {
                console.log(err);
              });
          }
        });
    },
    logout() {
      axios
        .post(
          SERVER.URL + "/user/logout/",
          {
            accessToken: this.$cookies.get("accessToken"),
          },
          {
            headers: {
              Authorization: this.$cookies.get("accessToken"),
              "content-type": "application/json",
            },
          }
        )
        .then((res) => {
          console.log(res);
          this.$cookies.remove("accessToken");
          this.$cookies.remove("refreshToken");
          location.href = "/";
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.navBtn {
  margin: 2px 5px 2px 5px;
}
.navBtn:hover {
  padding: 2px 5px 2px 5px;
  background-color: rgb(180, 180, 180);
}
</style>