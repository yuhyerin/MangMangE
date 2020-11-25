<template>
  <v-app>
    <Header />
    <div v-for="(n, index) in 4" :key="index">
      <transition name="component-fade" mode="out-in">
        <div
          v-if="num == n"
          style="height: 100vh; width: 100%; position: absolute"
          :style="
            'background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),url(' +
            require(`../assets/image/mainpage/section${num}.jpg`) +
            '); background-repeat: no-repeat; background-size: cover;'
          "
        >
          <div style="padding-top: 80px">
            <v-btn @click="numIncrease">{{ num }}</v-btn>
          </div>
        </div>
      </transition>
    </div>
  </v-app>
</template>

<script>
import Header from "../components/Header.vue";
export default {
  components: {
    Header,
  },
  data() {
    return {
      num: 1,
    };
  },
  components: {},
  created() {
    setInterval(() => {
      this.numIncrease();
    }, 5000);
  },
  methods: {
    numIncrease() {
      this.num = (this.num + 1) % 4;
    },

    test() {
      console.log("hihi");
    },
  },
};
</script>
<style>
.component-fade-enter-active {
  transition: opacity 4s ease;
}

.component-fade-leave-active {
  transition: opacity 4s ease;
}
.component-fade-enter, .component-fade-leave-to
/* .component-fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>