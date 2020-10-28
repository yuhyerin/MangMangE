<template>
  <div class="question">
    <div class="question-header">
      <h2>Q2. 반려동물과 함께 선호하는 운동은?</h2>
      <img src="@/assets/survey/1.png" alt="">
    </div>
    <div class="question-answer" style="display: flex; justify-content: space-around; align-item: center">
      <div class="selected-answer" @click="selectedDogMbti({idx: 1, answer: 1})" style="cursor:pointer;">
        <h3>체력소모가 있는 매우 활동적인 운동</h3>
        <img src="@/assets/survey/1-1.png" alt="">
      </div>
      <div class="selected-answer" @click="selectedDogMbti({idx: 1, answer: -1})" style="border: solid black; cursor:pointer">
        <h3>작은 공간에서도 간단히 할 수 있는 운동</h3>
        <img src="@/assets/survey/1-2.png" alt="">
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
  data() {
    return {
      selected: false,
    }
  },
  computed: mapState(['survey']),
  methods: {
    ...mapMutations(['selectedDogMbti']),
  }
}
</script>

<style>
.selected-answer:hover {
  background-color: #eeeeee;
}
</style>