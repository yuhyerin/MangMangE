<template>
  <div class="question">
    <div class="question-header">
      <h2>Q5. 아침에 반려동물이 어떻게 행동했으면 좋겠나요?</h2>
      <img src="@/assets/survey/1.png" alt="" />
    </div>
    <div
      class="question-answer"
      style="display: flex; justify-content: space-around; align-item: center"
    >
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 4, answer: 1 })"
        style="cursor: pointer"
      >
        <h3>얌전히 기다린다</h3>
        <img src="@/assets/survey/1-1.png" alt="" />
      </div>
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 4, answer: -1 })"
        style="border: solid black; cursor: pointer"
      >
        <h3>보호자에게 다가와서 애교를 부린다</h3>
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