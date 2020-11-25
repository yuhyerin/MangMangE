<template>
  <div>
    <Header />
    <div
      class="backBtn"
      style="position: fixed; top: 90px; left: 5%"
      @click="moveToBack"
    >
      <div style="display: flex; justify-content: center; height: 50px">
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon x-large>mdi-arrow-left </v-icon>
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
          height: 87vh;
        "
      >
        <div class="border" style="width: 30%; height: 100%">
          <div
            style="
              width: 100%;
              height: 80%;
              display: flex;
              flex-direction: column;
              align-items: center;
              justify-content: space-around;
            "
          >
            <h1>내 성향은?</h1>
            <div>활동성향</div>
            <hr style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)" />
            <div
              style="
                height: 5%;
                width: 100%;
                border: 1px solid rgba(0, 0, 0, 0.3);
                border-radius: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-left-radius: inherit;
                  border-bottom-left-radius: inherit;
                  text-align: center;
                  vertical-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[0]) == 'E'
                    ? 'background-color: rgb(232, 99, 100);'
                    : ''
                "
              >
                동적성향
              </div>
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-right-radius: inherit;
                  border-bottom-right-radius: inherit;
                  text-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[0]) == 'Q'
                    ? 'background-color: rgb(130, 157, 212);'
                    : ''
                "
              >
                정젹성향
              </div>
            </div>
            <div>관계성향</div>
            <hr style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)" />
            <div
              style="
                height: 5%;
                width: 100%;
                border: 1px solid rgba(0, 0, 0, 0.3);
                border-radius: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-left-radius: inherit;
                  border-bottom-left-radius: inherit;
                  text-align: center;
                  vertical-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[2]) == 'S'
                    ? 'background-color: rgb(238,158,85);'
                    : ''
                "
              >
                관계지향
              </div>
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-right-radius: inherit;
                  border-bottom-right-radius: inherit;
                  text-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[2]) == 'I'
                    ? 'background-color: rgb(177, 148, 196);'
                    : ''
                "
              >
                독립지향
              </div>
            </div>
            <div>적응성향</div>
            <hr style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)" />
            <div
              style="
                height: 5%;
                width: 100%;
                border: 1px solid rgba(0, 0, 0, 0.3);
                border-radius: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-left-radius: inherit;
                  border-bottom-left-radius: inherit;
                  text-align: center;
                  vertical-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[3]) == 'W'
                    ? 'background-color: rgb(251, 202, 84);'
                    : ''
                "
              >
                신중형
              </div>
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-right-radius: inherit;
                  border-bottom-right-radius: inherit;
                  text-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[3]) == 'A'
                    ? 'background-color: rgb(123, 181, 123);'
                    : ''
                "
              >
                능동형
              </div>
            </div>
            <div>순종성향</div>
            <hr style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)" />
            <div
              style="
                height: 5%;
                width: 100%;
                border: 1px solid rgba(0, 0, 0, 0.3);
                border-radius: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-left-radius: inherit;
                  border-bottom-left-radius: inherit;
                  text-align: center;
                  vertical-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[1]) == 'F'
                    ? 'background-color: rgb(89, 197, 173);'
                    : ''
                "
              >
                충직함
              </div>
              <div
                style="
                  width: 100%;
                  height: 100%;
                  border-top-right-radius: inherit;
                  border-bottom-right-radius: inherit;
                  text-align: center;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
                :style="
                  this.checkTag(animalTag[1]) == 'C'
                    ? 'background-color: rgb(214, 120, 157);'
                    : ''
                "
              >
                영민함
              </div>
            </div>
          </div>
        </div>
        <div
          class="border"
          style="
            width: 40%;
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
          <div
            style="
              width: 100%;
              height: 40%;
              display: flex;
              justify-content: space-around;
              align-items: flex-start;
              flex-direction: column;
            "
          >
            <div style="display: flex; width: 100%">
              <div style="font-size: 30px; width: 80%">특징</div>
              <div style="width: 20%; height: 100%">
                <div style="display: flex; width: 100%" @click="setLiked">
                  <v-icon
                    large
                    v-if="likeTrigger == false"
                    color="rgb(255,0,0)"
                  >
                    mdi-heart-outline
                  </v-icon>
                  <transition name="bounce">
                    <v-icon
                      large
                      v-if="likeTrigger == true"
                      color="rgb(255,0,0)"
                      style="position: absolute"
                    >
                      mdi-heart
                    </v-icon>
                  </transition>
                </div>
              </div>
            </div>
            <div
              style="
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: flex-start;
              "
            >
              <div style="width: 20%">품종</div>
              <div style="width: 80%; font-weight: bold">
                {{ this.animalInfo.kind_c }}
              </div>
            </div>
            <div
              style="
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div style="width: 20%">성별</div>
              <div style="width: 80%; font-weight: bold">{{ animalSex }}</div>
            </div>
            <div
              style="
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div style="width: 20%">나이</div>
              <div style="width: 80%; font-weight: bold">
                {{ animalAge }}(추정)
              </div>
            </div>
            <div
              style="
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div style="width: 20%">몸무게</div>
              <div style="width: 80%; font-weight: bold">
                {{ this.animalInfo.weight }} kg
              </div>
            </div>
            <div
              style="
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div style="width: 20%">털색</div>
              <div style="width: 80%; font-weight: bold">
                {{ this.animalInfo.color_cd }}
              </div>
            </div>
            <div style="width: 20%">성격</div>
            <diV
              style="
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div v-for="(tag, index) in animalTag" :key="index">
                <div style="margin-right: 5px; font-weight: bold">
                  #{{ tag }}
                </div>
              </div>
            </diV>
          </div>
          <hr style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)" />
          <div
            style="
              width: 100%;
              height: 50%;
              display: flex;
              justify-content: center;
              align-items: center;
              flex-direction: column;
            "
          >
            <div v-if="this.video != null">
              <div>대표 동영상</div>
              <video controls style="max-height: 505px; width: 100%">
                <source
                  :src="`https://k3b306.p.ssafy.io/allVideos/${video.filepath}`"
                  type="video/mp4"
                />
              </video>
            </div>
            <div v-else>동영상이 없습니다.</div>
          </div>
          <hr style="width: 90%; border: 1px solid rgba(0, 0, 0, 0.12)" />
          <div
            style="
              width: 100%;
              height: 10%;
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
              :disabled="this.adoptionBtn || this.admin"
            >
              <div v-if="!this.adoptionBtn && !this.admin" style="color: white">
                입양하기
              </div>
              <div
                v-else-if="
                  !this.admin && this.$cookies.get('accessToken') != null
                "
                style="color: white"
              >
                입양 심사가 진행 중입니다.
              </div>
              <div
                v-else-if="this.$cookies.get('accessToken') == null"
                style="color: white"
              >
                로그인 후 이용 가능합니다.
              </div>
              <div v-else style="color: white">입양 신청을 할 수 없습니다.</div>
            </v-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import axios from "axios";
import SERVER from "@/api/url";
import Liked from "../components/Liked.vue";

export default {
  components: {
    Header,
    Liked,
  },
  data() {
    return {
      likeTrigger: false,
      animalInfo: "",
      adoptionBtn: "",
      video: "",
      admin: false,
    };
  },
  computed: {
    animalTag() {
      var tag = [];
      for (let i = 0; i < Object(this.animalInfo.personality).length; i++) {
        tag[i] = this.animalInfo.personality[i];
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
              .then((res) => {})
              .catch((err) => {
                console.log(err);
              });
          });
        } else {
          // 좋아요 등록
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
              .then((res) => {})
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
      axios
        .get(SERVER.URL + "/newuser/video/search", {
          params: {
            desertion_no: this.$route.params.animalID,
          },
        })
        .then((res) => {
          if (res.data.videoList != null) {
            this.video = res.data.videoList[0];
          } else {
            this.video = null;
          }
        });
    },

    checkTag(tag) {
      if (
        tag == "활동적인" ||
        tag == "에너지뿜뿜" ||
        tag == "활발한" ||
        tag == "민첩한"
      ) {
        return "E";
      } else if (
        tag == "차분한" ||
        tag == "조용한" ||
        tag == "침착한" ||
        tag == "느긋한"
      ) {
        return "Q";
      } else if (
        tag == "충성심높은" ||
        tag == "책임감있는" ||
        tag == "온순한"
      ) {
        return "F";
      } else if (
        tag == "똑똑한" ||
        tag == "경계심많은" ||
        tag == "영리한" ||
        tag == "총명한"
      ) {
        return "C";
      } else if (
        tag == "사교적인" ||
        tag == "애착있는" ||
        tag == "친근감넘치는" ||
        tag == "장난끼많은"
      ) {
        return "S";
      } else if (tag == "고집이센" || tag == "자립심강한" || tag == "냉담한") {
        return "I";
      } else if (
        tag == "진취적인" ||
        tag == "적극적인" ||
        tag == "호기심많은"
      ) {
        return "A";
      } else if (
        tag == "신중한" ||
        tag == "방어적인" ||
        tag == "낯가리는" ||
        tag == "수줍음많은"
      ) {
        return "W";
      }
    },
    moveToBack() {
      this.$router.push("/animals");
    },
  },
};
</script>

<style>
.backBtn:hover {
  border-bottom: 2px solid rgb(0, 0, 0, 0.3);
  cursor: pointer;
}
.border {
  border: 1px solid rgba(0, 0, 0, 0.12);
  padding: 10px;
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