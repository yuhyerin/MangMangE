<template>
  <div
    style="
      position: fixed;
      height: 75px;
      padding: 1rem;
      color: black;
      background-color: rgba(255, 255, 255, 0.5);
      font-weight: bold;
      display: flex;
      justify-content: space-between;
      align-items: center;
      top: 0;
      left: 0;
      right: 0;
      z-index: 5;
    "
    :style="
      this.eventListener == -1
        ? 'background-color: rgba(255, 255, 255, 0.5);'
        : 'background-color: white'
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
        src="../assets/image/logofinal.png"
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
        <v-btn
          v-if="this.$cookies.get('refreshToken') == null"
          x-small
          text
          @click="register"
        >
          <div><h4>회원가입</h4></div>
        </v-btn>
        <!-- <div style="margin: 2px 5px 2px 5px">회원가입</div> -->
        <v-btn x-small text @click="moveToLogin">
          <div v-if="isUser == false"><h4>로그인</h4></div>
          <div v-else><h4>로그아웃</h4></div>
        </v-btn>
      </div>
      <div style="display: flex; justify-content: center; align-items: center">
        <!-- <v-btn
          text
          color="white"
          @click="toggleButton"
          :disabled="this.btnTrigger[1] == true ? true : false"
        >
          <div><h3>버어튼</h3></div>
        </v-btn> -->
        <v-btn text @click="moveTo('/animals')">
          <div>
            <h3>동물 보기</h3>
          </div>
        </v-btn>
        <v-btn text @click="moveTo('/videos')">
          <div>
            <h3>동영상 게시판</h3>
          </div>
        </v-btn>
        <v-btn text @click="moveToList">
          <div>
            <h3>입양 신청 목록</h3>
          </div>
        </v-btn>
        <v-btn v-show="isAdmin" text @click="moveTo('/livechat')">
          <div>
            <h3>라이브 방송하기</h3>
          </div>
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
      btnTrigger: [false, false, false, false],
      isAdmin: false,
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
      axios
        .get(SERVER.URL + "/user/userId", {
          headers: {
            Authorization: this.$cookies.get("accessToken"), //the token is a variable which holds the token
          },
        })
        .then((res) => {
          if(res.data.success)
            this.isAdmin = true;
          else
            this.isAdmin=false;
        })
        .catch((err) => {
          console.log(err)
        });
    }
  },
  methods: {
    ...mapMutations(["setEventListener"]),
    ...mapMutations(["setUserSurveyCheck"]),
    moveToMain() {
      this.setEventListener(-1);
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
      if (this.$router.history.current.path == page) {
        // location.reload(true);
      }
      if (page == "/animals") {
        this.setEventListener(2);
      } else {
        this.setEventListener(11);
      }
      this.$router.push(page).catch((error) => {
        if (error.name === "NavigationDuplicated") {
          location.reload();
        }
      });
    },
    moveToList() {
      if (this.$cookies.get("accessToken") == null) {
        alert("회원만 이용 가능합니다.");
        return;
      } else {
        console.log(this.$router.history.path);
        this.$router.push("/adoptionlist").catch((error) => {
          if (error.name === "NavigationDuplicated") {
            location.reload();
          }
        });
      }
    },
    toggleButton() {
      console.log(this.btnTrigger);
      this.btnTrigger[1] = true;
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
      SERVER.tokenCheck(() => {
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
            this.$cookies.remove("accessToken");
            this.$cookies.remove("refreshToken");
            this.$cookies.remove("expireTime");
            this.setUserSurveyCheck(false);
            alert("로그아웃 되셨습니다.");
            location.href = "/";
          })
          .catch((err) => {
            console.log(err);
          });
      });
    },
  },
};
</script>

<style scoped>
.navBtn {
  margin: 2px 5px 2px 5px;
}
.navBtn:hover {
  padding: 2px 5px 2px 5px;
  background-color: rgb(180, 180, 180);
}
.logo {
  cursor: pointer;
}
</style>