<template>
  <v-app>
    <Header />
    <v-content>
      <v-container style="padding-top: 75px">
        <div style="display: flex; height: 87vh">
          <div
            style="
              width: 20vw;
              background-color: rgb(244, 236, 225);
              border-radius: 20px;
              margin: 5px;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            "
          >
            <div>
              <div class="categoryBtn">
                <v-btn text color="black" @click="setTrigger(1)">
                  모든 아이들 보기 &nbsp;
                </v-btn>
                <v-icon v-if="trigger == 1" large color="black">
                  mdi-paw-outline
                </v-icon>
              </div>
              <div class="categoryBtn">
                <v-btn text color="black" @click="setTrigger(2)">
                  나와 맞는 아이는? &nbsp;
                </v-btn>
                <v-icon v-if="trigger == 2" large color="black">
                  mdi-paw-outline
                </v-icon>
              </div>
              <div class="categoryBtn" style="">
                <v-btn text color="black" @click="setTrigger(3)">
                  즐겨 찾는 아이들 &nbsp;
                </v-btn>
                <v-icon v-if="trigger == 3" large color="black">
                  mdi-paw-outline
                </v-icon>
              </div>
            </div>
            <div style="text-align: center">
              필터
              <img
                v-if="testTrigger == false"
                src="../assets/image/1wait.gif"
                alt="멍멍"
                @click="test"
              />
              <img
                v-if="testTrigger == true"
                src="../assets/image/1pop.gif"
                alt="멍멍"
              />
            </div>
          </div>
          <div
            style="
              width: 80vw;
              background-color: rgb(244, 236, 225);
              border-radius: 20px;
              margin: 5px;
              align-text: center;
            "
          >
            <div style="display: flex; flex-wrap: wrap; justify-content: left">
              <AllAnimals v-if="trigger == 1" v-for="i in tmp" :key="i" />
              <AllAnimals v-if="trigger == 2" v-for="i in tmp" :key="i" />
              <AllAnimals v-if="trigger == 3" v-for="i in tmp" :key="i" />
            </div>
          </div>
        </div>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import AllAnimals from "../components/AllAnimals.vue";
import Header from "../components/Header.vue";
import { mapGetters, mapMutations } from "vuex";

export default {
  data() {
    return {
      trigger: 0,
      tmp: 0,
      testTrigger: false,
    };
  },
  components: {
    AllAnimals,
    Header,
  },
  watch: {
    trigger(newValue, oldValue) {
      if (newValue == 1) {
        this.tmp = 10;
        console.log("All Animals");
      } else if (newValue == 2) {
        this.tmp = 4;
        console.log("matched Animals");
      } else {
        this.tmp = 2;
        console.log("like animals");
      }
    },
  },
  created() {
    if (this.$store.getters.getPageCheck == 0) {
      this.trigger = 0;
    } else if (this.$store.getters.getPageCheck == 1) {
      this.trigger = 1;
    }
    checkThisPage(0);
  },
  methods: {
    ...mapGetters(["getPageCheck"]),
    ...mapMutations(["checkThisPage"]),
    setTrigger(num) {
      this.trigger = num;
    },
    test() {
      this.testTrigger = true;
      setTimeout(() => {
        this.testTrigger = false;
      }, 3300);
    },
  },
};
</script>

<style>
.categoryBtn {
  padding-top: 10px;
  height: 7vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>