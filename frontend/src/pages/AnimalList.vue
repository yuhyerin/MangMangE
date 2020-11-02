<template>
  <v-app>
    <Header />
    <v-content>
      <v-container style="padding-top: 75px">
        <div style="display: flex; min-height: 87vh">
          <div
            style="
              width: 20vw;
              max-height: 88vh;
              display: flex;
              justify-content: center;
            "
          >
            <div
              style="
                background-color: rgb(244, 236, 225);
                border-radius: 20px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                position: fixed;
                height: 87vh;
                margin: 5px;
              "
            >
              <div>
                <div class="categoryBtn">
                  <v-btn text color="black" @click="setTrigger(0)">
                    모든 아이들 보기 &nbsp;
                  </v-btn>
                  <v-icon v-if="trigger == 0" large color="black">
                    mdi-paw-outline
                  </v-icon>
                </div>
                <div class="categoryBtn">
                  <v-btn text color="black" @click="setTrigger(1)">
                    나와 맞는 아이는? &nbsp;
                  </v-btn>
                  <v-icon v-if="trigger == 1" large color="black">
                    mdi-paw-outline
                  </v-icon>
                </div>
                <div class="categoryBtn" style="">
                  <v-btn text color="black" @click="setTrigger(2)">
                    즐겨 찾는 아이들 &nbsp;
                  </v-btn>
                  <v-icon v-if="trigger == 2" large color="black">
                    mdi-paw-outline
                  </v-icon>
                </div>
              </div>
              <div style="text-align: center">
                <div>
                  <input
                    type="checkbox"
                    id="checkbox"
                    v-model="checked[0]"
                    true-value="F"
                    false-value="no"
                    checked
                    @change="changeValue"
                  />
                  <label for="checkbox">암컷</label>
                  <input
                    type="checkbox"
                    id="checkbox"
                    v-model="checked[1]"
                    true-value="M"
                    false-value="no"
                    checked
                    @change="changeValue"
                  />
                  <label for="checkbox">수컷</label>
                </div>
                <img
                  v-if="testTrigger == false"
                  src="../assets/image/1wait.gif"
                  alt="멍멍"
                  @click="test"
                />
                <img
                  v-if="testTrigger == true"
                  src="../assets/image/1pop.gif"
                  alt="멍멍"
                />
              </div>
            </div>
          </div>
          <div
            style="
              width: 80vw;
              background-color: rgb(244, 236, 225);
              border-radius: 20px;
              margin: 5px;
              align-text: center;
            "
          >
            <div
              style="display: flex; flex-wrap: wrap; justify-content: center"
            >
              <AllAnimals
                v-if="trigger == 0"
                v-for="(data, index) in this.allDatas"
                :key="index"
                :animalInfo="data"
              />
              <!-- <AllAnimals
                v-if="trigger == 1"
                v-for="(data, index) in this.tmpArr"
                :key="index"
                :animalInfo="data"
              />
              <AllAnimals
                v-if="trigger == 2"
                v-for="(data, index) in this.tmpArr"
                :key="index"
                :animalInfo="data"
              /> -->
            </div>
          </div>
        </div>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import AllAnimals from "../components/AllAnimals.vue";
import Header from "../components/Header.vue";
import { mapGetters, mapMutations } from "vuex";
import data from "../assets/data/animal.json";

import SERVER from "@/api/url";
import axios from "axios";

export default {
  data() {
    return {
      trigger: 0,
      tmp: 0,
      testTrigger: false,
      allDatas: data,
      matchedDatas: "",
      likedDatas: [],
      checked: ["F", "M"],
      tmpArr: [],
    };
  },
  components: {
    AllAnimals,
    Header,
  },
  watch: {
    trigger(newValue, oldValue) {
      if (newValue == 0) {
        this.tmp = 10;  
        console.log("All Animals");
        axios.get(SERVER.URL + "/newuser/animal/allread",{
          headers: {
            Authorization: this.$cookies.get('accessToken')
          }
        }).then((res) => {
          this.allDatas = res.data.animalList;
          console.log(res.data.animalList);
        });
      } else if (newValue == 1) {
        this.tmp = 4;
        console.log("matched Animals");
      } else {
        this.tmp = 2;
        console.log("like animals");
      }
    },
  },

  created() {
    axios.get(SERVER.URL + "/newuser/animal/allread").then((res) => {
      this.allDatas = res.data.animalList;
    });
  },

  methods: {
    ...mapGetters(["getPageCheck"]),
    ...mapMutations(["checkThisPage"]),
    setTrigger(num) {
      this.trigger = num;
    },
    test() {
      this.testTrigger = true;
      setTimeout(() => {
        this.testTrigger = false;
      }, 3300);
    },

    changeValue() {
      this.tmpArr.length = 0;
      for (var i = 0; i < data.animal.length; i++) {
        if (this.checked.includes(data.animal[i].sex_cd)) {
          this.tmpArr.push(data.animal[i]);
        }
      }
      console.log(this.tmpArr);
    },
  },
};
</script>

<style>
.categoryBtn {
  padding-top: 10px;
  height: 7vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>