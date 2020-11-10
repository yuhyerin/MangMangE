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
    <div
      @click="moveToMain"
      class="logo"
      style="
        height: 75px;
        width: 10vw;
        display: flex;
        justify-content: center;
        align-items: center;
      "
    >
      <img
        src="../assets/image/logo4.png"
        alt="logo"
        style="height: 70%; padding-left: 20px"
      />
    </div>
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
        <v-btn text @click="countDownTimer">
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
      countDown: 10,
      timerTrigger: false,
    };
  },
  watch: {
    isUser(newVal, oldVal) {
      console.log(newVal, oldVal);
    },
  },
  computed: {
    ...mapState(["eventListener"]),
    ...mapState(["test"]),
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
    ...mapMutations(["setUserSurveyCheck"]),
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
      if (page == "/animals") {
        this.setEventListener(2);
      }
      this.$router.push(page);
    },
    // test() {
    //   axios
    //     .get(SERVER.URL + "/admin", {
    //       headers: {
    //         Authorization: this.$cookies.get("accessToken"),
    //         refreshToken: this.$cookies.get("refreshToken"),
    //       },
    //     })
    //     .then((res) => {
    //       console.log(res);
    //     })
    //     .catch((err) => {
    //       console.log("err : ", err.response.status);
    //       SERVER.RefreshToken(err);
    //     });
    // },
    countDownTimer() {
      // let today = new Date();
      // let expireTime =
      //   this.$cookies.get("expireTime").substring(11, 13) * 3600 +
      //   this.$cookies.get("expireTime").substring(14, 16) * 60 +
      //   this.$cookies.get("expireTime").substring(17, 19) * 1;
      // let userTime =
      //   today.getHours() * 3600 +
      //   today.getMinutes() * 60 +
      //   today.getSeconds() * 1;
      // if (expireTime > 85800) {
      //   expireTime -= 86400;
      //   userTime -= 86400;
      // }
      // if (expireTime <= userTime) {
      //   axios
      //     .post(
      //       SERVER.URL + "/newuser/refresh",
      //       {},
      //       {
      //         headers: {
      //           accessToken: this.$cookies.get("accessToken"),
      //           refreshToken: this.$cookies.get("refreshToken"),
      //         },
      //       }
      //     )
      //     .then((res) => {
      //       console.log(res);
      //       if (res.data.success) {
      //         this.$cookies.set("accessToken", res.data.accessToken);
      //         this.$cookies.set("expireTime", res.data.expireTime);
      //       }
      //     })
      //     .catch((err) => {
      //       console.log(err);
      //     });
      // }
      // window.setTimeout(() => {
      //   alert("확인");
      // }, 2000);
    },

    logout() {
      axios
        .post(
          SERVER.URL + "/user/logout/",
          {},
          {
            headers: {
              Authorization: this.$cookies.get("accessToken"),
            },
          }
        )
        .then((res) => {
          console.log(res);
          this.$cookies.remove("accessToken");
          this.$cookies.remove("refreshToken");
          this.$cookies.remove("expireTime");
          this.setUserSurveyCheck(false);
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
.logo {
  background-image: url("../assets/image/logo.png");
  cursor: pointer;
}
</style>