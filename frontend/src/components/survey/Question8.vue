<template>
  <div class="question1">
    <v-row>
      <h3>Q9. 반려동물과 여가시간을 어떤식으로 보내고 싶으신가요?</h3><hr>
    </v-row>
    <div>이미지</div>
    <v-row>
      <v-col @click="selectedDogMbti({idx: 1, answer: 1})" style="cursor:pointer">
        <h3>자주 놀아주고 싶다</h3>
        <div>이미지</div>
      </v-col>
      <v-col @click="selectedDogMbti({idx: 1, answer: -1})" style="cursor:pointer">
        <h3>서로의 휴식시간은 필요하다</h3>
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