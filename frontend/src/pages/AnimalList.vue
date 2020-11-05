<template>
  <v-app>
    <Header />
    <div class="loading" v-if="loadingTrigger">
      <!-- <i class="fas fa-spinner fa-10x fa-spin"></i> -->
      <img src="@/assets/image/loading.gif" alt="loading" />
    </div>
    <v-main>
      <v-container style="padding-top: 75px">
        <div style="display: flex; min-height: 87vh">
          <div
            style="
              width: 20vw;
              max-height: 88vh;
              display: flex;
              justify-content: center;
            "
          >
            <div
              style="
                background-color: rgb(244, 236, 225);
                border-radius: 20px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                position: fixed;
                height: 87vh;
                margin: 5px;
              "
            >
              <div>
                <div class="categoryBtn">
                  <v-btn text color="black" @click="setTrigger(0)">
                    모든 아이들 보기 &nbsp;
                  </v-btn>
                  <v-icon v-if="trigger == 0" large color="black">
                    mdi-paw-outline
                  </v-icon>
                </div>
                <div class="categoryBtn">
                  <v-btn text color="black" @click="setTrigger(1)">
                    나와 맞는 아이는? &nbsp;
                  </v-btn>
                  <v-icon v-if="trigger == 1" large color="black">
                    mdi-paw-outline
                  </v-icon>
                </div>
                <div class="categoryBtn" style="">
                  <v-btn text color="black" @click="setTrigger(2)">
                    즐겨 찾는 아이들 &nbsp;
                  </v-btn>
                  <v-icon v-if="trigger == 2" large color="black">
                    mdi-paw-outline
                  </v-icon>
                </div>
              </div>
              <div style="text-align: center">
                <div>
                  <input
                    type="checkbox"
                    id="checkbox"
                    v-model="checked[0]"
                    true-value="F"
                    false-value="no"
                    checked
                    @change="changeValue"
                  />
                  <label for="checkbox">암컷</label>
                  <input
                    type="checkbox"
                    id="checkbox"
                    v-model="checked[1]"
                    true-value="M"
                    false-value="no"
                    checked
                    @change="changeValue"
                  />
                  <label for="checkbox">수컷</label>
                </div>
                <img
                  v-if="testTrigger == false"
                  src="../assets/image/1wait.gif"
                  alt="멍멍"
                  @click="test"
                />
                <img
                  v-if="testTrigger == true"
                  src="../assets/image/1pop.gif"
                  alt="멍멍"
                />
              </div>
            </div>
          </div>
          <div
            style="
              width: 80vw;
              background-color: rgb(244, 236, 225);
              border-radius: 20px;
              margin: 5px;
              align-text: center;
            "
          >
            <div
              style="display: flex; flex-wrap: wrap; justify-content: center"
            >
              <AllAnimals
                v-if="trigger == 0"
                v-for="(data, index) in this.allDatas"
                :key="index"
                :animalInfo="data"
              />
              <AllAnimals
                v-if="trigger == 1"
                v-for="(data, index) in this.matchedDatas"
                :key="index"
                :animalInfo="data"
              />
              <AllAnimals
                v-if="trigger == 2"
                v-for="(data, index) in this.likedDatas"
                :key="index"
                :animalInfo="data"
              />
            </div>
          </div>
        </div>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import AllAnimals from "../components/AllAnimals.vue";
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
    AllAnimals,
    Header,
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