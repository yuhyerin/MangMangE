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
      <v-col align="align">
        <!--가입한 회원이면 ~~ / 가입하지 않은 회원이면 ~~-->
        <h1>아이디는 {{ this.user_id }}입니다.</h1>
      </v-col>
      <v-col align="align">
        <!--가입한 회원이면 로그인 화면으로-->
        <div style="display: flex; justify-content: space-around">
          <v-btn color="rgb(1,118,72)" width="100px" @click="submit"
            ><p style="color: white; padding-top: 14px">확인</p></v-btn
          >
          <!--가입하지 않은 회원이면-->
          <!-- <v-btn color="rgba(64,33,22)" width="100px" @click="moveToRegister"
            ><p style="color: white; padding-top: 14px">회원가입</p></v-btn
          > -->
        </div>
      </v-col>

      <!--가입한 회원만-->
      <v-col align="align" style="font-size: 0.8em">
        <div style="display: flex; justify-content: center">
          <p style="padding-right: 5px">혹시 비밀번호를 잊으셨나요?</p>
          <p class="find-pw" @click="moveToFindPw"><u>비밀번호 찾기</u></p>
        </div>
      </v-col>
    </v-col>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      user_id: "",
    };
  },

  computed: {
    ...mapState(["findUserId"]),
  },
  created() {
    this.user_id = this.findUserId;
  },

  methods: {
    submit() {
      this.$emit("changeComponents", "LoginForm");
    },
    moveToFindPw() {
      this.$emit("changeComponents", "FindPwForm");
    },
    moveToRegister() {
      this.$emit("changeComponents", "RegisterForm");
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