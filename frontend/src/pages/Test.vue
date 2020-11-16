<template>
  <v-app>
    <Header />
    <!-- <transition name="component-fade" mode="out-in">
      <div
        v-if="num"
        style="height: 100vh; width: 50%"
        :style="
          'background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),url(' +
          require(`../assets/image/mainpage/section1.jpg`) +
          '); background-repeat: no-repeat; background-size: cover;'
        "
      >
        <div style="padding-top: 80px">
          <v-btn @click="num = !num">{{ num }}</v-btn>
        </div>
      </div>
    </transition>
    <transition name="component-fade" mode="out-in">
      <div
        v-if="!num"
        style="height: 100vh; width: 50%; margin-left: 50%"
        :style="
          'background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),url(' +
          require(`../assets/image/mainpage/section2.jpg`) +
          '); background-repeat: no-repeat; background-size: cover;'
        "
      >
        <div style="padding-top: 80px">
          <v-btn @click="num = !num">{{ num }}</v-btn>
        </div>
      </div>
    </transition> -->
    <component v-bind:is="view"></component>
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
      num: true,
      view: "v-a",
    };
  },
  components: {
    "v-a": {
      template: "<div>Component A</div>",
    },
    "v-b": {
      template: "<div>Component B</div>",
    },
  },
  methods: {
    numIncrease() {
      this.num++;
      if (this.num > 4) {
        this.num = 1;
      }
    },
  },
};
</script>
<style>
.component-fade-enter-active,
.component-fade-leave-active {
  transition: opacity 3s ease;
}
.component-fade-enter, .component-fade-leave-to
/* .component-fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>