<template>
  <div class="question">
    <div class="question-header">
      <h2>Q7. 새로산 장난감을 보았을 때 반려동물이 어떻게 행동하기를 원하시나요?</h2>
      <img src="@/assets/survey/1.png" alt="">
    </div>
    <div class="question-answer" style="display: flex; justify-content: space-around; align-item: center">
      <div class="selected-answer" @click="selectedDogMbti({idx: 6, answer: 1}); selectedAnswer(1)" style="cursor:pointer;">
        <h3>보호자가 자신에게 가져와서 놀아줄 때까지 기다린다</h3>
        <img src="@/assets/survey/1-1.png" alt="">
      </div>
      <div class="selected-answer" @click="selectedDogMbti({idx: 6, answer: -1}); selectedAnswer(1)" style="border: solid black; cursor:pointer">
        <h3>거부감 없이 잘 가지고 논다</h3>
        <img src="@/assets/survey/1-2.png" alt="">
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  methods: {
    ...mapMutations(['selectedDogMbti'])
  }
}
</script>

<style>

</style>