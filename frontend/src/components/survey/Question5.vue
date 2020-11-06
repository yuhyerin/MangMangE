<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q5</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">아침에 반려동물이 어떻게 행동했으면 좋겠나요?</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="quiz-choice1" @click="selectedDogMbti({ idx: 4, answer: 1 })">
      <h3 style="padding: 10px;">혼자 잘 논다</h3>
    </v-row>
    <v-row class="quiz-choice2" @click="selectedDogMbti({ idx: 4, answer: -1 })">
      <h3 style="padding: 10px;">다가와서 애교를 부린다</h3></v-row>
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