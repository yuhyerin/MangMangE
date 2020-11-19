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
      <div style="display: flex; cursor: pointer" @click="back">
        <v-icon color="black"> mdi-arrow-left </v-icon>
      </div>
      <v-col align="align">
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black"> mdi-paw-outline </v-icon>
          <h1>아이디 찾기</h1>
          <v-icon large color="black"> mdi-paw-outline </v-icon>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black" style="padding: 17px 10px 0 0">
            mdi-paw-outline
          </v-icon>
          <v-text-field
            v-model="name"
            label="성명"
            :rules="ruleName"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black" style="padding: 17px 10px 0 0">
            mdi-paw-outline
          </v-icon>
          <v-text-field
            v-model="email"
            label="이메일"
            :rules="ruleEmail"
            hide-details="auto"
          ></v-text-field>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: space-around">
          <v-btn color="rgb(1,118,72)" width="100px" @click="submit"
            ><p style="color: white; padding-top: 14px">확인</p></v-btn
          >
          <!-- <v-btn color="rgba(64,33,22)" width="100px" @click="moveToRegister"
            ><p style="color: white; padding-top: 14px">비밀번호 찾기</p></v-btn
          > -->
        </div>
      </v-col>
    </v-col>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/url";
import { mapMutations } from "vuex";

export default {
  data() {
    return {
      // @ 유효한 이메일 형식인지 체크
      ruleEmail: [(value) => !!value || "이메일을 입력해 주세요."],
      ruleName: [(value) => !!value || "이름을 입력해 주세요."],
      email: "",
      name: "",
    };
  },

  methods: {
    ...mapMutations(["setFindUserId"]),
    back() {
      this.$emit("changeComponents", "LoginForm");
    },
    submit() {
      if (this.email == "") {
        alert("이름을 입력해 주세요");
      } else if (this.name == "") {
        alert("이메일을 입력해 주세요.");
      } else {
        axios
          .post(
            SERVER.URL + "/newuser/findid",
            {
              user_name: this.name,
              user_email: this.email,
            },
            {}
          )
          .then((res) => {
            //user_id == null의 경우 없는 회원
            this.setFindUserId(res.data.user_id);
            this.$emit("changeComponents", "FindIdSubmit");
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
  },
};
</script>

<style scoped>
</style>