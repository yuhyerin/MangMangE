<template>
  <div class="mbti">
    <div class="question">
      <h1>Q1. 내 MBTI를 선택해주세요.</h1>
      <p>만약 내 MBTI를 모른다면 <a href="https://www.16personalities.com/ko/" target="_blank">여기</a>를 눌러주세요</p>
    </div>
    <div class="mbti-buttons">
      <!-- <v-row>
        <v-col cols="12" sm="6" class="py-2">
          <v-btn-toggle v-model="toggle_exclusive" v-for="mbti in mbtiList" :key="mbti.id">
            <v-btn>
              <v-icon>{{mbti}}</v-icon>
            </v-btn> 
          </v-btn-toggle>
        </v-col>
      </v-row> -->
      <v-row style="padding:0px">
        <v-col v-for="(mbti, index) in mbtiList" :key="mbti.id" cols="3">
          <v-btn v-if="userMbti==mbtiList[index]" color="rgb(1, 118, 72)" style="color:white">{{ mbti }}</v-btn>
          <v-btn v-else @click="selectedUserMbti(mbtiList[index])">{{ mbti }}</v-btn>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'

export default {
  data() {
    return {
      mbtiList: [
        'ISTJ', 'ISFJ', 'INFJ', 'INTJ',
        'ISTP', 'ISFP', 'INFP', 'INTP',
        'ESTP', 'ESFP', 'ENFP', 'ENTP',
        'ESTJ', 'ESFJ', 'ENFJ', 'ENTJ',
      ]
    }
  },
  computed: mapState(['userMbti']),
  methods: {
    ...mapMutations(['selectedUserMbti'])
  }
}
</script>

<style>

</style>