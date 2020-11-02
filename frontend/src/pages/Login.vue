<template>
  <v-app>
    <div style="display: flex; height: 100%; width: 100%">
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: center;
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
          <LoginForm v-if="pageTrigger == 0" @changeComponents="register" />
          <RegisterForm v-if="pageTrigger == 1" @changeComponents="register" />
          <FindIdForm v-if="pageTrigger == 2" @changeComponents="register" />
          <FindPwForm v-if="pageTrigger == 3" @changeComponents="register" />
          <FindIdSubmit v-if="pageTrigger == 4" @changeComponents="register" />
          <FindPwSubmit v-if="pageTrigger == 5" @changeComponents="register" />
        </div>
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
      this.pageTrigger = value;
    },
  },
};
</script>

<style>
</style>