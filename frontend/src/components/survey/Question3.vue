<template>
  <div class="question" style="height: 100%">
    <div class="question-header">
      <h2>Q3. 반려동물과 어떤 관계를 원하시나요?</h2>
      <img src="@/assets/survey/1.png" alt="" />
    </div>
    <div
      class="question-answer"
      style="display: flex; justify-content: space-around; align-item: center"
    >
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 2, answer: 1 })"
        style="cursor: pointer"
      >
        <h3>애인같이 서로 죽고 못사는 관계</h3>
        <img src="@/assets/survey/1-1.png" alt="" />
      </div>
      <div
        class="selected-answer"
        @click="selectedDogMbti({ idx: 2, answer: 1 })"
        style="cursor: pointer"
      >
        <h3>룸메이트 같은 적당히 독립적인 관계</h3>
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