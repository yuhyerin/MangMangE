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
          <LoginForm v-if="pageTrigger == false" @changeComponents="register" />
          <RegisterForm v-else @changeComponents="register" />
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
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterFrom";

export default {
  components: {
    LoginForm,
    RegisterForm,
  },
  data() {
    return {
      pageTrigger: false,
    };
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