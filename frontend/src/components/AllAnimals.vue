<template>
  <div class="flip">
    <div
      class="front"
      :style="{ 'background-image': 'url(' + animalInfo.popfile + ')' }"
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
    <div class="back" @click="clicked">
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
      <p>{{ animalInfo.sex_cd == "M" ? "수컷" : "암컷" }}</p>
      <p>{{ animalInfo.weight }} (추정)</p>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import SERVER from "@/api/url";

export default {
  props: ["animalInfo"],
  data() {
    return {
      moveTrigger: true,
      likeTrigger: false,
    };
  },
  created() {},

  methods: {
    clicked() {
      if (this.moveTrigger == true) {
        console.log("페이지 이동!!", this.animalInfo);
        this.$router.push("/animalDetail" + `/${this.animalInfo.desertion_no}`);
      }
    },
    setLiked() {
      if (this.moveTrigger == false) {
        if (this.likeTrigger == true) {
          // 좋아요 해제
          console.log("false");
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
        } else {
          // 좋아요 등록
          console.log("true");
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
        }
        this.likeTrigger = !this.likeTrigger;
      }
    },
    setMoveTrigger() {
      this.moveTrigger = !this.moveTrigger;
    },
  },
};
</script>

<style lang="scss" scoped src="../assets/css/Card.scss"></style>
