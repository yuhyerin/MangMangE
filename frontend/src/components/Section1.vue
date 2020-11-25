<template>
  <div class="section">
    <div v-for="(n, index) in 8" :key="index">
      <transition name="component-fade" mode="out-in">
        <div
          v-if="num == n"
          style="height: 100vh; width: 100%; position: absolute"
          :style="
            'background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),url(' +
            require(`../assets/image/mainpage/section${num}.jpg`) +
            '); background-repeat: no-repeat; background-size: cover;'
          "
        ></div>
      </transition>
    </div>
    <div style="display: flex">
      <div style="width: 50vw; height: 100vh"></div>
      <div
        style="
          width: 50vw;
          display: flex;
          justify-content: center;
          align-items: center;
        "
      >
        <div class="mainWindow" style="z-index: 1">
          <div style="height: 30%; padding-top: 20px; line-height: 220%">
            <h2 style="text-align: center">지금 당신에게 맞는</h2>
            <h2 style="text-align: center">반려동물을 찾아보세요</h2>
          </div>
          <div
            style="display: flex; width: 100%; justify-content: space-around"
          >
            <v-btn
              style="width: 8vw"
              color="rgb(1,118,72)"
              @click="moveTo('/survey')"
              ><div style="color: white">동물 매칭하기</div></v-btn
            >
            <v-btn
              style="width: 8vw"
              color="rgba(64,33,22)"
              @click="moveTo('/animals')"
              ><div style="color: white">모든 동물 보기</div></v-btn
            >
          </div>
          <div>
            <a class="moveToSection2" href="#page2"> HOW IT WORKS? </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
export default {
  name: "Section1",
  data() {
    return {
      num: 1,
    };
  },
  created() {
    setInterval(() => {
      this.numIncrease();
    }, 5000);
  },

  methods: {
    ...mapMutations(["setEventListener"]),
    ...mapMutations(["resetSurvey"]),

    moveTo(page) {
      if (page == "/animals") {
        this.setEventListener = 2;
      } else if (page == "/survey") {
        this.resetSurvey(null);
      }
      this.$router.push(page);
    },
    numIncrease() {
      this.num++;
      if (this.num > 8) {
        this.num = 1;
      }
    },
  },
};
</script>

<style scoped>
.s0 {
  background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),
    url("../assets/image/mainpage/section1.jpg");
  background-size: cover;
  max-width: 100vw;
  padding-top: 75px;
}

.slogan {
  text-align: center;
}

.match-box {
  margin-top: 20px;
  background-color: black;
  color: white;
  text-align: center;
  cursor: pointer;
  font-size: medium;
}

.match-box:hover {
  background-color: darkgray;
}

.all-animal {
  background-color: rgb(1, 118, 72);
  color: white;
  text-align: center;
  cursor: pointer;
  font-size: medium;
}

.all-animal:hover {
  background-color: lightgreen;
}

.howitworks {
  font-size: large;
  color: black;
  cursor: pointer;
  text-align: center;
  text-decoration: underline;
}

.howitworks:hover {
  background-color: gray;
}

a.moveToSection2 {
  border-bottom: 2px solid black;
  text-decoration: none;
  color: black;
}

a.moveToSection2:link {
  color: white;
}

a.moveToSection2:visited {
  color: black;
}

.mainWindow {
  width: 50%;
  height: 40%;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  margin-bottom: 50px;
}
</style>