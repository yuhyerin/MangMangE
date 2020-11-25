<template>
  <div
    style="
      border: 1px solid rgb(224, 222, 219);
      border-radius: 10%;
      background-color: rgb(224, 222, 219);
      width: 30vw;
      height: 50vh;
      display: flex;
      align-items: center;
      justify-content: center;
    "
  >
    <v-col lg="10">
      <v-col align="align" style="text-align: left">
        <h1>가입된 이메일로</h1>
        <h1>임시 비밀번호를</h1>
        <h1>전송했습니다😄</h1>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: space-around">
          <v-btn color="rgb(1,118,72)" width="100px" @click="submit"
            ><p style="color: white; padding-top: 14px">확인</p></v-btn
          >
        </div>
      </v-col>
    </v-col>
  </div>
</template>

<script>
export default {
  methods: {
    submit() {
      this.$emit("changeComponents", "LoginForm");
    },
  },
};
</script>

<style scoped>
.find-pw:hover {
  cursor: pointer;
  font-weight: bold;
}
.find-pw {
  text-decoration: blue;
}
</style>