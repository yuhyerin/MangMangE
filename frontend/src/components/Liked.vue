<template>
  <div style="display: flex" @click="setLiked">
    <v-icon v-if="likeTrigger == false" color="rgb(255,0,0)">
      mdi-heart-outline
    </v-icon>
    <v-icon v-if="likeTrigger == true" color="rgb(255,0,0)"> mdi-heart </v-icon>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/url";
export default {
  props: ["dNo", "animalLike"],
  data() {
    return {
      likeTrigger: this.animalLike,
      desertion_no: this.dNo,
    };
  },

  methods: {
    setLiked() {
      if (this.$cookies.get("accessToken") == null) {
        alert("로그인이 필요한 서비스 입니다.");
      } else {
        if (this.likeTrigger == true) {
          // 좋아요 해제
          console.log("false");
          axios
            .post(
              SERVER.URL + "/user/animal/animalLike",
              {
                desertion_no: this.desertion_no,
              },
              {
                headers: {
                  Authorization: this.$cookies.get("accessToken"),
                },
              }
            )
            .then((res) => {
              console.log(res);
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          // 좋아요 등록
          console.log("true");
          axios
            .post(
              SERVER.URL + "/user/animal/animalLike",
              {
                desertion_no: this.desertion_no,
              },
              {
                headers: {
                  Authorization: this.$cookies.get("accessToken"),
                },
              }
            )
            .then((res) => {
              console.log(res);
            })
            .catch((err) => {
              console.log(err);
            });
        }
        this.likeTrigger = !this.likeTrigger;
      }
    },
  },
};
</script>

<style>
</style>