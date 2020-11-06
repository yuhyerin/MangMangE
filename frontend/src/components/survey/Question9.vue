<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q9</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">여가시간에 나는 반려동물과...</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="quiz-choice1" @click="submitSurvey({ idx: 8, answer: 1 })">
      <h3 style="padding: 10px;">자주 놀아주고 싶다</h3>
    </v-row>
    <v-row class="quiz-choice2" @click="submitSurvey({ idx: 8, answer: -1 })">
      <h3 style="padding: 10px;">서로의 휴식시간은 필요하다</h3></v-row>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions(["submitSurvey"]),
  },
};
</script>

<style>
</style>