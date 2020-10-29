<template>
  <div class="question">
    <div class="question-header">
      <h2>Q8. 내 반려동물은 미련한 곰? 눈치빠른 여우?</h2>
      <img src="@/assets/survey/1.png" alt="" />
    </div>
    <div
      class="question-answer"
      style="display: flex; justify-content: space-around; align-item: center"
    >
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 7, answer: 1 })"
        style="cursor: pointer"
      >
        <h3>미련한 곰</h3>
        <img src="@/assets/survey/1-1.png" alt="" />
      </div>
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 7, answer: -1 })"
        style="border: solid black; cursor: pointer"
      >
        <h3>눈치빠른 여우</h3>
        <img src="@/assets/survey/1-2.png" alt="" />
      </div>
    </div>
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