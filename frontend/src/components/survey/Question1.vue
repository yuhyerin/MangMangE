<template>
  <div class="quiz-box">
    <v-row style="position: absolute; top: 3%; left: 48%;">
    <v-avatar color="rgb(1,118,72)" size="50">
      <h3 style="color: white">Q1</h3>
    </v-avatar>
    </v-row>
    <div class="quiz-header1" style="text-align: center; padding-top: 25px;">
      <h2>내 MBTI를 선택해주세요.</h2>
      <p>
        만약 MBTI를 모른다면
        <a href="https://www.16personalities.com/ko/" target="_blank">여기</a>를
        눌러주세요
      </p>
    </div>
    <v-row class="quiz-img" style="display: flex; justify-content: space-around; padding: 7px">
      <img style="margin: 10px; width: 25%" src="@/assets/survey/Q1.png" alt=""/>
    </v-row>
    <v-row style="margin:0 10px 0 10px; padding:0 10px 0 10px">
      <v-col v-for="(mbti, index) in mbtiList" :key="mbti.id" cols="3">
        <div
            class="select-mbti"
            v-if="userMbti == mbtiList[index]"
            @click="selectedUserMbti(mbtiList[index])"
            color="rgb(1, 118, 72)"
            style="color: white;  background-color: rgb(1, 118, 72);"
            ><h4 style="padding: 7px;">{{ mbti }}</h4></div
          >
          <div class="select-mbti" v-else @click="selectedUserMbti(mbtiList[index])">
            <h4 style="padding: 7px;">{{ mbti }}</h4>
          </div>
      </v-col>
    </v-row>
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
.select-mbti {
  border-radius: 15px;
  cursor: pointer;
  background-color: #eee;
}
.select-mbti:hover{
  opacity: 0.8;
}
.quiz-header {
  margin-top: 20px;
}
.quiz-box {
  width: 50%; 
  margin: 10px auto; 
  border: solid rgb(1, 118, 72); 
  background-color: white; 
  border-radius: 15px;
}
</style>