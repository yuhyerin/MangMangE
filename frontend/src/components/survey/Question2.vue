<template>
  <div class="quiz-box">
    <v-avatar style=" position: absolute; top: 7%; left: 48%;" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q2</h3>
    </v-avatar>
    <v-row class="quiz-header" style="display: flex; justify-content: space-around; margin:10px">
      <h2 style="margin:10px; padding:5px;">반려동물과 함께 선호하는 운동은?</h2>
    </v-row>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; margin:10px">
      <img style="margin:10px" src="@/assets/survey/1.png" alt="" />
    </v-row>
    <v-row class="answer" @click="selectedDogMbti({ idx: 1, answer: 1 }); selectedAnswer(1)" :style="answer1">
      <h3 style="padding: 10px;">체력소모가 있는 매우 활동적인 운동</h3>
    </v-row>
    <v-row class="answer" @click="selectedDogMbti({ idx: 1, answer: -1 }); selectedAnswer(2)" :style="answer2">
      <h3 style="padding: 10px;">작은 공간에서도 간단히 할 수 있는 운동</h3>
    </v-row>
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
}
</script>

<style>
/* .question-header {
  background-color: white;
} */
.answer {
  cursor: pointer; 
  display: flex; 
  justify-content: space-around; 
  margin: 10px; 
}
</style>