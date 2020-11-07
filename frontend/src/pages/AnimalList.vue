<template>
  <v-app>
    <AnimalListHeader @changeComponents="componentChange" />
    <div class="loading" v-if="loadingTrigger">
      <!-- <i class="fas fa-spinner fa-10x fa-spin"></i> -->
      <img src="@/assets/image/loading.gif" alt="loading" />
    </div>
    <v-main>
      <div style="padding-top: 75px; display: flex; justify-content: center">
        <div style="display: flex; min-height: 87vh">
          <div
            style="
              width: 80vw;
              border-radius: 20px;
              margin: 10px;
              align-text: center;
              justify-content: center;
            "
          >
            <div
              v-if="trigger == 0"
              style="display: flex; flex-wrap: wrap; justify-content: center"
            >
              <AnimalCard
                v-for="(data, index) in this.allDatas"
                :key="index"
                :animalInfo="data"
              />
            </div>
            <div v-if="trigger == 1">
              <div style="border: 1px solid rgba(0, 0, 0, 0.12)">
                <div
                  style="
                    width: 100%;
                    height: 5vh;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    height: 10%;
                  "
                >
                  <div
                    style="
                      margin: 20px 0 20px 0;
                      display: flex;
                      justiy-content: center;
                      align-items: center;
                    "
                  >
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                  </div>
                  <hr
                    style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)"
                  />
                </div>
                <div
                  style="
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: center;
                  "
                >
                  <AnimalCard
                    v-for="data in this.perfectDatas"
                    :key="data.desertion_no"
                    :animalInfo="data"
                  />
                </div>
              </div>
              <br />
              <v-divider></v-divider>
              <br />
              <div style="border: 1px solid rgba(0, 0, 0, 0.12)">
                <div
                  style="
                    width: 100%;
                    height: 5vh;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    height: 10%;
                  "
                >
                  <div
                    style="
                      margin: 20px 0 20px 0;
                      display: flex;
                      justiy-content: center;
                      align-items: center;
                    "
                  >
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                    <v-icon x-large color="yellow"> mdi-star</v-icon>
                  </div>
                  <hr
                    style="width: 90%; border: 0.2px solid rgba(0, 0, 0, 0.12)"
                  />
                </div>
                <div
                  style="
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: center;
                  "
                >
                  <AnimalCard
                    v-for="data in this.goodDatas"
                    :key="data.desertion_no"
                    :animalInfo="data"
                  />
                </div>
              </div>
            </div>
            <div
              v-if="trigger == 2"
              style="display: flex; flex-wrap: wrap; justify-content: center"
            >
              <AnimalCard
                v-for="(data, index) in this.likedDatas"
                :key="index"
                :animalInfo="data"
              />
            </div>
          </div>
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import AnimalCard from "../components/AnimalCard.vue";
import AnimalListHeader from "../components/AnimalListHeader.vue";
import { mapState, mapGetters, mapMutations } from "vuex";

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
      perfectDatas: [],
      goodDatas: [],
      likedDatas: [],
      checked: ["F", "M"],
      tmpArr: [],
      userFinishedSurvey: false,
      loadingTrigger: false,
      perfectLength: 0,
      goddLength: 0,
    };
  },
  components: {
    AnimalCard,
    AnimalListHeader,
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
              SERVER.RefreshToken(err);
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
            console.log(res.data.perfect.length, res.data.good.length);
            this.perfectDatas = [];
            this.goodDatas = [];
            this.perfectDatas = [...res.data.perfect];
            this.goodDatas = [...res.data.good];
            this.matchedDatas = [];
            this.matchedDatas = [...res.data.perfect, ...res.data.good];
            this.loadingTrigger = false;
          })
          .catch((err) => {
            console.log("error message", err);
            SERVER.RefreshToken(err);
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
              console.log(res.data.animalList);
              this.likedDatas = [];
              this.likedDatas = [...res.data.animalList];
              this.loadingTrigger = false;
            })
            .catch((err) => {
              console.log(err);
              SERVER.RefreshToken(err);
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
    this.userFinishedSurvey = false;
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

    componentChange(value) {
      this.trigger = value;
    },
  },
};
</script>

<style>
.categoryBtn {
  padding-top: 10px;
  height: 7vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.loading {
  height: 100vh;
  width: 100vw;
  position: absolute;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 5px;
  background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4));
  /* background-image: linear-gradient(
    rgba(255, 255, 255, 0.4),
    rgba(255, 255, 255, 0.4)
  ); */
}
</style>