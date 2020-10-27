<template>
  <div class="survey">
    <v-container >
      <v-app id="inspire">
        <v-stepper v-model="page">
          <v-stepper-header>
            <v-stepper-step
              @click="goPage(1)"
              :complete="page > 1"
              step="1"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>
      
            <v-stepper-step
              @click="goPage(2)"
              :complete="page > 2"
              step="2"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>

            <v-stepper-step
              @click="goPage(3)"
              :complete="page > 3"
              step="3"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>

            <v-stepper-step
              @click="goPage(4)"
              :complete="page > 4"
              step="4"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>

            <v-stepper-step
              @click="goPage(5)"
              :complete="page > 5"
              step="5"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>

            <v-stepper-step
              @click="goPage(6)"
              :complete="page > 6"
              step="6"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>

            <v-stepper-step
              @click="goPage(7)"
              :complete="page > 7"
              step="7"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>

            <v-stepper-step
              @click="goPage(8)"
              :complete="page > 8"
              step="8"
              color="rgb(1, 118, 72)"
            >
            </v-stepper-step>
      
            <v-divider></v-divider>
      
            <v-stepper-step step="9" @click="goPage(9)" color="rgb(1, 118, 72)">
              <!-- Name of step 3 -->
            </v-stepper-step>
          </v-stepper-header>


          <v-stepper-items>
            <v-stepper-content step="1">
              <Mbti />
            </v-stepper-content>
      
            <v-stepper-content step="2">
              <Question1 />
            </v-stepper-content>
      
            <v-stepper-content step="3">
              <Question2 />
              <!-- <v-card
                class="mb-12"
                color="grey lighten-1"
                height="200px"
              ></v-card> -->

              <!-- <v-btn
                color="primary"
                @click="e1 = 1"
              >
                Continue
              </v-btn>
      
              <v-btn text>
                Cancel
              </v-btn> -->
            </v-stepper-content>

            <v-stepper-content step="4">
              <Question3 />
            </v-stepper-content>

            <v-stepper-content step="5">
              <Question4 />
            </v-stepper-content>

            <v-stepper-content step="6">
              <Question5 />
            </v-stepper-content>

            <v-stepper-content step="7">
              <Question6 />
            </v-stepper-content>

            <v-stepper-content step="8">
              <Question7 />
            </v-stepper-content>

            <v-stepper-content step="9">
              <Question8 />
            </v-stepper-content>
          </v-stepper-items>
        </v-stepper>  
    </v-app>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex';

import Mbti from "../components/survey/Mbti";
import Question1 from "../components/survey/Question1";
import Question2 from "../components/survey/Question2";
import Question3 from "../components/survey/Question3";
import Question4 from "../components/survey/Question4";
import Question5 from "../components/survey/Question5";
import Question6 from "../components/survey/Question6";
import Question7 from "../components/survey/Question7";
import Question8 from "../components/survey/Question8";


export default {
  components: {
    Mbti,
    Question1,
    Question2,
    Question3,
    Question4,
    Question5,
    Question6,
    Question7,
    Question8,
  },
  computed: mapState(['page']),
  methods: {
    ...mapGetters(['userMbti', 'survey']),
    ...mapMutations(['goPage']),
    ...mapActions(['submitSurvey'])
  }
}
</script>

<style scoped>
/* .survey {
  background-color: rgb(244, 236, 225);
} */

</style>