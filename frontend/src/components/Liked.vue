<template>
  <div style="display: flex" @click="setLiked">
    <v-icon x-large v-if="likeTrigger == false" color="rgb(255,0,0)">
      mdi-heart-outline
    </v-icon>
    <transition name="bounce">
      <v-icon x-large v-if="likeTrigger == true" color="rgb(255,0,0)">
        mdi-heart
      </v-icon>
    </transition>

    <!-- <img
      v-if="likeTrigger == false"
      src="../assets/image/unlike1.png"
      alt="좋아요"
      style="width: 45px"
    />
    <transition name="bounce">
      <img
        v-if="likeTrigger == true"
        src="../assets/image/like1-1.png"
        alt="좋아요"
        style="width: 45px"
      />
    </transition> -->
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
          SERVER.tokenCheck(() => {
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
              .then((res) => {})
              .catch((err) => {
                console.log(err);
              });
          });
        } else {
          // 좋아요 등록
          SERVER.tokenCheck(() => {
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
              .then((res) => {})
              .catch((err) => {
                console.log(err);
              });
          });
        }
        this.likeTrigger = !this.likeTrigger;
      }
    },
  },
};
</script>

<style scoped>
.bounce-enter-active {
  animation: bounce-in 0.5s;
}
.bounce-leave-active {
  animation: bounce-in 0.5s reverse;
  z-index: -2;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.5);
  }
  100% {
    transform: scale(1);
  }
}
</style>