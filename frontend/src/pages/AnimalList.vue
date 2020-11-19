<template>
  <v-app>
    <AnimalListHeader @changeComponents="componentChange" />
    <div class="loading" v-if="loadingTrigger">
      <!-- <i class="fas fa-spinner fa-10x fa-spin"></i> -->
      <img src="@/assets/image/loading.gif" alt="loading" />
    </div>
    <v-main>
      <div style="padding-top: 75px; display: flex; justify-content: center">
        <div
          style="
            padding-top: 10px;
            width: 10vw;
            position: fixed;
            top: 13vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
          "
          :style="
            viewStyle == false && trigger == 1 ? 'left: 0.5vw;' : 'left: 1vw;'
          "
        >
          <div>
            <v-btn @click="changeViewStyle(false)">
              <v-icon>mdi-view-grid</v-icon>
            </v-btn>
            <v-btn @click="changeViewStyle(true)">
              <v-icon>mdi-format-align-justify</v-icon>
            </v-btn>
          </div>
          <div class="reSurvey" style="margin-top: 20px" @click="moveToSurvey">
            설문 다시하기
          </div>
        </div>
        <div style="display: flex; min-height: 87vh; width: 80vw">
          <div
            v-if="viewStyle == false"
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

          <!-- **************************페이지 표시 구분************************* -->
          <div
            v-if="viewStyle == true"
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
              <AnimalInfo
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
                  <AnimalInfo
                    v-for="(data, index) in this.perfectDatas"
                    :key="index"
                    :animalInfo="data"
                    :matched="100"
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
                  <AnimalInfo
                    v-for="(data, index) in this.goodDatas"
                    :key="index"
                    :animalInfo="data"
                    :matched="75"
                  />
                </div>
              </div>
            </div>
            <div
              v-if="trigger == 2"
              style="display: flex; flex-wrap: wrap; justify-content: center"
            >
              <AnimalInfo
                v-for="(data, index) in this.likedDatas"
                :key="index"
                :animalInfo="data"
              />
            </div>
          </div>
        </div>
      </div>
    </v-main>
    <ScrollToTop />
  </v-app>
</template>

<script>
import AnimalCard from "../components/AnimalCard.vue";
import AnimalInfo from "../components/AnimalInfo.vue";
import AnimalListHeader from "../components/AnimalListHeader.vue";
import ScrollToTop from "../components/ScrollToTop.vue";
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
      viewStyle: false,
      dangsu: {
        desertion_no: "430368202000374",
        age: "2020",
        care_addr:
          "대전광역시 유성구 갑동로15번길 20-39 (갑동) 대전동물보호센터",
        care_nm: "대전동물보호센터",
        care_tel: "042-825-1118",
        charge_nm: "대덕구",
        color_cd: "검정색",
        filename: "",
        happen_dt: "20200728",
        happen_place: "대전광역시 유성구 봉명동 레자미3차 주변",
        kind_c: "믹스견",
        kind_p: "개",
        mbti: "ECSW",
        neuter_yn: "U",
        notice_edt: "20200828",
        notice_no: "대전-대덕-2020-77431",
        notice_sdt: "20200828",
        officetel: "042-608-6951",
        org_nm: "대전광역시 대덕구",
        popfile:
          "https://user-images.githubusercontent.com/44697835/99143860-4c358500-26a4-11eb-8adb-c705e8481c58.jpg",
        process_state: "보호중",
        sex_cd: "M",
        special_mark: "자이언드",
        weight: "98.7",
        personality: ["민첩한", "영리한", "친근감넘치는", "신중한"],
      },
    };
  },
  components: {
    AnimalCard,
    AnimalListHeader,
    AnimalInfo,
    ScrollToTop,
  },
  watch: {
    trigger(newValue, oldValue) {
      if (newValue == 0) {
        this.allDatas = "";
        if (this.$cookies.get("accessToken") != null) {
          this.loadingTrigger = true;
          SERVER.tokenCheck(() => {
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
          });
        } else {
          this.loadingTrigger = true;
          axios
            .get(SERVER.URL + "/newuser/animal/allread")
            .then((res) => {
              this.allDatas = res.data.animalList;
              this.loadingTrigger = false;
            })
            .catch((err) => {
              this.loadingTrigger = false;
            });
        }
      } else if (newValue == 1) {
        this.matchedDatas = [];
        this.loadingTrigger = true;
        SERVER.tokenCheck(() => {
          axios
            .get(SERVER.URL + "/user/animal/matchlist", {
              headers: {
                Authorization: $cookies.get("accessToken"),
              },
            })
            .then((res) => {
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
            });
        });
      } else {
        this.likedDatas = [];
        if (this.$cookies.get("accessToken") != null) {
          this.loadingTrigger = true;
          SERVER.tokenCheck(() => {
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
                this.loadingTrigger = false;
              });
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
      SERVER.tokenCheck(() => {
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
            this.loadingTrigger = false;
          });
      });

      if (this.eventListener == 1) {
        this.trigger = 1;
      } else if (this.eventListener == 2) {
        this.tirgger = 0;
      }
    } else {
      this.loadingTrigger = true;
      axios
        .get(SERVER.URL + "/newuser/animal/allread")
        .then((res) => {
          this.allDatas = res.data.animalList;
          this.loadingTrigger = false;
        })
        .catch((err) => {
          this.loadingTrigger = false;
        });
    }
  },

  methods: {
    ...mapGetters(["getPageCheck"]),
    ...mapMutations(["checkThisPage"]),
    ...mapMutations(["resetSurvey"]),

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
    changeViewStyle(value) {
      this.viewStyle = value;
    },

    test() {
      this.testTrigger = true;
      setTimeout(() => {
        this.testTrigger = false;
      }, 3300);
    },

    moveToSurvey() {
      var surveyCheck = confirm("설문을 다시 작성하시겠습니까?");
      if (surveyCheck) {
        this.$router.push("/survey");
        this.resetSurvey();
      } else {
        return;
      }
    },

    componentChange(value) {
      this.trigger = value;
    },
  },
};
</script>

<style scoped>
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
}
.reSurvey:hover {
  color: blue;
  cursor: pointer;
  border-bottom: 2px solid blue;
}
</style>