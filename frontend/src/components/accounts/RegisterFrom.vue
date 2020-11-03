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
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black"> mdi-paw-outline </v-icon>
          <h1>회원가입</h1>
          <v-icon large color="black"> mdi-paw-outline </v-icon>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: center; align-item: center">
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
        <div style="display: flex; justify-content: center; align-item: center">
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
        <div style="display: flex; justify-content: center; align-item: center">
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
          ></v-text-field>
        </div>
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon color="black" style="padding: 17px 10px 0 0">
            mdi-bone
          </v-icon>
          <v-text-field
            v-model="email"
            label="이메일"
            :rules="ruleEmail"
            hide-details="auto"
          ></v-text-field>
        </div>
        <div style="display: flex; justify-content: center; align-item: center">
          <v-btn style="margin: 20px 0 0 15px" @click="emailSubmit">인증</v-btn>
          <v-text-field
            v-model="checkNum"
            label="이메일"
            :rules="ruleEmail"
            hide-details="auto"
          ></v-text-field>
          <v-btn style="margin: 20px 0 0 15px" @click="emailCheck">인증</v-btn>
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
          <v-btn color="rgba(64,33,22)" width="100px" @click="submit"
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

      name: "",
      showpassword: "",
      showpasswordCheck: "",
    };
  },
  methods: {
    back() {
      this.$emit("changeComponents", 0);
    },

    emailSubmit() {
      axios
        .get(SERVER.URL + "/newuser/signup", {
          params: {
            email: this.email,
          },
        })
        .then((res) => {
          console.log("then res : ", res.data.origin_hash);
          this.origin_hash = res.data.origin_hash;
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
          console.log(res.data);
        })
        .catch((err) => {
          console.log("catch err : ", err);
        });
    },
    submit() {
      console.log(
        this.id,
        this.password,
        this.passwordCheck,
        this.email,
        this.name
      );
    },
  },
};
</script>

<style scoped>
</style>