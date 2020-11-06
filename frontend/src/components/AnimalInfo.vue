<template>
  <div
    class="outLine"
    style="
      width: 80%;
      height: 30vh;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 20px;
    "
  >
    <div
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
        </div>
        <div style="width: 8%; height: 100%">
          <Liked
            :dNo="this.animalInfo.desertion_no"
            :animalLike="this.animalInfo.like"
          />
        </div>
      </div>
      <div style="width: 100%; height: 50%">{{ animalTag }}</div>
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
        >
          <v-icon> mdi-magnify-plus </v-icon>
          <div>프로필 보기</div>
        </v-btn>
        <div style="width: 70%; height: 100%">여긴 뭐넣지</div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import SERVER from "@/api/url";
import Liked from "./Liked.vue";

export default {
  props: ["animalInfo", "perfect"],
  components: {
    Liked,
  },
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

  methods: {
    clicked() {
      if (this.moveTrigger == true) {
        this.$router.push("/animalDetail" + `/${this.animalInfo.desertion_no}`);
      }
    },
  },
};
</script>

<style scoped >
.outLine {
  padding: 20px;
  border: 2px solid rgba(0, 0, 0, 0.12);
}
</style>
