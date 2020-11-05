<template>
  <v-app>
    <Header />
    <div class="loading" v-if="loadingTrigger">
      <!-- <i class="fas fa-spinner fa-10x fa-spin"></i> -->
      <img src="@/assets/image/loading.gif" alt="loading" />
    </div>
    <v-main>
      <div
        class="test"
        style="
          width: 100%;
          height: 100%;
          padding-top: 75px;
          display: flex;
          flex-wrap: wrap;
          justify-content: space-around;
        "
      >
        <AnimalPhoto
          v-for="(data, index) in this.allDatas"
          :key="index"
          :animalInfo="data"
        />
      </div>
    </v-main>
  </v-app>
</template>

<script>
import AnimalPhoto from "../components/AnimalPhoto.vue";
import Header from "../components/Header.vue";
import { mapState, mapGetters, mapMutations } from "vuex";
// import data from "../assets/data/animal.json";

import SERVER from "@/api/url";
import axios from "axios";

export default {
  data() {
    return {
      trigger: "",
      tmp: 0,
      testTrigger: false,
      allDatas: "",
      matchedDatas: [],
      likedDatas: [],
      checked: ["F", "M"],
      tmpArr: [],
      userFinishedSurvey: "",
      loadingTrigger: false,
    };
  },
  components: {
    Header,
    AnimalPhoto,
  },
  watch: {
    trigger(newValue, oldValue) {
      if (newValue == 0) {
        this.allDatas = "";
        console.log("All Animals");
        if (this.$cookies.get("accessToken") != null) {
          this.loadingTrigger = true;
          axios
            .get(SERVER.URL + "/user/animal/allread", {
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            })
            .then((res) => {
              this.allDatas = res.data.animalList;
              this.loadingTrigger = false;
            })
            .catch((err) => {
              // SERVER.RefreshToken(err);
              this.loadingTrigger = false;
            });
        } else {
          this.loadingTrigger = true;
          axios
            .get(SERVER.URL + "/newuser/animal/allread")
            .then((res) => {
              this.allDatas = res.data.animalList;
              this.loadingTrigger = false;
              // console.log(res.data);
            })
            .catch((err) => {
              this.loadingTrigger = false;
            });
          // console.log(data);
        }
      } else if (newValue == 1) {
        this.matchedDatas = [];
        this.loadingTrigger = true;
        axios
          .get(SERVER.URL + "/user/animal/matchlist", {
            headers: {
              Authorization: $cookies.get("accessToken"),
            },
          })
          .then((res) => {
            this.matchedDatas = [];
            this.matchedDatas = [...res.data.perfect, ...res.data.good];
            this.userFinishedSurvey = true;
            this.loadingTrigger = false;
          })
          .catch((err) => {
            SERVER.RefreshToken(err);
            this.loadingTrigger = false;
          });
      } else {
        // console.log("like animals");
        this.likedDatas = [];
        if (this.$cookies.get("accessToken") != null) {
          this.loadingTrigger = true;
          axios
            .get(SERVER.URL + "/user/animal/like", {
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            })
            .then((res) => {
              this.likedDatas = [];
              this.likedDatas = [...res.data.animalList];
              this.loadingTrigger = false;
            })
            .catch((err) => {
              console.log(err);
              // SERVER.RefreshToken(err);
              this.loadingTrigger = false;
            });
        } else {
          alert("로그인이 필요한 서비스 입니다.");
          this.$router.push("/login");
        }
      }
    },
  },

  computed: {
    ...mapState(["eventListener"]),
    ...mapState(["dogMbti"]),
  },

  async created() {
    var data = null;
    if (this.$cookies.get("accessToken") != null) {
      this.loadingTrigger = true;
      await axios
        .get(SERVER.URL + "/user/animal/allread", {
          headers: {
            Authorization: this.$cookies.get("accessToken"),
          },
        })
        .then((res) => {
          this.allDatas = res.data.animalList;
          this.loadingTrigger = false;
          // console.log(res.data);
        })
        .catch((err) => {
          SERVER.RefreshToken(err);
          this.loadingTrigger = false;
        });

      await axios
        .get(SERVER.URL + "/user/animal/surveyread", {
          headers: {
            Authorization: $cookies.get("accessToken"),
          },
        })
        .then((res) => {
          if (res.data.survey.answer != null) {
            this.userFinishedSurvey = true;
          } else {
            this.userFinishedSurvey = false;
          }
        })
        .catch((err) => {
          console.log(err);
          SERVER.RefreshToken(err);
        });

      if (this.eventListener == 1) {
        // console.log(this.dogMbti);
        this.trigger = 1;
      } else if (this.eventListener == 2) {
        this.tirgger = 0;
      }
    } else {
      this.loadingTrigger = true;
      await axios
        .get(SERVER.URL + "/newuser/animal/allread")
        .then((res) => {
          this.allDatas = res.data.animalList;
          this.loadingTrigger = false;
        })
        .catch((err) => {
          this.loadingTrigger = false;
        });
      // console.log(data);
    }
  },

  methods: {
    ...mapGetters(["getPageCheck"]),
    ...mapMutations(["checkThisPage"]),

    setTrigger(num) {
      this.trigger = num;
      if (this.trigger == 1 && this.userFinishedSurvey == false) {
        var surveyCheck = confirm(
          "아직 추천동물 기록이 없습니다. 추천 설문을 하시겠습니까?"
        );
        if (surveyCheck) {
          this.$router.push("/survey");
        } else {
          this.trigger = 0;
        }
      }
    },

    test() {
      this.testTrigger = true;
      setTimeout(() => {
        this.testTrigger = false;
      }, 3300);
    },

    changeValue() {
      this.tmpArr.length = 0;
      for (var i = 0; i < data.animal.length; i++) {
        if (this.checked.includes(data.animal[i].sex_cd)) {
          this.tmpArr.push(data.animal[i]);
        }
      }
      console.log(this.tmpArr);
    },
  },
};
</script>

<style>
.test {
  background-image: url("../assets/image/cork.jpg");
  background-repeat: repeat;
}
</style>