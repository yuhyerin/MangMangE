<template>
  <div class="live-chat">
    <Header />
    <v-row style="padding: 80px 0 0 70px">
      <h1 style="padding-right: 10px">마이리틀댕댕 TV</h1>
      <img v-if="onair" src="@/assets/image/on-air.png" alt="" width="50px">
      <img v-else src="@/assets/image/off-air.png" alt="" width="50px">
    </v-row>

    <v-row style="padding-left: 20px; padding-right: 20px;">
      <v-col cols="9">
        <div class="video-play" style="width: 100%; height: 100%; background-color: black">
          영상자리
        </div>
        <v-row>
          <v-col>
            2029288명 시청중
          </v-col>
          <v-col style="display: flex; justify-content: flex-end">
            <div @click="StartBtn" class="onair-btn">
              <v-row style="display: flex; align-items: center; margin: 0;">
                <v-col style="padding: 0" cols="5">
                  <img src="@/assets/image/startbutton.png" alt="" width="40px" style="margin-top:1px">
                </v-col>
                <v-col style="padding: 0">
                  <h4 v-if="onair" style="display: inline">방송 종료</h4>
                  <h4 v-else style="">방송 시작</h4>
                </v-col>
              </v-row>
            </div>
          </v-col>
        </v-row>
      </v-col>

      <v-col cols="3" style="border: 1px solid black; margin-top: 10px; height: 500px;">
        <div class="chatting">
          <p style="font-size: 1.1rem">채팅</p>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Header from "../components/Header.vue";

export default {
  components: {
    Header,
  },
  data() {
    return {
      onair: false,
    }
  },
  methods: {
    startBtn() {
      // 모달창 띄우기
    }
  }
}
</script>

<style scoped>
.onair-btn {
  cursor: pointer;
  border: solid black;
  border-radius: 7px;
  width: 25%;
  padding: 1px 3px 1px 3px;
}
.onair-btn:hover {
  background-color: #eeeeee;
}
</style>