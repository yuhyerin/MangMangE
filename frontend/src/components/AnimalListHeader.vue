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
    "
  >
    <div
      @click="moveToMain"
      class="logo"
      style="
        cursor: pointer;
        height: 75px;
        width: 15vw;
        display: flex;
        justify-content: flex-start;
        align-items: center;
      "
    >
      <img
        src="../assets/image/logo4.png"
        alt="logo"
        style="height: 70%; padding-left: 20px"
      />
    </div>
    <div style="width: 70vw; display: flex; justify-content: space-around">
      <div
        style="
          width: 100%;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        "
      >
        <v-btn
          text
          @click="moveToAllAnimals"
          :style="
          "
          width="10vw"
        >
          <div><h3>모든 동물 보기</h3></div>
        </v-btn>
        <div
          style="width: 100%"
          :style="
            btnChecked1 == true
              ? 'border-bottom: 7px solid rgb(250,174,95)'
              : null
          "
        ></div>
      </div>
      <div
        style="
          width: 100%;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        "
      >
        <v-btn
          text
          @click="moveToMatchedAnimals"
          :style="
          "
          width="10vw"
        >
          <div><h3>나와 맞는 동물은?</h3></div>
        </v-btn>
        <div
          style="width: 100%"
          :style="
            btnChecked2 == true
              ? 'border-bottom: 7px solid rgb(250,174,95)'
              : null
          "
        ></div>
      </div>
      <div
        style="
          width: 100%;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        "
      >
        <v-btn
          text
          @click="moveToLikedAnimals"
          :style="
          "
          width="10vw "
        >
          <div><h3>즐겨 찾는 동물들</h3></div>
        </v-btn>
        <div
          style="width: 100%"
          :style="
            btnChecked3 == true
              ? 'border-bottom: 7px solid rgb(250,174,95)'
              : null
          "
        ></div>
      </div>
    </div>
    <div
      style="
        width: 15vw;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
        align-items: center;
      "
    >
      <div
        style="
          width: 100%;
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
          color="black"
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
      <div
        style="
          width: 100%;
          display: flex;
          justify-content: flex-end;
          align-items: center;
        "
      >
        <v-btn text @click="moveTo('/videos')" width="40%">
          <div><h5>동영상 게시판</h5></div>
        </v-btn>
        <v-btn text @click="moveTo('/adoptionlist')" width="40%">
          <div><h5>입양 신청 목록</h5></div>
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
      // console.log(newVal, oldVal);
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

    moveTo(page) {
      if (page == "/animals") {
        this.setEventListener(2);
      }
      this.$router.push(page).catch((error) => {
        if (error.name === "NavigationDuplicated") {
          location.reload();
        }
      });
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