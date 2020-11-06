<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q3</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">반려동물과 원하는 관계는?</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="quiz-choice1" @click="selectedDogMbti({ idx: 2, answer: 1 }); choice(1)">
      <h3 style="padding: 10px;">애인같이 서로 죽고 못사는 관계</h3>
    </v-row>
    <v-row class="quiz-choice2" @click="selectedDogMbti({ idx: 2, answer: -1 }); choice(2)">
      <h3 style="padding: 10px;">룸메이트 같이 적당히 독립적인 관계</h3></v-row>
  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  methods: {
    ...mapMutations(["selectedDogMbti"]),
    // choice(val) {
    //   var answer1 = 
    //   if (val == 1) {

    //   }
    // }
  },
};
</script>

<style>
</style>