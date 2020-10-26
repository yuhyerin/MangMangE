<template>
  <div class="question1">
    <v-row>
      <h1>Q2. 반려동물과 어떤 관계를 원하시나요?</h1><hr>
    </v-row>
    <div>이미지</div>
    <v-row>
      <v-col @click="selectedDogMbti({idx: 1, answer: 1})" style="cursor:pointer">
        <h3>애인같이 서로 죽고 못사는 관계</h3>
        <div>이미지</div>
      </v-col>
      <v-col @click="selectedDogMbti({idx: 1, answer: -1})" style="cursor:pointer">
        <h3>룸메이트 같은 적당히 독립적인 관계</h3>
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