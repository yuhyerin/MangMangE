<template>
    <div class="question">
    <div class="question-header">
      <h2>Q9. 반려동물과 여가시간을 어떤식으로 보내고 싶으신가요?</h2>
      <img src="@/assets/survey/1.png" alt="">
    </div>
    <div class="question-answer" style="display: flex; justify-content: space-around; align-item: center">
      <div class="selected-answer" @click="selectedDogMbti({idx: 8, answer: 1}); selectedAnswer(1)" style="cursor:pointer;">
        <h3>자주 놀아주고 싶다</h3>
        <img src="@/assets/survey/1-1.png" alt="">
      </div>
      <div class="selected-answer" @click="selectedDogMbti({idx: 8, answer: -1}); selectedAnswer(1)" style="border: solid black; cursor:pointer">
        <h3>서로의 휴식시간은 필요하다</h3>
        <img src="@/assets/survey/1-2.png" alt="">
      </div>
    </div>
  </div>

</template>

<script>
import { mapMutations, mapActions } from 'vuex'

export default {
  methods: {
    ...mapMutations(['whatIsDogMbti']),
    ...mapActions(['submitSurvey'])
  }
}
</script>

<style>

</style>