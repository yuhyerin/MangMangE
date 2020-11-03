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
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black"> mdi-paw-outline </v-icon>
          <h1>로그인</h1>
          <v-icon large color="black"> mdi-paw-outline </v-icon>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black" style="padding: 17px 10px 0 0">
            mdi-paw-outline
          </v-icon>
          <v-text-field
            label="ID"
            :rules="ruleID"
            hide-details="auto"
            v-model="id"
          ></v-text-field>
        </div>
        <div style="display: flex; justify-content: center; align-item: center">
          <v-icon large color="black" style="padding: 17px 10px 0 0">
            mdi-paw-outline
          </v-icon>
          <v-text-field
            label="password"
            :rules="rulePW"
            v-model="password"
            hide-details="auto"
            :append-icon="showpassword ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showpassword ? 'text' : 'password'"
            @click:append="showpassword = !showpassword"
          ></v-text-field>
        </div>
      </v-col>
      <v-col align="align">
        <div style="display: flex; justify-content: space-around">
          <v-btn color="rgb(1,118,72)" width="100px" @click="login"
            ><p style="color: white; padding-top: 14px">로그인</p></v-btn
          >
          <v-btn color="rgba(64,33,22)" width="100px" @click="moveToRegister"
            ><p style="color: white; padding-top: 14px">회원가입</p></v-btn
          >
        </div>
      </v-col>
      <v-col align="align" style="font-size: 0.8em">
        <div style="display: flex; justify-content: center">
          <p class="find-id" @click="moveToFindId">아이디 찾기</p>
          <p style="padding: 0px 5px">/</p>
          <p class="find-pw" @click="moveToFindPw">비밀번호 찾기</p>
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
      id: "",
      password: "",
      showpassword: "",
    };
  },
  methods: {
    moveToRegister() {
      this.$emit("changeComponents", 1);
    },
    moveToFindId() {
      this.$emit("changeComponents", 2);
    },
    moveToFindPw() {
      this.$emit("changeComponents", 3);
    },
    // logout() {
    // axios.get(baseURL+'user/animal/surveyread/',{
    //   headers:{
    //     "Authorization" : this.$cookies.get("accessToken")
    //   }
    // })
    // .then((res)=>{
    //   console.log(res)
    // })
    // .catch((err)=>{
    //   console.log(err)
    // })
    //   axios
    //     .post(
    //       SERVER.URL + "user/logout/",
    //       {
    //         headers: {
    //           Authorization: this.$cookies.get("accessToken"),
    //           "content-type": "application/json",
    //         },
    //       },
    //       {
    //         accessToken: this.$cookies.get("accessToken"),
    //       }
    //     )
    //     .then((res) => {
    //       console.log(res);
    //       this.$cookies.remove("accessToken");
    //       this.$cookies.remove("refreshToken");
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },

    login() {
      axios
        .post(SERVER.URL + "/newuser/login/", {
          user_id: this.id,
          user_password: this.password,
        })
        .then((res) => {
          console.log(res);
          this.$cookies.set("accessToken", res.data.accessToken);
          this.$cookies.set("refreshToken", res.data.refreshToken);
          this.$router.push("/");
          // res.data.accessToken
          // res.data.refreshToken
          // this.$cookies.set("Authorization", res.data.accessToken)
          //   axios.post(baseURL+'user/logout',
          // { "accessToken" : this.$cookies.get("Authorization") },
          // {
          //     headers:{
          //       "Authorization": this.$cookies.get("Authorization")
          //     }
          // })
          // .then((res)=>{
          //   console.log(res)
          // });
        })
        .catch((err) => {
          // 없는 유저 아니면 서버 안 킨거
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.find-id:hover {
  cursor: pointer;
  font-weight: bold;
}
.find-pw:hover {
  cursor: pointer;
  font-weight: bold;
}
</style>