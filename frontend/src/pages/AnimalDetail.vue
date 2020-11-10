<template>
  <v-app>
    <Header />
    <v-container style="padding-top: 90px">
      <div
        style="
          height: 40vh;
          background-color: rgb(244, 236, 225);
          border-radius: 15px;
        "
      >
        <div
          style="
            display: flex;
            height: 100%;
            justify-content: center;
            align-items: center;
          "
        >
          <!-- <div>
            <v-icon color="black"> mdi-arrow-left </v-icon>
          </div> -->
          <div
            style="
              display: flex;
              justify-content: center;
              align-items: center;
              overflow: hidden;
              width: 30vw;
              height: 90%;
              margin-right: 2%;
              margin-left: 2%;
            "
          >
            <img
              :src="this.animalInfo.popfile"
              alt="사진"
              style="width: 100%"
            />
          </div>
          <div style="width: 70vw; height: 35vh; margin: 10px">
            <div
              style="
                width: 100%;
                height: 20%;
                padding-left: 2%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div style="width: 90%">제목</div>
              <div style="display: flex; width: 10%" @click="setLiked">
                <v-icon large v-if="likeTrigger == false" color="rgb(255,0,0)">
                  mdi-heart-outline
                </v-icon>
                <transition name="bounce">
                  <v-icon large v-if="likeTrigger == true" color="rgb(255,0,0)">
                    mdi-heart
                  </v-icon>
                </transition>
              </div>
            </div>
            <div style="height: 60%; padding-left: 2%">
              <table>
                <tr>
                  <td>종 / 품종</td>
                  <td>{{ this.animalInfo.kind_c }}</td>
                </tr>
                <tr>
                  <td>성별(중성화)</td>
                  <td>
                    {{ animalSex }}
                  </td>
                </tr>
                <tr>
                  <td>나이</td>
                  <td>{{ animalAge }}</td>
                </tr>
                <tr>
                  <td>몸무게</td>
                  <td>{{ this.animalInfo.weight }}</td>
                </tr>
                <tr>
                  <td>털색</td>
                  <td>{{ this.animalInfo.color_cd }}</td>
                </tr>
                <tr>
                  <td>성격</td>
                  <td>{{ animalTag }}</td>
                </tr>
              </table>
            </div>
            <div
              style="
                height: 20%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <v-btn
                width="95%"
                height="70%"
                color="rgb(1,118,72)"
                @click="moveTo('/adoption')"
                :disabled="this.adoptionBtn"
              >
                <div style="color: white">입양하기</div>
              </v-btn>
            </div>
          </div>
        </div>
      </div>
    </v-container>
  </v-app>
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

  created() {
    this.animalInfo = "";
    this.likeTrigger = false;

    if (this.animalInfo.like == false) {
      this.likeTrigger = true;
    } else {
      this.likeTrigger = false;
    }

    if (this.$cookies.get("accessToken") != null) {
      SERVER.tokenCheck(() => {
        axios
          .get(SERVER.URL + "/newuser/animal/detail", {
            params: {
              desertion_no: this.$route.params.animalID,
            },
          })
          .then((res) => {
            this.animalInfo = res.data.animalList;
            this.adoptionBtn = false;
            // console.log(res.data.animalList);
          })
          .catch((err) => {
            // console.log("user/animal/detail 요청 막힘");
            console.log(err);
            // axios
            //   .get(SERVER.URL + "/newuser/animal/detail", {
            //     params: {
            //       desertion_no: this.$route.params.animalID,
            //     },
            //   })
            //   .then((res) => {
            //     console.log(res.data);
            //     this.animalInfo = res.data.animalList;
            //   })
            //   .catch((err) => {
            //     console.log(err);
            //   });
          });
      });
    } else {
      SERVER.tokenCheck(() => {
        axios
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
      });
    }
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
      // axios
      //   // .get(SERVER.URL + `/user/adopt/read/${this.$route.params.animalID}`,
      //   // .get(SERVER.URL + `/user/animal/detail/${this.$route.params.animalID}`,
      //   .get(SERVER.URL + "/user/animal/detail", {
      //     params: {
      //       desertion_no: this.$route.params.animalID,
      //     },
      //     headers: {
      //       Authorization: this.$cookies.get("accessToken"),
      //     },
      //   })
      //   .then((res) => {
      //     console.log(res.data);
      //   if (res.data.success) {
      //     this.$router.push(
      //       {
      //         name: 'Adoption',
      //         params: {
      //           animalId: this.animalInfo.desertion_no
      //         }
      //       }
      //   )} else {
      //     alert('이미 신청했습니다.')
      //   }
      // })
      // .catch((err) => {
      //   console.log(err);
      //   alert("신청 이력이 있습니다.");
      //   this.$router.push("/adoptionlist");
      // });
      this.$router.push(page + `/${this.$route.params.animalID}`);
    },
  },
};
</script>

<style>
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