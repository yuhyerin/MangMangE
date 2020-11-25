<template>
  <div class="video-detail">
    <Header />
    <div
      class="backBtn"
      style="position: fixed; top: 90px; left: 3%"
      @click="moveToBack"
    >
      <div style="display: flex; justify-content: center; height: 50px">
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <v-icon x-large>mdi-arrow-left </v-icon>
        </div>
      </div>
      <div>돌아가기</div>
    </div>
    <v-container fluid style="margin-top: 50px; padding-left: 100px">
      <v-layout col wrap>
        <v-col cols="8">
          <div class="video-play">
            <video
              :src="`https://k3b306.p.ssafy.io/allVideos/${video.filepath}`"
              type="video/mp4"
              controls
              autoplay
              style="max-height: 505px; width: 100%; height: 100%; background-color: black;"
            ></video>
          </div>
          <v-row>
            <v-col style="padding-bottom: 0">
              <h2 style="padding-left: 5px">{{ video.title }}</h2>
            </v-col>
            <v-col style="display: flex; justify-content: flex-end">
              <img
                @click="moveToSupport"
                src="@/assets/image/kakaoBtn.png"
                alt=""
                style="width: 150px; cursor: pointer"
              />
            </v-col>
          </v-row>
          <div class="video-info" style="padding-left: 5px">
            <p style="color: darkgray; padding-bottom: 10px">
              <i class="far fa-user fa-xs" style="margin-right: 5px"></i
              >{{ video.writer }} |
              <i class="fas fa-calendar-day fa-xs" style="margin-left: 5px"></i>
              {{ video.regtime }}
            </p>
            <p style="padding-bottom: 10px">{{ video.content }}</p>
            <hr />
            <p class="animal-info" @click="moveToAnimal(video.desertion_no)">
              <v-icon> mdi-magnify-plus </v-icon>프로필 보기
            </p>
          </div>
        </v-col>

        <v-col cols="3" style="padding-left: 20px">
          <div class="next-videolist">
            <p style="font-size: 1.1rem">추천 동영상</p>
            <v-row v-for="rec in randomRecList" :key="rec.uid">
              <v-col style="padding-right: 8px">
                <video
                  @click="moveToAnoterVideo(rec.uid)"
                  :src="`https://k3b306.p.ssafy.io/allVideos/${rec.filepath}`"
                  type="video/mp4"
                  style="
                    max-height: 89px;
                    width: 100%;
                    height: 100%;
                    background-color: black;
                    cursor: pointer;
                  "
                ></video>
              </v-col>
              <v-col
                @click="moveToAnoterVideo(rec.uid)"
                style="cursor: pointer"
              >
                <h4>{{ rec.title }}</h4>
                <p style="color: darkgray; font-size: 0.8rem">
                  {{ rec.writer }}
                </p>
                <p style="color: darkgray; font-size: 0.8rem">
                  {{ rec.regtime }}
                </p>
              </v-col>
            </v-row>
          </div>
        </v-col>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import SERVER from "@/api/url";
import axios from "axios";
import { mapMutations } from "vuex";

const ADMIN_KEY = process.env.kakaopay_admin_key;

export default {
  components: {
    Header,
  },
  data() {
    return {
      videoID: this.$route.params.videoId,
      video: [],
      nextVideoList: [],
      recommendVideoList: [],
      randomRecList: [],
    };
  },
  created() {
    this.getVideo();
    this.getVideoList();
  },
  methods: {
    ...mapMutations(["setEventListener"]),
    moveToMainpage() {
      this.$router.push("/videos");
    },
    moveToSupport() {
      axios
        .post(SERVER.URL + "/newuser/kakaoPay", {
          videoid: 1,
        })
        .then((res) => {
          async function openPopup() {
            window.open(
              res.data,
              "window_name",
              "width=430, height=500, location=no, status=no, scrollbars=yes"
            );
            await function () {
              window.close(SERVER.URL + "/video/0");
            };
          }
          openPopup();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    moveToAnimal(animalID) {
      this.$router.push(`/animaldetail/${animalID}`);
    },
    getVideo() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getVideo, {
          params: {
            uid: this.videoID,
          },
        })
        .then((res) => {
          this.video = res.data.VideoDetail;
          var item = this.video;
          var someday = new Date(this.video.regtime);
          var year = someday.getFullYear();
          var month = someday.getMonth() + 1;
          var date = someday.getDate();
          var regTime = year + "-" + month + "-" + date;
          this.video.regtime = regTime;
        });
    },
    getVideoList() {
      axios.get(SERVER.URL + SERVER.ROUTES.getAllVideos).then((res) => {
        this.nextVideoList = res.data.VideoList;
        for (let i = 0; i < this.nextVideoList.length; i++) {
          var item = this.nextVideoList[i];
          var someday = new Date(item.regtime);
          var year = someday.getFullYear();
          var month = someday.getMonth() + 1;
          var date = someday.getDate();
          var regTime = year + "-" + month + "-" + date;
          item.regtime = regTime;
        }
        this.nextVideoList.forEach((val) => {
          if (val.uid !== Number(this.videoID)) {
            this.recommendVideoList.push(val);
          }
        });
        this.randomRec(this.recommendVideoList);
        // var randomRec = this.recommendVideoList
        // randomRec.sort(function(a, b) {
        //   return 0.5 - Math.random()
        // })
      });
    },
    randomRec(recList) {
      var shuffle = recList.slice();
      var len = shuffle.length;
      var i = len;
      while (i--) {
        var p = parseInt(Math.random() * len);
        var t = shuffle[i];
        shuffle[i] = shuffle[p];
        shuffle[p] = t;
      }
      if (shuffle.length > 4) {
        this.randomRecList = shuffle.slice(0, 5);
      } else {
        this.randomRecList = shuffle;
      }
    },
    moveToAnoterVideo(idx) {
      location.href = "/video" + `/${idx}`;
    },
    moveToBack() {
      this.setEventListener(5);
      this.$router.push("/videos");
    },
  },
};
</script>

<style>
.kakaopaySupport {
  cursor: pointer;
}

.selectMoney {
  border: 1px solid green;
}
.animal-info {
  cursor: pointer;
  font-size: 1.1rem;
  margin-top: 5px;
}
.animal-info:hover {
  font-weight: bold;
}
</style>