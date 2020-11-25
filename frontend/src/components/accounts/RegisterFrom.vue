<template>
  <div
    style="
      border: 1px solid rgb(224, 222, 219);
      border-radius: 10%;
      background-color: rgb(224, 222, 219);
      width: 30vw;
      height: 70vh;
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
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon large color="black"> mdi-paw-outline </v-icon>
          <h1>회원가입</h1>
          <v-icon large color="black"> mdi-paw-outline </v-icon>
        </div>
      </v-col>
      <v-col align="align">
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="id"
            label="ID"
            :rules="ruleID"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="password"
            label="비밀번호"
            :rules="rulePW"
            hide-details="auto"
            :append-icon="showpassword ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showpassword ? 'text' : 'password'"
            @click:append="showpassword = !showpassword"
          ></v-text-field>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="passwordCheck"
            label="비밀번호 확인"
            :rules="rulePWC"
            hide-details="auto"
            :append-icon="showpasswordCheck ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showpasswordCheck ? 'text' : 'password'"
            @click:append="showpasswordCheck = !showpasswordCheck"
            @change="checkPassword"
          ></v-text-field>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="email"
            label="이메일"
            :rules="ruleEmail"
            hide-details="auto"
          ></v-text-field>
          <v-btn style="margin: 20px 0 0 15px" @click="emailSend">전송</v-btn>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="checkNum"
            label="인증번호"
            hide-details="auto"
            :disabled="this.emailAuthentication"
            :value="this.emailAuthentication == true ? this.checkNum : ''"
          ></v-text-field>
          <v-btn
            style="margin: 20px 0 0 15px"
            @click="emailCheck"
            :disabled="authenticationBtnTrigger"
            >인증</v-btn
          >
        </div>
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="name"
            label="성명"
            :rules="ruleName"
            hide-details="auto"
          ></v-text-field>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: space-around">
          <v-btn color="rgba(64,33,22)" width="100px" @click="register"
            ><p style="color: white; padding-top: 14px">가입하기</p></v-btn
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
      rulePW: [(value) => !!value || "비밀번호를 입력해 주세요."],
      rulePWC: [(value) => !!value || "비밀번호를 입력해 주세요."],
      rulePWC: [
        (value) => !!(value == this.password) || "비밀번호를 확인해 주세요.",
      ],
      ruleEmail: [(value) => !!value || "이메일을 입력해 주세요."],
      ruleName: [(value) => !!value || "이름을 입력해 주세요."],

      id: "",
      password: "",
      passwordCheck: "",
      email: "",

      origin_hash: "",
      checkNum: "",

      passwordCheckResult: false,
      authenticationBtnTrigger: true,
      registerBtnTrigger: false,

      name: "",
      showpassword: "",
      showpasswordCheck: "",

      emailAuthentication: false,
    };
  },
  methods: {
    back() {
      this.$emit("changeComponents", "LoginForm");
    },

    emailSend() {
      axios
        .get(SERVER.URL + "/newuser/signup", {
          params: {
            email: this.email,
          },
        })
        .then((res) => {
          if (res.data.origin_hash == null) {
            alert("이미 가입된 이메일 입니다.");
          } else {
            this.authenticationBtnTrigger = false;
            this.origin_hash = res.data.origin_hash;
          }
        })
        .catch((err) => {
          console.log("catch err : ", err);
        });
    },

    emailCheck() {
      axios
        .get(SERVER.URL + "/newuser/signup/hashcheck", {
          params: {
            auth_number: this.checkNum,
            hash_number: this.origin_hash,
          },
        })
        .then((res) => {
          console.log(res.data.result);
          if (res.data.result == true) {
            this.emailAuthentication = true;
          } else {
            this.emailAuthentication = false;
            alert("인증번호가 다릅니다.");
          }
        })
        .catch((err) => {
          console.log("catch err : ", err);
        });
    },
    register() {
      this.checkForm();
      if (this.registerBtnTrigger == true) {
        axios
          .post(SERVER.URL + "/newuser/signup", {
            user_id: this.id,
            user_email: this.email,
            user_password: this.password,
            user_name: this.name,
          })
          .then((res) => {
            this.$emit("changeComponents", "LoginForm");
          })
          .catch((err) => {
            console.log("catch err : ", err);
          });
      } else {
        alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
      }
    },
    checkPassword() {
      if (this.password == this.passwordCheck) {
        this.passwordCheckResult = true;
      }
    },

    checkForm() {
      if (this.id == null) {
        alert("아이디를 입력해 주세요.");
      }
      if (this.email == null) {
        alert("이메일을 입력해 주세요.");
      }
      if (this.password == null) {
        alert("비밀번호를 입력해 주세요.");
      }
      if (this.passwordCheck == null) {
        alert("비밀번호를 확인해 주세요.");
      }
      if (this.name == null) {
        alert("이름을 입력해 주세요.");
      }

      if (
        this.id != null &&
        this.password != null &&
        this.passwordCheckResult == true &&
        this.email != null &&
        this.name != null &&
        this.emailAuthentication == true
      ) {
        this.registerBtnTrigger = true;
      }
    },
  },
};
</script>

<style scoped>
</style>