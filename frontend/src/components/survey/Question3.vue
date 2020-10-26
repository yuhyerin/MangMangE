<template>
  <div class="question1">
    <v-row>
      <h1>Q3. 반려동물이 낯선 사람을 보면 어떻게 행동하길 원하시나요?</h1><hr>
    </v-row>
    <div>이미지</div>
    <v-row>
      <v-col @click="selectedDogMbti({idx: 2, answer: 1})" style="cursor:pointer">
        <h3>지켜보는 타입</h3>
        <div>이미지</div>
      </v-col>
      <v-col @click="selectedDogMbti({idx: 2, answer: -1})" style="cursor:pointer">
        <h3>궁금해서 먼저 다가가는 타입</h3>
        <div>이미지</div>
      </v-col>
    </v-row>
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