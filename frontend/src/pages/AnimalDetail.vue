<template>
  <div>
    <Header />
    <div style="position: fixed; top: 90px; left: 1%">
      <div style="display: flex; justify-content: center; height: 50px">
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon x-large>mdi-arrow-left </v-icon>
        </div>
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: flex-start;
          "
        >
          <v-icon style="transform: rotate(-90deg)"> mdi-paw </v-icon>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: flex-end"
        >
          <v-icon style="transform: rotate(-90deg)"> mdi-paw </v-icon>
        </div>
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: flex-start;
          "
        >
          <v-icon style="transform: rotate(-90deg)"> mdi-paw </v-icon>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: flex-end"
        >
          <v-icon style="transform: rotate(-90deg)"> mdi-paw </v-icon>
        </div>
      </div>
      <div>돌아가기</div>
    </div>
    <div
      style="
        padding-top: 80px;
        display: flex;
        justify-content: center;
        align-items: center;
      "
    >
      <div
        style="
          width: 80%;
          display: flex;
          justify-content: center;
          align-items: center;
          height: 89vh;
        "
      >
        <div class="border" style="width: 30%; height: 100%">1</div>
        <div
          class="border"
          style="
            width: 40%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 1% 0 1%;
          "
        >
          <img :src="this.animalInfo.popfile" alt="사진" style="width: 100%" />
        </div>
        <div
          class="border"
          style="
            width: 30%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
          "
        >
          <div style="width: 100%; height: 100%">내용1</div>
          <div style="width: 100%; height: 100%">내용2</div>
          <div style="width: 100%; height: 100%">내용3</div>
          <v-btn @click="moveTo('/adoption')">버어튼</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import axios from "axios";
import SERVER from "@/api/url";

export default {
  components: {
    Header,
  },
  data() {
    return {
      likeTrigger: false,
      animalInfo: "",
      adoptionBtn: "",
      videos: [],
      admin: false,
    };
  },
  computed: {
    animalTag() {
      var tag = "";
      for (let i = 0; i < this.animalInfo.personality.length; i++) {
        tag += "#" + this.animalInfo.personality[i] + " ";
      }
      return tag;
    },

    animalAge() {
      var d = new Date();
      var n = d.getFullYear();
      var age = n - this.animalInfo.age * 1;
      if (age < 1) {
        return "1년 미만";
      } else {
        return age + "살";
      }
    },

    animalSex() {
      if (this.animalInfo.sex_cd == "M") {
        return "수컷";
      } else if (this.animalInfo.sex_cd == "F") {
        return "암컷";
      } else {
        return "알수없음";
      }
    },
  },

  async created() {
    this.animalInfo = "";
    this.likeTrigger = false;

    if (this.animalInfo.like == false) {
      this.likeTrigger = true;
    } else {
      this.likeTrigger = false;
    }

    if (this.$cookies.get("accessToken") != null) {
      await SERVER.tokenCheck(() => {
        axios
          .get(SERVER.URL + "/user/animal/detail", {
            params: {
              desertion_no: this.$route.params.animalID,
            },
            headers: {
              Authorization: this.$cookies.get("accessToken"),
            },
          })
          .then((res) => {
            console.log("유저 정보 있음", res.data);
            this.animalInfo = res.data.animalList;
            this.adoptionBtn = res.data.adoptCheck;
            axios
              .get(SERVER.URL + "/user/userId", {
                headers: {
                  Authorization: this.$cookies.get("accessToken"),
                },
              })
              .then((res) => {
                this.admin = res.data.success;
                console.log(this.admin);
              });
          })
          .catch((err) => {
            console.log(err);
          });
      });
    } else {
      await axios
        .get(SERVER.URL + "/newuser/animal/detail", {
          params: {
            desertion_no: this.$route.params.animalID,
          },
        })
        .then((res) => {
          this.animalInfo = res.data.animalList;
          this.adoptionBtn = true;
        })
        .catch((err) => {
          console.log(err);
        });
    }
    await this.getVideos();
  },
  methods: {
    setLiked() {
      if (this.$cookies.get("accessToken") == null) {
        alert("로그인이 필요한 서비스 입니다.");
      } else {
        if (this.likeTrigger == true) {
          // 좋아요 해제
          console.log("false");
          SERVER.tokenCheck(() => {
            axios
              .post(
                SERVER.URL + "/user/animal/animalLike",
                {
                  desertion_no: this.animalInfo.desertion_no,
                },
                {
                  headers: {
                    Authorization: this.$cookies.get("accessToken"),
                  },
                }
              )
              .then((res) => {
                console.log(res);
              })
              .catch((err) => {
                console.log(err);
              });
          });
        } else {
          // 좋아요 등록
          console.log("true");
          SERVER.tokenCheck(() => {
            axios
              .post(
                SERVER.URL + "/user/animal/animalLike",
                {
                  desertion_no: this.animalInfo.desertion_no,
                },
                {
                  headers: {
                    Authorization: this.$cookies.get("accessToken"),
                  },
                }
              )
              .then((res) => {
                console.log(res);
              })
              .catch((err) => {
                console.log(err);
              });
          });
        }
        this.likeTrigger = !this.likeTrigger;
      }
    },
    moveTo(page) {
      this.$router.push(page + `/${this.$route.params.animalID}`);
    },

    getVideos() {
      console.log("dNo", this.animalInfo);
      axios
        .get(SERVER.URL + "/newuser/video/search", {
          params: {
            desertion_no: this.$route.params.animalID,
          },
        })
        .then((res) => {
          console.log(res.data.videoList);
          if (res.data.videoList.length >= 4) {
            this.videos = res.data.videoList.slice(-4);
          } else {
            this.videos = res.data.videoList;
          }
        });
    },
  },
};
</script>

<style>
.border {
  background-color: gray;
}
.bounce-enter-active {
  animation: bounce-in 0.5s;
}
.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
  z-index: -5;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}
</style>	