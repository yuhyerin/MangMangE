<template>
  <div>
    <Header />
    <v-row style="padding: 90px 0 10px 50px">
      <h1 style="padding-right: 10px">마이리틀댕댕 TV</h1>
      <img v-if="onair" src="@/assets/image/on-air.png" alt="" width="50px">
      <img v-else src="@/assets/image/off-air.png" alt="" width="50px">
    </v-row>
    <v-row style="display: flex; justify-content: center; align-items: center; background-color: rgb(193, 218, 204); height: 480px">
      <div style="width: 545px; height:295px; background-color: black;">
        <h3 style="color: white; text-align: center; margin-top:130px;">현재 방송중이 아닙니다.</h3>
      </div>
    </v-row>
    <v-row style="display: flex; justify-content: center; margin-top: 20px;">
      <div @click="onAir" class="onair-btn">
        <v-row style="display: flex; align-items: center; margin: 0 0 0 3px;">
          <v-col style="padding: 0" cols="5">
            <img src="@/assets/image/merong.png" alt="" width="40px" style="margin-top:1px">
          </v-col>
          <v-col style="padding: 0">
            <h4 v-if="onair" style="display: inline">방송 종료</h4>
            <h4 v-else style="">방송 시작</h4>
          </v-col>
        </v-row>
      </div>
    </v-row>
  </div>
</template>

<script>
import Header from "../components/Header.vue";

export default {
  components: {
    Header
  },
  data() {
    return {
      onair: false,
    }
  },
  methods: {
    onAir() {
      this.onair = !this.onair
    }
  }
}
</script>

<style scoped>
.onair-btn {
  cursor: pointer;
  border: solid black;
  border-radius: 7px;
  width: 9%;
  padding: 1px 3px 1px 3px;
}
.onair-btn:hover {
  background-color: #eeeeee;
}
</style>