<template>
  <div class="question">
    <div class="question-header">
      <h2>Q6. 내 반려동물은 어떤 성향이길 원하나요?</h2>
      <img src="@/assets/survey/1.png" alt="" />
    </div>
    <div
      class="question-answer"
      style="display: flex; justify-content: space-around; align-item: center"
    >
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 5, answer: 1 })"
        style="cursor: pointer"
      >
        <h3>누군가와 함께있는걸 좋아하는 경향</h3>
        <img src="@/assets/survey/1-1.png" alt="" />
      </div>
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 5, answer: -1 })"
        style="border: solid black; cursor: pointer"
      >
        <h3>혼자서도 사색을 즐기는 경향</h3>
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