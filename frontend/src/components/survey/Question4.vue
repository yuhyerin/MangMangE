<template>
  <div class="question">
    <div class="question-header">
      <h2>Q4. 반려동물이 낯선 사람을 보면 어떻게 행동하길 원하시나요?</h2>
      <img src="@/assets/survey/1.png" alt="" />
    </div>
    <div
      class="question-answer"
      style="display: flex; justify-content: space-around; align-item: center"
    >
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 3, answer: 1 })"
        style="cursor: pointer"
      >
        <h3>지켜보는 타입</h3>
        <img src="@/assets/survey/1-1.png" alt="" />
      </div>
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 3, answer: -1 })"
        style="border: solid black; cursor: pointer"
      >
        <h3>궁금해서 먼저 다가가는 타입</h3>
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