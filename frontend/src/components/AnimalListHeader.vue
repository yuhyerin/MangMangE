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
    <div @click="moveToMain" style="width: 10vw">로고</div>
    <div style="width: 80vw; display: flex; justify-content: space-around">
      <v-btn
        text
        :color="btnChecked1 == true ? 'white' : 'black'"
        @click="moveToAllAnimals"
        :style="btnChecked1 == true ? 'background-color:rgb(33,150,243)' : null"
        width="10vw"
      >
        <div>모든 동물 보기</div>
      </v-btn>
      <v-btn
        text
        :color="btnChecked2 == true ? 'white' : 'black'"
        @click="moveToMatchedAnimals"
        :style="btnChecked2 == true ? 'background-color:rgb(33,150,243)' : null"
        width="10vw"
      >
        <div>나와 맞는 동물은?</div>
      </v-btn>
      <v-btn
        text
        :color="btnChecked3 == true ? 'white' : 'black'"
        @click="moveToLikedAnimals"
        :style="btnChecked3 == true ? 'background-color:rgb(33,150,243)' : null"
        width="10vw "
      >
        <div>즐겨 찾는 동물들</div>
      </v-btn>
    </div>
    <div style="width: 10vw">
      <div
        style="
          display: flex;
          justify-content: center;
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
        <v-btn text @click="moveTo('/videos')" width="100%">
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
      toggle_exclusive: undefined,
      btnChecked1: false,
      btnChecked2: false,
      btnChecked3: false,
      userFinishedSurvey: false,
    };
  },
  watch: {
    isUser(newVal, oldVal) {
      console.log(newVal, oldVal);
    },
  },
  computed: {
    ...mapState(["eventListener"]),
    ...mapState(["userSurveyCheck"]),
  },
  created() {
    if (this.eventListener == 1) {
      this.btnChecked1 = false;
      this.btnChecked2 = true;
      this.btnChecked3 = false;
    } else if (this.eventListener == 2) {
      this.btnChecked1 = true;
      this.btnChecked2 = false;
      this.btnChecked3 = false;
    }

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
    moveToAllAnimals() {
      this.btnChecked1 = true;
      this.btnChecked2 = false;
      this.btnChecked3 = false;
      this.$emit("changeComponents", 0);
    },
    moveToMatchedAnimals() {
      if (this.$cookies.get("accessToken") == null) {
        alert("로그인이 필요한 서비스 입니다.");
        return;
      } else if (this.userSurveyCheck == false) {
        var surveyCheck = confirm(
          "아직 추천동물 기록이 없습니다. 추천 설문을 하시겠습니까?"
        );
        if (surveyCheck) {
          this.$router.push("/survey");
        } else {
          return;
        }
      } else {
        this.btnChecked1 = false;
        this.btnChecked2 = true;
        this.btnChecked3 = false;
        this.$emit("changeComponents", 1);
      }
    },
    moveToLikedAnimals() {
      if (this.$cookies.get("accessToken") == null) {
        alert("로그인이 필요한 서비스 입니다.");
        return;
      } else {
        this.btnChecked1 = false;
        this.btnChecked2 = false;
        this.btnChecked3 = true;
        this.$emit("changeComponents", 2);
      }
    },

    countDownTimer() {
      if (this.countDown > 0) {
        setTimeout(() => {
          this.countDown -= 1;
          this.countDownTimer();
        }, 1000);
      }
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
</style>