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
          <h1>비밀번호 찾기</h1>
          <v-icon large color="black"> mdi-paw-outline </v-icon>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black" style="padding: 17px 10px 0 0">
            mdi-paw-outline
          </v-icon>
          <v-text-field
            v-model="id"
            label="ID"
            :rules="ruleID"
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
        </div>
      </v-col>
    </v-col>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/url";

export default {
  data() {
    return {
      ruleID: [(value) => !!value || "ID를 입력해 주세요."],

      ruleEmail: [(value) => !!value || "이메일을 입력해 주세요."],
      id: "",
      email: "",
    };
  },
  methods: {
    back() {
      this.$emit("changeComponents", "LoginForm");
    },
    submit() {
      if (this.id == "") {
        alert("아이디를 입력해 주세요.");
      } else if (this.email == "") {
        alert("이메일을 입력해 주세요.");
      } else {
        axios
          .post(SERVER.URL + "/newuser/findpw", {
            user_id: this.id,
            user_email: this.email,
          })
          .then((res) => {
            if (res.data.result == "fail") {
              alert("가입된 내역이 없습니다.");
            } else {
              this.$emit("changeComponents", 5);
            }
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