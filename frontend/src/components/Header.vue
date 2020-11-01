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
        <v-btn x-small text color="black" @click="moveTo">
          <div>회원가입</div>
        </v-btn>
        <!-- <div style="margin: 2px 5px 2px 5px">회원가입</div> -->
        <v-btn x-small text @click="moveTo">
          <div v-if="isUser == false">로그인</div>
          <div v-else>로그아웃</div>
        </v-btn>
      </div>
      <div style="display: flex; justify-content: center; align-items: center">
        <v-btn text @click="moveTo('/animals')">
          <div>동물 보기</div>
        </v-btn>
        <v-btn text>
          <div>동영상 게시판</div>
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
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
  created() {
    if (this.$cookies.get("accessToken") == null) {
      this.isUser = false;
    } else {
      this.isUser = true;
    }
  },
  methods: {
    moveToMain() {
      location.href = "/";
    },
    register() {
      this.$router.push("/login");
    },
    moveToLogin() {
      if (this.isUser == false) {
        this.$router.push("/login");
      } else {
        this.logout();
      }
    },
    moveTo(page) {
      this.$router.push(page);
    },
    logout() {
      axios
        .post(
          SERVER.URL + "user/logout/",
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