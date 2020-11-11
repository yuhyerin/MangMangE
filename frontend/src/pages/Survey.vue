<template>
  <v-app id="app">
    <v-container>
      <v-stepper v-model="page" style="box-shadow: 0 0 black; height: 90vh">
        <v-stepper-header style="box-shadow: 0 0 black">
          <v-stepper-step
            @click="goPage(1)"
            :complete="survey[0] !== 0"
            step="1"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(2)"
            :complete="survey[1] !== 0"
            step="2"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(3)"
            :complete="survey[2] !== 0"
            step="3"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(4)"
            :complete="survey[3] !== 0"
            step="4"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(5)"
            :complete="survey[4] !== 0"
            step="5"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(6)"
            :complete="survey[5] !== 0"
            step="6"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(7)"
            :complete="survey[6] !== 0"
            step="7"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            @click="goPage(8)"
            :complete="survey[7] !== 0"
            step="8"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
          </v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step
            step="9"
            @click="goPage(9)"
            color="rgb(1, 118, 72)"
            style="cursor: pointer"
          >
            <!-- Name of step 3 -->
          </v-stepper-step>
        </v-stepper-header>
        <v-stepper-items
          class="survey-items"
          style="display: flex; text-align: center; align-items: center"
        >
          <v-stepper-content step="1">
            <Question1 />
          </v-stepper-content>

          <v-stepper-content step="2">
            <Question2 />
          </v-stepper-content>

          <v-stepper-content step="3">
            <Question3 />
          </v-stepper-content>

          <v-stepper-content step="4">
            <Question4 />
          </v-stepper-content>

          <v-stepper-content step="5">
            <Question5 />
          </v-stepper-content>

          <v-stepper-content step="6">
            <Question6 />
          </v-stepper-content>

          <v-stepper-content step="7">
            <Question7 />
          </v-stepper-content>

          <v-stepper-content step="8">
            <Question8 />
          </v-stepper-content>

          <v-stepper-content step="9">
            <Question9 />
          </v-stepper-content>
        </v-stepper-items>
      </v-stepper>
      <div style="width: 100%; display: flex; justify-content: flex-end">
        <div style="font-weight: bold; color: red; cursor: pointer" @click="cancelSurvey">
          다음에 하기
        </div>
      </div>
    </v-container>
  </v-app>
</template>

<script>
import { mapState, mapMutations } from "vuex";

import Question1 from "../components/survey/Question1";
import Question2 from "../components/survey/Question2";
import Question3 from "../components/survey/Question3";
import Question4 from "../components/survey/Question4";
import Question5 from "../components/survey/Question5";
import Question6 from "../components/survey/Question6";
import Question7 from "../components/survey/Question7";
import Question8 from "../components/survey/Question8";
import Question9 from "../components/survey/Question9";

export default {
  components: {
    Question1,
    Question2,
    Question3,
    Question4,
    Question5,
    Question6,
    Question7,
    Question8,
    Question9,
  },
  computed: {
    ...mapState(["page", "survey"]),
  },
  methods: {
    ...mapMutations(["goPage"]),
    ...mapMutations(["resetSurvey"]),
    cancelSurvey() {
      this.resetSurvey(null);
      location.href = "/";
    },
  },
};
</script>

<style scoped>
.survey-items {
  height: 85vh;
  transform-origin: center top 0px;
  border-radius: 50px;
}
</style>
