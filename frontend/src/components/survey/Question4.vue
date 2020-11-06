<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q4</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">반려동물이 낯선 사람을 보면 어떻게 행동하길 원하시나요?</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="quiz-choice1" @click="selectedDogMbti({ idx: 3, answer: 1 })">
      <h3 style="padding: 10px;">지켜보는 타입</h3>
    </v-row>
    <v-row class="quiz-choice2" @click="selectedDogMbti({ idx: 3, answer: -1 })">
      <h3 style="padding: 10px;">궁금해서 먼저 다가가는 타입</h3></v-row>
  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  methods: {
    ...mapMutations(["selectedDogMbti"]),
  },
};
</script>

<style>
</style>