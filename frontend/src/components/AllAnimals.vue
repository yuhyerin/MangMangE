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
export default {
  props: ["animalInfo"],
  data() {
    return {
      moveTrigger: true,
      likeTrigger: false,
    };
  },
  methods: {
    clicked() {
      if (this.moveTrigger == true) {
        console.log("페이지 이동!!");
        this.$router.push("/animalDetail" + `/${this.animalInfo.desertion_no}`);
      }
    },
    setLiked() {
      if (this.moveTrigger == false) {
        console.log("즐겨찾기!!");
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
