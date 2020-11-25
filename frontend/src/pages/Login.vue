<template>
  <v-app>
    <div class="loading" v-if="loadingTrigger">
      <i class="fas fa-spinner fa-10x fa-spin"></i>
    </div>
    <div style="display: flex; height: 100%; width: 100%">
      <div
        class="homeBtn"
        style="position: absolute; top: 20px; left: 20px; font-size: 20px"
        @click="moveToHome"
      >
        <v-icon> mdi-arrow-left </v-icon>
        홈으로
      </div>
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: center;
          flex-direction: column;
          width: 100%;
          background-color: rgb(244, 236, 225);
        "
      >
        <div style="text-align: center">
          <img
            src="../assets/image/cogi.gif"
            alt="LoginBackImage"
            style="width: 100px"
          />

          <component
            :is="selectedComponent"
            @changeComponents="register"
            @isLoading="setLoadingTrigger"
          ></component>
          <!--           
          <LoginForm
            v-if="pageTrigger == 0"
            @changeComponents="register"
            @isLoading="setLoadingTrigger"
          />
          <RegisterForm v-if="pageTrigger == 1" @changeComponents="register" />
          <FindIdForm v-if="pageTrigger == 2" @changeComponents="register" />
          <FindPwForm v-if="pageTrigger == 3" @changeComponents="register" />
          <FindIdSubmit v-if="pageTrigger == 4" @changeComponents="register" />
          <FindPwSubmit v-if="pageTrigger == 5" @changeComponents="register" />
        --></div>
      </div>
      <div style="width: 100%; height: 100vh">
        <img
          src="../assets/image/backgroundImage.jpg"
          alt="LoginBackImage"
          style="width: 100%; height: 100%; object-fit: cover; opacity: 0.7"
        />
      </div>
    </div>
  </v-app>
</template>

<script>
import LoginForm from "../components/accounts/LoginForm";
import RegisterForm from "../components/accounts/RegisterFrom";
import FindIdForm from "../components/accounts/FindIdForm";
import FindPwForm from "../components/accounts/FindPwForm";
import FindIdSubmit from "../components/accounts/FindIdSubmit";
import FindPwSubmit from "../components/accounts/FindPwSubmit";
import { mapState } from "vuex";

export default {
  components: {
    LoginForm,
    RegisterForm,
    FindIdForm,
    FindPwForm,
    FindIdSubmit,
    FindPwSubmit,
  },
  data() {
    return {
      pageTrigger: 0,
      loadingTrigger: false,
      selectedComponent: "LoginForm",
    };
  },
  computed: {
    ...mapState(["eventListener"]),
  },
  created() {
    if (this.eventListener == 3) {
      this.pageTrigger = 1;
    } else if (this.eventListener) {
      this.pageTrigger = 0;
    }
  },
  methods: {
    register(value) {
      this.selectedComponent = value;
    },
    moveToHome() {
      location.href = "/";
    },
    setLoadingTrigger(value) {
      console.log(value);
      this.loadingTrigger = value;
    },
  },
};
</script>

<style>
.homeBtn:hover {
  cursor: pointer;
  border-bottom: 2px solid gray;
}
.loading {
  height: 100vh;
  width: 100vw;
  position: absolute;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 5px;
  background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4));
  /* background-image: linear-gradient(
    rgba(255, 255, 255, 0.4),
    rgba(255, 255, 255, 0.4)
  ); */
}
</style>