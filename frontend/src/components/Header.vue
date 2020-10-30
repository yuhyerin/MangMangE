<template>
  <!-- <v-app-bar id="app-toolbar" app color="white">
    <v-icon>mdi-view-list</v-icon>
    <v-spacer />
  </v-app-bar> -->
  <div
    style="
      position: fixed;
      height: 75px;
      padding: 1rem;
      color: black;
      background: white;
      font-weight: bold;
      display: flex;
      justify-content: space-between;
      align-items: center;
      top: 0;
      left: 0;
      right: 0;
      z-index: 5;
      border-bottom: 1px solid gray;
    "
  >
    <div @click="moveToMain">로고</div>
    <div>
      <div
        style="
          display: flex;
          justify-content: flex-end;
          align-items: center;
          font-size: 10px;
        "
      >
        <v-btn x-small text color="black">
          <div>회원가입</div>
        </v-btn>
        <!-- <div style="margin: 2px 5px 2px 5px">회원가입</div> -->
        <v-btn x-small text>
          <div>로그인 / 로그아웃</div>
        </v-btn>
      </div>
      <div style="display: flex; justify-content: center; align-items: center">
        <v-btn text @click="moveTo('/animals')">
          <div>동물 보기</div>
        </v-btn>
        <v-btn text @click="moveTo('/videos')">
          <div>동영상 게시판</div>
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    moveToMain() {
      location.href = "/";
    },
    moveTo(page) {
      this.$router.push(page);
    },
  },
};
</script>

<style>
.navBtn {
  margin: 2px 5px 2px 5px;
}
.navBtn:hover {
  padding: 2px 5px 2px 5px;
  background-color: rgb(180, 180, 180);
}
</style>