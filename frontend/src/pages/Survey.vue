<template>
  <div class="survey">
    <!-- https://vuetifyjs.com/en/components/steppers/ -->
    <div class="container">
      <Mbti />
      <Question1 />
      <Question2 />
      <Question3 />
      <Question4 />
      <Question5 />
      <Question6 />
      <Question7 />
      <Question8 />
    </div>
  </div>
</template>

<script>
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
}
</script>

<style scoped>
/* .survey {
  background-color: rgb(244, 236, 225);
} */

</style>