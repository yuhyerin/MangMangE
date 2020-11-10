<template>
  <div class="quiz-box">
    <v-avatar class="quiz-num" color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q9</h3>
    </v-avatar>
    <v-row class="quiz-header">
      <h2 style="margin:10px; padding:5px;">여가시간에 나는 반려동물과...</h2>
    </v-row>
    <v-row class="quiz-img">
      <v-col class="answer-img" @click="submitSurvey({ idx: 8, answer: 1 }); selectedAnswer(1)" :style="answerImg1" cols="5">
        <h4 class="img-num">A.</h4><img style="margin:10px; width: 45%;" src="@/assets/survey/Q9A.png" alt=""/>
      </v-col>
      <v-col class="answer-img" @click="submitSurvey({ idx: 8, answer: -1 }); selectedAnswer(2)" :style="answerImg2" cols="5"> 
        <h4 class="img-num">B.</h4><img style="margin: 10px; width: 45%" src="@/assets/survey/Q9B.png" alt=""/>
      </v-col>
    </v-row>
    <v-row class="answer" @click="submitSurvey({ idx: 8, answer: 1 }); selectedAnswer(1)" :style="answer1">
      <h3 style="padding: 10px;">A. 자주 놀아주고 싶다</h3>
    </v-row>
    <v-row class="answer" @click="submitSurvey({ idx: 8, answer: -1 }); selectedAnswer(2)" :style="answer2">
      <h3 style="padding: 10px;">B. 서로의 휴식시간은 필요하다</h3></v-row>
  </div>
</template>

<script>
import { mapActions } from "vuex";
require('@/assets/css/survey.css')

export default {
  data() {
    return {
      answerImg1: {
        border: 'hidden',
        borderRadius: '0'
      },
      answerImg2: {
        borderStyle: 'hidden',
        borderRadius: '0'
      },
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
    ...mapActions(["submitSurvey"]),
    selectedAnswer(val) {
      if (val == 1) {
        this.answer1.color = 'white'
        this.answer1.backgroundColor = 'rgb(1, 118, 72)'
        this.answerImg1.border = 'solid rgb(1,118,72)'
        this.answerImg1.borderRadius = '5px'
        this.answer2.color = 'black'
        this.answer2.backgroundColor = '#eee'
        this.answerImg2.border = 'hidden'
        this.answerImg2.borderRadius = '0'
      }
      else if (val == 2 ) {
        this.answer2.color = 'white'
        this.answer2.backgroundColor = 'rgb(1, 118, 72)'
        this.answerImg2.border = 'solid rgb(1,118,72)'
        this.answerImg2.borderRadius = '5px'
        this.answer1.color = 'black'
        this.answer1.backgroundColor = '#eee'
        this.answerImg1.border = 'hidden'
        this.answerImg1.borderRadius = '0'
      }
    }  
  },
};
</script>

<style>
</style>