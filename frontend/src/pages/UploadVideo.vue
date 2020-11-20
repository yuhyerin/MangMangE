<template>
  <div style="padding-top: 75px">
    <div class="loading" v-if="loadingTrigger">
      <i class="fas fa-spinner fa-10x fa-spin"></i>
    </div>
    <Header />
    <v-container style="padding-top: 30px; width: 50%">
      <h2 style="text-align: center; padding-bottom: 12px">동영상 업로드</h2>
      <div style="display: flex; height: 60px">
        <img
          :src="image"
          alt="이미지"
          style="
            height: 60px;
            margin: 0 1% 1% 0;
            border-radius: 20px;
            width: 60px;
          "
        />
        <v-text-field
          placeholder="일련번호"
          v-model="desertionNo"
          outlined
        ></v-text-field>
        <v-icon
          style="color: green; padding: 0"
          elevation="0"
          :disabled="desertionNoCheck != 0"
        >
          mdi-checkbox-marked-circle
        </v-icon>
      </div>
      <p v-if="desertionNoCheck == 1" style="color: orange">
        일련번호는 15자입니다.
      </p>
      <p v-else-if="desertionNoCheck == 2" style="color: red">
        존재하지 않는 일련번호입니다.
      </p>

      <div style="display: flex; margin-top: 25px; height: 60px">
        <v-text-field
          placeholder="제목"
          v-model="title"
          outlined
          style="padding: 0"
        ></v-text-field>
        <v-icon
          style="color: green; padding: 0"
          elevation="0"
          :disabled="title.length < 1"
        >
          mdi-checkbox-marked-circle
        </v-icon>
      </div>

      <div style="display: flex; margin-top: 25px; padding-bottom: 12px">
        <input
          type="file"
          ref="file"
          @change="selectFile"
          :disabled="desertionNoCheck != 0"
        />
        <v-icon style="color: green" :disabled="selectedFileCheck != 3">
          mdi-checkbox-marked-circle
        </v-icon>
        <!-- </v-btn> -->
      </div>
      <label v-if="selectedFileCheck == 1" style="color: red"
        >이미 업로드된 파일입니다</label
      >
      <label v-else-if="selectedFileCheck == 2" style="color: red"
        >지원하지 않는 파일 형식입니다.</label
      >
      <div style="display: flex; padding-top: 12px">
        <v-textarea
          :counter="500"
          outlined
          name="input-7-4"
          v-model="content"
          placeholder="내용"
          style="padding-top: 24px"
        ></v-textarea>
        <!-- <v-btn
          class="mx-2"
          small
          elevation="0"
          style="margin-top: 10px; padding-top: 24px; background: rgba(255, 255, 255, 0);"
        > -->
        <v-icon
          style="color: green"
          :disabled="content.length < 1 || contentCheck === 1"
        >
          mdi-checkbox-marked-circle
        </v-icon>
        <!-- </v-btn> -->
      </div>
      <label v-if="contentCheck" style="color: orange"
        >500자 이하로 입력해주세요</label
      >
      <div style="padding-top: 20px">
        <v-btn
          outlined
          rounded
          :disabled="
            selectedFileCheck != 3 ||
            desertionNoCheck != 0 ||
            title.length < 1 ||
            content.length < 1 ||
            contentCheck === 1
          "
          @click="upload"
          >등록하기</v-btn
        >
      </div>
    </v-container>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import SERVER from "@/api/url";
import axios from "axios";

export default {
  components: {
    Header,
  },

  data() {
    return {
      desertionNo: "",
      title: "",
      file: undefined,
      content: "",
      selectedFiles: false,
      desertionNoCheck: 3,
      selectedFileCheck: 0,
      image: require(`@/assets/image/merong1.png`),
      error: {
        message: "일련번호를 확인해주세요",
      },
      contentCheck: 0,
      loadingTrigger: false,
    };
  },
  watch: {
    desertionNo() {
      this.image = require(`@/assets/image/merong1.png`);
      console.log(this.desertionNo.length);
      if (this.desertionNo.length < 15) this.desertionNoCheck = 1; // 일련번호는 15자입니다.
      if (this.desertionNo.length == 15) {
        SERVER.tokenCheck(() => {
          axios
            .get(SERVER.URL + "/admin/upload/checkNO", {
              params: {
                desertion_no: this.desertionNo,
              },
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            })
            .then((res) => {
              console.log(res);
              if (res.status == 202) {
                console.log(res);
                this.desertionNoCheck = 2; // 존재하지 않는 일련번호입니다.
              } else {
                this.image = res.data.image;
                this.desertionNoCheck = 0;
                this.error.message = "";
              }
            })
            .catch((err) => {
              console.log(err);
            });
        });
      }
    },

    file() {
      if (this.file[0].name.slice(-3) == "mp4") {
        SERVER.tokenCheck(() => {
          axios
            .get(SERVER.URL + "/admin/upload/checkFile", {
              params: {
                fileName: this.desertionNo + "_" + this.file[0].name,
              },
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            })
            .then((res) => {
              console.log(res);
              if (res.status == 202) {
                this.selectedFileCheck = 1;
              } else {
                this.selectedFileCheck = 3;
              }
            })
            .catch((err) => {
              console.log(err);
            });
        });
      } else {
        this.selectedFileCheck = 2;
      }
    },

    content() {
      if (this.content.length <= 500) {
        this.contentCheck = 0;
      } else {
        this.contentCheck = 1;
      }
    },
  },

  methods: {
    selectFile() {
      this.file = this.$refs.file.files;
      this.selectedFiles = true;
    },

    upload() {
      var formData = new FormData();
      this.loadingTrigger = true;
      console.log(this.desertionNo + "_" + this.file[0].name);
      SERVER.tokenCheck(() => {
        formData.append(
          "mfile",
          this.file[0],
          this.desertionNo + "_" + this.file[0].name
        );
        this.registerFile(formData);
      });
    },
    async registerFile(formData) {
      await axios
        .post(SERVER.URL + "/admin/upload", formData, {
          headers: {
            Authorization: this.$cookies.get("accessToken"),
            "content-Type":
              "multipart/form-data; charset=utf-8; boundary='calculated when request is sent';",
          },
        })
        .then((res) => {
          console.log("video upload result is : ", res);
          if (res.data.success == true) {
            this.registerDB();
          }
        })
        .catch((err) => {
          this.loadingTrigger = false;
          console.log("video upload result is : ", err);
        });
    },

    async registerDB() {
      await axios
        .post(
          SERVER.URL + "/admin/uploadVideo",
          {
            desertion_no: this.desertionNo,
            title: this.title,
            content: this.content,
            filepath: this.desertionNo + "_" + this.file[0].name,
          },
          {
            headers: {
              Authorization: this.$cookies.get("accessToken"),
              contentType: "application/json",
            },
          }
        )
        .then((res) => {
          console.log("DataBase upload result is", res.data);
          this.loadingTrigger = false;
          this.$router.push("/videos");
        })
        .catch((err) => {
          console.log("DataBase upload result is ", err);
          this.loadingTrigger = false;
        });
    },
  },
};
</script>

<style scoped>
.loading {
  height: 100vh;
  width: 100vw;
  position: absolute;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 5px;
  background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4));
  /* background-image: linear-gradient(
    rgba(255, 255, 255, 0.4),
    rgba(255, 255, 255, 0.4)
  ); */
}
</style>