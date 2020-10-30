<template>
  <v-app>
    <Header />
      <v-container style="padding-top: 90px">
        <div
          style="
            height: 40vh;
            background-color: rgb(244, 236, 225);
            border-radius: 15px;
          "
        >
          <div
            style="
              display: flex;
              position: absolute;
              z-index: 1;
              right: 13%;
              top: 95px;
            "
            @click="setLiked"
          >
            <v-icon x-large v-if="likeTrigger == false" color="rgb(255,0,0)">
              mdi-heart-outline
            </v-icon>
            <v-icon x-large v-if="likeTrigger == true" color="rgb(255,0,0)">
              mdi-heart
            </v-icon>
          </div>
          <div style="display: flex">
            <div>
              <v-icon color="black"> mdi-arrow-left </v-icon>
            </div>
            <div
              style="
                width: 30vw;
                height: 35vh;
                margin: 10px;
              "
              :style="{ 'background-image': 'url(' + animalInfo.popfile + ')', 'background-size' : 'cover' }"
            >
            </div>
            <div style="width: 70vw; height: 35vh; margin: 10px">
              <div style="height: 20%">제목</div>
              <div style="height: 60%">
                <table>
                  <tr>
                    <td>종 / 품종</td>
                    <td>{{this.animalInfo.kind_cd}}</td>
                  </tr>
                  <tr>
                    <td>성별(중성화)</td>
                    <td>{{this.animalInfo.sex_cd == "M" ? "수컷" : "암컷"}}</td>
                  </tr>
                  <tr>
                    <td>나이</td>
                    <td>{{this.animalInfo.age}}</td>
                  </tr>
                  <tr>
                    <td>몸무게</td>
                    <td>{{this.animalInfo.weight}}</td>
                  </tr>
                  <tr>
                    <td>털색</td>
                    <td>{{this.animalInfo.color_cd}}</td>
                  </tr>
                  <tr>
                    <td>성격</td>
                    <td>{{this.animalInfo.mbti}}</td>
                  </tr>
                </table>
              </div>
              <div
                style="
                  height: 20%;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
              >
                <v-btn width="95%" height="70%" color="rgb(1,118,72)">
                  <div style="color: white">입양하기</div>
                </v-btn>
              </div>
            </div>
          </div>
        </div>
      </v-container>
  </v-app>
</template>

<script>
import Header from "../components/Header.vue";
import dump from "../assets/data/animal.json";

export default {
  components: {
    Header,
  },
  data() {
    return {
      likeTrigger: false,
      animalInfo: "",
    };
  },
  created() {
    for (var i = 0; i < dump.animal.length; i++) {
      if (dump.animal[i].desertion_no == this.$route.params.animalID) {
        this.animalInfo = dump.animal[i];
      }
    }
  },
  methods: {
    setLiked() {
      console.log("즐겨찾기!!");
      this.likeTrigger = !this.likeTrigger;
    },
  },
};
</script>

<style>
</style>	