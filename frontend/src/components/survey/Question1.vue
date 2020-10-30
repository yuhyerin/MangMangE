<template>
  <div class="mbti" style="text-align: center">
    <div class="question">
      <h2>Q1. 내 MBTI를 선택해주세요.</h2>
      <p>
        만약 내 MBTI를 모른다면
        <a href="https://www.16personalities.com/ko/" target="_blank">여기</a>를
        눌러주세요
      </p>
    </div>
    <div class="mbti-buttons">
      <v-row>
        <v-col v-for="(mbti, index) in mbtiList" :key="mbti.id" cols="3">
          <v-btn
            v-if="userMbti == mbtiList[index]"
            color="rgb(1, 118, 72)"
            style="color: white"
            >{{ mbti }}</v-btn
          >
          <v-btn v-else @click="selectedUserMbti(mbtiList[index])">{{
            mbti
          }}</v-btn>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  data() {
    return {
      mbtiList: [
        "ISTJ",
        "ISFJ",
        "INFJ",
        "INTJ",
        "ISTP",
        "ISFP",
        "INFP",
        "INTP",
        "ESTP",
        "ESFP",
        "ENFP",
        "ENTP",
        "ESTJ",
        "ESFJ",
        "ENFJ",
        "ENTJ",
      ],
    };
  },
  computed: mapState(["userMbti"]),
  methods: {
    ...mapMutations(["selectedUserMbti"]),
  },
};
</script>

<style>
</style>