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
              display: flex;
              justify-content: center;
              align-items: center;
              overflow: hidden;
              width: 30vw;
            "
          >
            <img :src="this.animalInfo.animal.popfile" alt="사진" />
          </div>
          <div style="width: 70vw; height: 35vh; margin: 10px">
            <div style="height: 20%">제목</div>
            <div style="height: 60%">
              <table>
                <tr>
                  <td>종 / 품종</td>
                  <td>{{ this.animalInfo.animal.kind_c }}</td>
                </tr>
                <tr>
                  <td>성별(중성화)</td>
                  <td>
                    {{ this.animalInfo.animal.sex_cd == "M" ? "수컷" : "암컷" }}
                  </td>
                </tr>
                <tr>
                  <td>나이</td>
                  <td>{{ this.animalInfo.animal.age }}</td>
                </tr>
                <tr>
                  <td>몸무게</td>
                  <td>{{ this.animalInfo.animal.weight }}</td>
                </tr>
                <tr>
                  <td>털색</td>
                  <td>{{ this.animalInfo.animal.color_cd }}</td>
                </tr>
                <tr>
                  <td>성격</td>
                  <td>{{ this.animalInfo.animal.mbti }}</td>
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
              <v-btn
                width="95%"
                height="70%"
                color="rgb(1,118,72)"
                @click="moveTo('/adoption')"
              >
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
import axios from "axios";
import SERVER from "@/api/url";

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
    axios
      .get(SERVER.URL + "/newuser/animal/detail", {
        params: {
          desertion_no: this.$route.params.animalID,
        },
      })
      .then((res) => {
        this.animalInfo = res.data;
        console.log(this.animalInfo);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    setLiked() {
      if (this.likeTrigger == true) {
        // 좋아요 해제
        console.log("false");
        axios
          .post(
            SERVER.URL + "/user/animal/animalLike",
            {
              desertion_no: this.animalInfo.animal.desertion_no,
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
              desertion_no: this.animalInfo.animal.desertion_no,
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
    },
    moveTo(page) {
      this.$router.push(page + `/${this.animalInfo.animal.desertion_no}`);
    },
  },
};
</script>

<style>
</style>	