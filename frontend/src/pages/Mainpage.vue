<template>
  <div>
    <Header />
    <full-page ref="fullpage" :options="options" id="fullpage">
      <Section1 />

      <Section2 />

      <Section3 />

      <Section4 />
    </full-page>
  </div>
</template>

<script>
import Section1 from "../components/Section1.vue";
import Section2 from "../components/Section2.vue";
import Section3 from "../components/Section3.vue";
import Section4 from "../components/Section4.vue";
import Header from "../components/Header.vue";
import { mapMutations, mapState } from "vuex";

export default {
  name: "Mainpage",
  components: {
    Section1,
    Section2,
    Section3,
    Section4,
    Header,
  },
  data() {
    return {
      options: {
        scrollBar: false,
        navigation: true,
        menu: "#menu",
        anchors: ["page1", "page2", "page3", "page4"],
        sectionsColor: [null, null, null, null],
      },
    };
  },
  created() {
    this.setEventListener(-1);
  },
  methods: {
    ...mapMutations(["setEventListener"]),
  },
};
</script>

<style>
</style>