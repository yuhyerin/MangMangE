<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q7</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">새로 산 장난감을 보았을 때 내 반려동물은?</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="quiz-choice1" @click="selectedDogMbti({ idx: 6, answer: 1 })">
      <h3 style="padding: 10px;">보호자가 자신에게 가져와서 놀아줄 때까지 기다린다</h3>
    </v-row>
    <v-row class="quiz-choice2" @click="selectedDogMbti({ idx: 6, answer: -1 })">
      <h3 style="padding: 10px;">거부감없이 잘 가지고 논다</h3></v-row>
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