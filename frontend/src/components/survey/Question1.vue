<template>
  <div class="question1">
    <v-row>
      <h1>Q2. 반려동물과 함께 선호하는 운동은?</h1><hr>
    </v-row>
    <img src="@/assets/survey/1.png" alt="">
    <v-row>
      <v-col @click="selectedDogMbti({idx: 1, answer: 1})" style="cursor:pointer" >
        <h3>체력소모가 있는 매우 활동적인 운동</h3>
        <img src="@/assets/survey/1-1.png" alt="">
      </v-col>
      <v-col @click="selectedDogMbti({idx: 1, answer: -1})" style="cursor:pointer">
        <h3>작은 공간에서도 간단히 할 수 있는 운동</h3>
        <img src="@/assets/survey/1-2.png" alt="">
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