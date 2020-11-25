<template>
  <div style="width: 200px; margin: 20px">
    <div class="flip">
      <div
        class="front"
        :style="{
          'background-image': 'url(' + animalInfo.popfile + ')',
        }"
      >
        <div
          style="
            display: flex;
            position: absolute;
            z-index: 1;
            left: 170px;
            top: 5px;
          "
          @mouseover="setMoveTrigger"
          @mouseout="setMoveTrigger"
          @click="setLiked"
        >
          <!-- <img
            v-if="likeTrigger == false"
            src="../assets/image/unlike1.png"
            alt="좋아요"
            style="width: 30px"
          />
          <img
            v-if="likeTrigger == true"
            src="../assets/image/like1-1.png"
            alt="좋아요"
            style="width: 30px"
          /> -->
          <v-icon v-if="likeTrigger == false" color="rgb(255,0,0)">
            mdi-heart-outline
          </v-icon>
          <v-icon v-if="likeTrigger == true" color="rgb(255,0,0)">
            mdi-heart
          </v-icon>
        </div>
        <!-- <h1 class="text-shadow">{{ data.desertion_no }}</h1> -->
        <!-- <img :src="data.filename"  alt="동물이미지" /> -->
      </div>
      <div
        class="back"
        :style="{
          'background-image':
            'linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),url(' +
            animalInfo.popfile +
            ')',
        }"
        @click="clicked"
      >
        <div
          style="
            display: flex;
            position: absolute;
            z-index: 1;
            left: 170px;
            top: 5px;
          "
          @click="setLiked"
          @mouseover="setMoveTrigger"
          @mouseout="setMoveTrigger"
        >
          <v-icon v-if="likeTrigger == false" color="rgb(255,0,0)">
            mdi-heart-outline
          </v-icon>
          <v-icon v-if="likeTrigger == true" color="rgb(255,0,0)">
            mdi-heart
          </v-icon>
        </div>

        <h2>{{ animalInfo.kind_cd }}</h2>
        <p>일련번호 : {{ animalInfo.desertion_no }}</p>
        <br />
        <p>{{ animalAge }} (추정)</p>
        <p>{{ animalSex }}</p>
        <p>{{ animalInfo.weight }} kg (추정)</p>
      </div>
    </div>
    <div
      style="display: flex; justify-content: center; align-items: flex-start; flex; flex-direction: column; width: 90%"
    >
      <div
        style="
          display: flex;
          justify-content: center;
          align-items: center;
          width: 100%;
        "
      >
        <div
          :style="
            this.animalInfo.personality[0].length > 5
              ? 'font-size: 15px'
              : 'font-size: 15px'
          "
          style="width: 100%; font-weight: bold"
        >
          # {{ this.animalInfo.personality[0] }}
        </div>
        <div
          :style="
            this.animalInfo.personality[0].length > 5
              ? 'font-size: 15px'
              : 'font-size: 15px'
          "
          style="width: 100%; font-weight: bold"
        >
          # {{ this.animalInfo.personality[1] }}
        </div>
      </div>
      <div
        style="
          display: flex;
          justify-content: center;
          align-items: center;
          width: 100%;
        "
      >
        <div
          :style="
            this.animalInfo.personality[0].length > 5
              ? 'font-size: 15px'
              : 'font-size: 15px'
          "
          style="width: 100%; font-weight: bold"
        >
          # {{ this.animalInfo.personality[2] }}
        </div>
        <div
          :style="
            this.animalInfo.personality[0].length > 5
              ? 'font-size: 15px'
              : 'font-size: 15px'
          "
          style="width: 100%; font-weight: bold"
        >
          # {{ this.animalInfo.personality[3] }}
        </div>
      </div>
    </div>
    <!-- <div style="display: flex; align-items: center">
      <v-icon> mdi-hail </v-icon>
      <div>&nbsp; 215 &nbsp;</div>
      <v-icon small> mdi-heart </v-icon>
      <div>&nbsp;4</div>
    </div> -->
    <!-- <div>만약 이런식으로 글 제목이 들어간다면 어떨까</div> -->
  </div>
</template>
<script>
import axios from "axios";
import SERVER from "@/api/url";

export default {
  props: ["animalInfo", "perfect"],
  data() {
    return {
      moveTrigger: true,
      likeTrigger: false,
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
    this.likeTrigger = false;
    if (this.animalInfo.like == false) {
      this.likeTrigger = false;
    } else {
      this.likeTrigger = true;
    }
  },

  methods: {
    clicked() {
      if (this.moveTrigger == true) {
        this.$router.push("/animalDetail" + `/${this.animalInfo.desertion_no}`);
      }
    },
    setLiked() {
      if (this.$cookies.get("accessToken") == null) {
        alert("로그인이 필요한 서비스 입니다.");
      } else {
        if (this.moveTrigger == false) {
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
      }
    },
    setMoveTrigger() {
      this.moveTrigger = !this.moveTrigger;
    },
  },
};
</script>

<style lang="scss" scoped src="../assets/css/Card.scss">
</style>
