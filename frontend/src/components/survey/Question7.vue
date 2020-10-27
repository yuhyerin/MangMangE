<template>
  <div class="question1">
    <v-row>
      <h1>Q8. 내 반려동물은 미련한 곰? 눈치빠른 여우?33</h1><hr>
    </v-row>
    <div>이미지</div>
    <v-row>
      <v-col @click="selectedDogMbti({idx: 7, answer: 1})" style="cursor:pointer">
        <h3>미련한 곰</h3>
        <div>이미지</div>
      </v-col>
      <v-col @click="selectedDogMbti({idx: 7, answer: -1})" style="cursor:pointer">
        <h3>눈치빠른 여우</h3>
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