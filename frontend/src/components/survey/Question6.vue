<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">

      <h3 style="color: white">Q6</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">반려동물이 어떤 성향이었으면 좋겠나요?</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="answer" @click="selectedDogMbti({ idx: 5, answer: 1 }); selectedAnswer(1)" :style="answer1">
      <h3 style="padding: 10px;">누군가와 함께 있는걸 좋아하는 성향</h3>
    </v-row>
    <v-row class="answer" @click="selectedDogMbti({ idx: 5, answer: -1 }); selectedAnswer(2)" :style="answer2">
      <h3 style="padding: 10px;">혼자서도 잘 지내는 성향</h3></v-row>
  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data() {
    return {
      answer1: {
        color: 'black',
        backgroundColor: '#eee'
      },
      answer2: {
        color: 'black',
        backgroundColor: '#eee'
      },
    }
  },
  methods: {
    ...mapMutations(["selectedDogMbti"]),
    selectedAnswer(val) {
      if (val == 1) {
        this.answer1.color = 'white',
        this.answer1.backgroundColor = 'rgb(1, 118, 72)'
        this.answer2.color = 'black',
        this.answer2.backgroundColor = '#eee'
      }
      else if (val == 2 ) {
        this.answer2.color = 'white',
        this.answer2.backgroundColor = 'rgb(1, 118, 72)'
        this.answer1.color = 'black',
        this.answer1.backgroundColor = '#eee'
      }
    }
  },
};
</script>

<style>
</style>