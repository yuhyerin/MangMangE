<template>
  <div class="question1">
    <v-row>
      <h1>Q7. 새로산 장난감을 보았을 때 반려동물이 어떻게 행동하기를 원하시나요?</h1><hr>
    </v-row>
    <div>이미지</div>
    <v-row>
      <v-col @click="selectedDogMbti({idx: 2, answer: 1})" style="cursor:pointer">
        <h3>보호자가 자신에게 가져와서 놀아줄 때까지 기다린다</h3>
        <div>이미지</div>
      </v-col>
      <v-col @click="selectedDogMbti({idx: 2, answer: -1})" style="cursor:pointer">
        <h3>거부감 없이 잘 가지고 논다</h3>
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