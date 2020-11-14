<template>
  <div>
    <Header />
    <div class="container" style="padding-top: 80px; width: 70%">
      <h1>입양신청서</h1>
      <br />
      <div>
        <!-- 리스트 -->
        <h3>입양할 준비가 되었는지 확인해보세요</h3>
        <br />
        <div>
          <p>1. 입양에 필요한 비용을 감당하실 수 있나요?</p>
          <br />
          <p>2. 정기적인 검진에 따른 비용을 감당하실 수 있나요?</p>
          <br />
          <p>3. 사료, 간식, 장난감 등의 비용을 감당하실 수 있나요?</p>
          <br />
          <p>4. 매일 반려견과 30분 이상 산책하실 수 있나요?</p>
          <br />
          <p>
            5. 가족과 함께 살고 있다면 가족 구성원 모두 반려견 입양에
            동의하셨나요?
          </p>
          <br />
          <p>6. 입양을 희망하는 견종의 특성과 성향을 파악하고 계신가요?</p>
          <br />
          <p>
            7. 휴가를 갈때 반려견이 함께 갈 수 없는 경우에 반려견을 어떻게 할지
            생각해보셨나요?
          </p>
          <br />
        </div>
      </div>
      <!-- 리스트 끝 -->
      <hr />
      <br />
      <h3>유기동물 정보</h3>
      <div style="display: flex">
        <!-- 동물정보 -->
        <div style="width: 50%">
          <v-row>
            <v-col>
              <p>1. 일련번호</p>
            </v-col>
            <v-space></v-space>
            <v-col>
              <p style="border-bottom: 0.5px solid #bbb">
                {{ dogInfo.desertion_no }}
              </p>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <p>2. 추정나이</p>
            </v-col>
            <v-space></v-space>
            <v-col>
              <p style="border-bottom: 0.5px solid #bbb">
                {{ animalAge }}
              </p>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <p>3. 종류</p>
            </v-col>
            <v-space></v-space>
            <v-col>
              <p style="border-bottom: 0.5px solid #bbb">
                {{ dogInfo.kind_c }}
              </p>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <p>4. 성별</p>
            </v-col>
            <v-space></v-space>
            <v-col>
              <p
                v-if="dogInfo.sex_cd == 'M'"
                style="border-bottom: 0.5px solid #bbb"
              >
                남
              </p>
              <p v-else style="border-bottom: 0.5px solid #bbb">여</p>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <p>5. 털색</p>
            </v-col>
            <v-space></v-space>
            <v-col>
              <p style="border-bottom: 0.5px solid #bbb">
                {{ dogInfo.color_cd }}
              </p>
            </v-col>
          </v-row>
        </div>
        <div style="width: 40%; display: flex; justify-content: center">
          <img
            :src="dogInfo.popfile"
            style="width: 60%; height: 90%; border-radius: 30px"
          />
        </div>
      </div>
      <!-- 동물 정보 끝 -->
      <hr />
      <br />
      <h3>입양희망자 정보</h3>
      <br />
      <div>
        <!-- 신청 폼 -->
        <v-row md="12" style="padding: 12px">
          <v-col>
            <v-row cols="6">
              <v-text-field
                v-model="title"
                label="제목"
                clearable
                :rules="rule[0]"
              ></v-text-field>
            </v-row>
            <v-row cols="6">
              <v-text-field
                v-model="name"
                label="이름"
                clearable
                :rules="rule[1]"
              ></v-text-field>
            </v-row>
            <v-row>
              <v-col cols="10" style="padding: 0">
                <v-text-field
                  v-model="phoneNumber"
                  label="연락처"
                  clearable
                  :rules="rule[2]"
                  maxlength="13"
                ></v-text-field>
              </v-col>
              <v-col>
                <v-btn @click="sendMessage" :disabled="auth">인증</v-btn>
              </v-col>
            </v-row>
            <v-row v-if="send" cols="6">
              <v-text-field
                v-model="message"
                label="인증번호"
                clearable
                :rules="rule[3]"
                :disabled="auth"
              ></v-text-field>
            </v-row>
            <v-row cols="6">
              <v-text-field
                v-model="email"
                label="이메일주소"
                clearable
                :rules="rule[4]"
              ></v-text-field>
            </v-row>
          </v-col>
          <v-col style="border: 1px solid gray"></v-col>
        </v-row>
        <!-- <v-col cols="6">
          <v-text-field
            v-model="title"
            label="제목"
            clearable
            :rules="rule[0]"
          ></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="name"
            label="이름"
            clearable
            :rules="rule[1]"
          ></v-text-field>
        </v-col>
        <v-row style="padding-left: 10px">
          <v-col cols="6">
            <v-text-field
              v-model="phoneNumber"
              label="연락처"
              clearable
              :rules="rule[2]"
              maxlength="13"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-btn @click="sendMessage" :disabled="auth">인증</v-btn>
          </v-col>
        </v-row>
        <v-col v-if="send" cols="6">
          <v-text-field
            v-model="message"
            label="인증번호"
            clearable
            :rules="rule[3]"
            :disabled="auth"
          ></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="email"
            label="이메일주소"
            clearable
            :rules="rule[4]"
          ></v-text-field>
        </v-col> -->
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Header from "../components/Header.vue";
import axios from "axios";
import SERVER from "@/api/url";

export default {
  name: "Adoption",
  components: {
    Header,
  },
  data() {
    return {
      dogInfo: [],
      title: "",
      name: "",
      email: "",
      phoneNumber: "",
      authNum: "",
      message: "",
      send: false,
      auth: false,

      rule: [
        [(value) => !!value || "제목을 입력해 주세요."],
        [(value) => !!value || "이름을 입력해 주세요."],
        [(value) => !!value || value.length == 13 || "연락처를 입력해 주세요."],
        [(value) => !!value || "인증번호를 입력해 주세요."],
        [
          (value) =>
            !value ||
            /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value) ||
            "유효하지 않은 이메일입니다.",
        ],
      ],
    };
  },

  computed: {
    ...mapState(["address"]),
    animalAge() {
      var age = new Date().getFullYear() - this.dogInfo.age * 1;
      if (age < 1) {
        return "1년 미만";
      } else {
        return age + "살";
      }
    },
  },

  methods: {
    ...mapMutations(["findPersonAddress"]),

    sendMessage() {
      SERVER.tokenCheck(() => {
        axios
          .get(SERVER.URL + "/user/adopt/create", {
            params: {
              phone: this.phoneNumber,
            },
            headers: {
              Authorization: this.$cookies.get("accessToken"),
            },
          })
          .then((res) => {
            this.pressedAuthenticationBtn = 1;
            this.authNum = res.data.number;
            console.log(this.authNum);
            this.send = true;
          })
          .catch((err) => {
            SERVER.RefreshToken(err);
          });
      });
    },

    adoptionApply() {
      SERVER.tokenCheck(() => {
        axios
          .post(
            SERVER.URL + "/user/adopt/create",
            {
              ani_num: this.dogInfo.desertion_no,
              user_name: this.name,
              user_phone: this.phoneNumber,
              user_email: this.email,
              title: this.title,
            },
            {
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            }
          )
          .then((res) => {
            this.$router.push("/adoptionlist");
          })
          .catch((err) => {
            SERVER.RefreshToken(err);
          });
      });
    },
  },
  watch: {
    phoneNumber() {
      this.auth = false;
      if (this.phoneNumber == null) return;
      if (this.phoneNumber.length == 3 || this.phoneNumber.length == 8)
        this.phoneNumber += "-";
    },
    message() {
      if (this.message == this.authNum) {
        this.auth = true;
      }
    },
  },
  created() {
    SERVER.tokenCheck(() => {
      axios
        .get(SERVER.URL + "/user/animal/detail", {
          params: {
            desertion_no: this.$route.params.animalID,
          },
          headers: {
            Authorization: this.$cookies.get("accessToken"),
          },
        })
        .then((res) => {
          this.dogInfo = res.data.animalList;
        })
        .catch((err) => {
          SERVER.RefreshToken(err);
        });
    });
  },
};
</script>

<style scoped>
.backrow {
  background-color: blueviolet;
}
.backcol {
  background-color: blue;
}
</style>