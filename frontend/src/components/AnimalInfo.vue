<template>
  <div
    class="outLine"
    style="
      width: 90%;
      height: 30vh;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 20px;
    "
  >
    <div
      class="image"
      style="
        width: 30%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
      "
    >
      <img :src="this.animalInfo.popfile" alt="사진" style="width: 100%" />
    </div>
    <div style="width: 70%; height: 100%; margin-left: 5%">
      <div
        style="
          width: 100%;
          height: 20%;
          display: flex;
          justify-content: center;
          align-items: center;
        "
      >
        <div
          style="
            width: 92%;
            height: 100%;
            display: flex;
            justify-content: flex-start;
            align-items: center;
          "
        >
          <!-- <h3>No. {{ this.animalInfo.desertion_no }}</h3> -->
          <div
            v-if="this.matched == null"
            style="
              width: 90%;
              height: 92%;
              background-color: orange;
              border: 2px solid rgba(0, 0, 0, 0.4);
              color: white;
              text-align: center;
            "
          >
            당신과의 만남을 기다리고 있습니다.
          </div>
          <div
            v-else
            style="
              width: 90%;
              height: 92%;
              border: 2px solid rgba(0, 0, 0, 0.4);
              color: white;
              text-align: center;
              display: flex;
              justify-content: flex-start;
              align-items: center;
            "
          >
            <div
              :style="`width:${this.matched}%`"
              style="background-color: orange"
            >
              당신과의 적합성 : {{ this.matched }}%
            </div>
            <div></div>
          </div>
        </div>
        <div style="width: 8%; height: 100%">
          <Liked
            :dNo="this.animalInfo.desertion_no"
            :animalLike="this.animalInfo.like"
          />
        </div>
      </div>
      <div
        style="
          width: 100%;
          height: 50%;
          font-family: singleDay;
          font-size: 25px;
          display: flex;
          align-items: center;
          justify-content: flex-start;
        "
      >
        <div
          stlye="width:100%;"
          v-for="(tag, index) in this.animalTag"
          :key="index"
        >
          <div
            v-if="checkTag(tag) == 'E'"
            style="color: rgb(232, 99, 100); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'Q'"
            style="color: rgb(130, 157, 212); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'F'"
            style="color: rgb(89, 197, 173); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'C'"
            style="color: rgb(214, 120, 157); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'S'"
            style="color: rgb(238, 158, 85); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'I'"
            style="color: rgb(177, 148, 196); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'A'"
            style="color: rgb(123, 181, 123); margin-right: 10px"
          >
            #{{ tag }}
          </div>
          <div
            v-if="checkTag(tag) == 'W'"
            style="color: rgb(251, 202, 84); margin-right: 10px"
          >
            #{{ tag }}
          </div>
        </div>
      </div>
      <div style="width: 100%; height: 15%">
        {{ this.animalInfo.kind_c }} | {{ animalSex }} | {{ animalAge }}
      </div>
      <div
        style="
          width: 100%;
          height: 15%;
          display: flex;
          justify-content: flex-start;
          align-items: center;
        "
      >
        <v-btn
          text
          style="
            height: 100%;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            padding-left: 0;
          "
          @click="clicked"
        >
          <v-icon> mdi-magnify-plus </v-icon>
          <div>프로필 보기</div>
        </v-btn>
        <div
          v-if="this.animalInfo.special_mark != ','"
          style="width: 70%; height: 100%"
        >
          특징 : {{ this.animalInfo.special_mark }}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import SERVER from "@/api/url";
import Liked from "./Liked.vue";

export default {
  props: ["animalInfo", "perfect", "matched"],
  components: {
    Liked,
  },
  data() {
    return {
      moveTrigger: true,
      likeTrigger: false,
      tag: [],
    };
  },
  computed: {
    animalTag() {
      var tag = [];
      for (let i = 0; i < this.animalInfo.personality.length; i++) {
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

  methods: {
    clicked() {
      if (this.moveTrigger == true) {
        this.$router.push("/animalDetail" + `/${this.animalInfo.desertion_no}`);
      }
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
  },
};
</script>

<style scoped>
.outLine {
  padding: 20px;
  border: 2px solid rgba(0, 0, 0, 0.12);
}

.image img {
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transform: scale(1);
  -webkit-transition: 0.3s;
  -moz-transition: 0.3s;
  -ms-transition: 0.3s;
  -o-transition: 0.3s;
  transition: 0.3s;
}
.image:hover img {
  -webkit-transform: scale(1.2);
  -moz-transform: scale(1.2);
  -ms-transform: scale(1.2);
  -o-transform: scale(1.2);
  transform: scale(1.2);
}

@font-face {
  font-family: "singleDay";
  src: url("../assets/font/SingleDay-Regular.ttf") format("truetype");
}
</style>
