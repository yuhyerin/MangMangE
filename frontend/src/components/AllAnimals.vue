<template>
  <div style="width: 200px; margin: 10px">
    <div class="flip">
      <div
        class="front"
        :style="{
          'background-image': 'url(' + animalInfo.popfile + ')',
        }"
      >
        <div
          style="
            display: flex;
            position: absolute;
            z-index: 1;
            left: 170px;
            top: 5px;
          "
          @mouseover="setMoveTrigger"
          @mouseout="setMoveTrigger"
          @click="setLiked"
        >
          <v-icon v-if="likeTrigger == false" color="rgb(255,0,0)">
            mdi-heart-outline
          </v-icon>
          <v-icon v-if="likeTrigger == true" color="rgb(255,0,0)">
            mdi-heart
          </v-icon>
        </div>
        <!-- <h1 class="text-shadow">{{ data.desertion_no }}</h1> -->
        <!-- <img :src="data.filename"  alt="동물이미지" /> -->
      </div>
      <div
        class="back"
        :style="{
          'background-image':
            'linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),url(' +
            animalInfo.popfile +
            ')',
        }"
        @click="clicked"
      >
        <div
          style="
            display: flex;
            position: absolute;
            z-index: 1;
            left: 170px;
            top: 5px;
          "
          @click="setLiked"
          @mouseover="setMoveTrigger"
          @mouseout="setMoveTrigger"
        >
          <v-icon v-if="likeTrigger == false" color="rgb(255,0,0)">
            mdi-heart-outline
          </v-icon>
          <v-icon v-if="likeTrigger == true" color="rgb(255,0,0)">
            mdi-heart
          </v-icon>
        </div>
        <h2>{{ animalInfo.kind_cd }}</h2>
        <p>{{ animalInfo.sex_cd == "M" ? "수컷" : "암컷" }}</p>
        <p>{{ animalInfo.weight }} (추정)</p>
        <p>{{ this.animalTag }}</p>
      </div>
    </div>
    <div style="display: flex; align-items: center">
      <v-icon> mdi-hail </v-icon>
      <div>&nbsp; 215 &nbsp;</div>
      <v-icon small> mdi-heart </v-icon>
      <div>&nbsp;4</div>
    </div>
    <!-- <div>만약 이런식으로 글 제목이 들어간다면 어떨까</div> -->
  </div>
</template>
<script>
import axios from "axios";
import SERVER from "@/api/url";

export default {
  props: ["animalInfo"],
  data() {
    return {
      moveTrigger: true,
      likeTrigger: false,
    };
  },
  computed: {
    animalTag() {
      var tag = "";
      for (let i = 0; i < this.animalInfo.personality.length; i++) {
        tag += "#" + this.animalInfo.personality[i] + " ";
      }
      return tag;
    },
  },
  async created() {
    this.likeTrigger = false;
    if (this.animalInfo.like == false) {
      this.likeTrigger = false;
    } else {
      this.likeTrigger = true;
    }

    // axios
    //   .get(SERVER.URL + "/newuser/animal/detail", {
    //     params: {
    //       desertion_no: this.animalInfo.desertion_no,
    //     },
    //   })
    //   .then((res) => {
    //     for (let i = 0; i < res.data.personality.length; i++) {
    //       this.animalTag += "#" + res.data.personality[i] + " ";
    //     }
    //   })
    //   .catch((err) => {
    //     console.log(err);
    //   });
  },

  methods: {
    clicked() {
      if (this.moveTrigger == true) {
        this.$router.push("/animalDetail" + `/${this.animalInfo.desertion_no}`);
      }
    },
    setLiked() {
      if (this.moveTrigger == false) {
        if (this.likeTrigger == true) {
          // 좋아요 해제
          axios
            .post(
              SERVER.URL + "/user/animal/animalLike",
              {
                desertion_no: this.animalInfo.desertion_no,
              },
              {
                headers: {
                  Authorization: this.$cookies.get("accessToken"),
                },
              }
            )
            .then((res) => {})
            .catch((err) => {
              console.log(err);
            });
        } else {
          // 좋아요 등록
          axios
            .post(
              SERVER.URL + "/user/animal/animalLike",
              {
                desertion_no: this.animalInfo.desertion_no,
              },
              {
                headers: {
                  Authorization: this.$cookies.get("accessToken"),
                },
              }
            )
            .then((res) => {})
            .catch((err) => {
              console.log(err);
            });
        }
        this.likeTrigger = !this.likeTrigger;
      }
      console.log(
        "this.animalInfo.desertion_no : " + this.animalInfo.desertion_no
      );
    },
    setMoveTrigger() {
      this.moveTrigger = !this.moveTrigger;
    },
  },
};
</script>

<style lang="scss" scoped src="../assets/css/Card.scss">
</style>
