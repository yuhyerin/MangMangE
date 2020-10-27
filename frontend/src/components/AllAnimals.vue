<template>
  <div class="flip">
    <div class="front">
      <div
        style="
          display: flex;
          position: absolute;
          z-index: 1;
          left: 170px;
          top: 5px;
        "
        @mouseover="setMoveTrigger"
        @click="showConsole"
      >
        <v-icon v-if="likeTrigger == false" color="rgb(255,0,0)">
          mdi-heart-outline
        </v-icon>
        <v-icon v-if="likeTrigger == true" color="rgb(255,0,0)">
          mdi-heart
        </v-icon>
      </div>
      <h1 class="text-shadow">앞면</h1>
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
        @click="showConsole"
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
      <h2>뒷면 제목</h2>
      <p>뒷면1</p>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    storeData: {},
  },
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
      }
    },
    showConsole() {
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
