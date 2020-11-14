<template>
  <div>
    <h3>유기동물 정보</h3>
    <div
      style="display: flex; border-bottom: 2.5px solid #bbb; padding-bottom: 2%"
    >
      <!-- 동물정보 -->
      <div style="width: 50%">
        <v-row>
          <v-col>
            <p>1. 일련번호</p>
          </v-col>
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
    <br />
    <!-- 동물 정보 끝 -->
    <div style="border-bottom: 2.5px solid #bbb; padding-bottom: 2%">
      <!-- 신청 폼 -->
      <v-row md="12" style="padding: 12px">
        <v-col>
          <h3>입양희망자 정보</h3>
          <br />
          <v-row cols="6">
            <v-text-field
              v-model="title"
              ref="title"
              label="제목"
              clearable
              counter="20"
              :rules="rule[0]"
            ></v-text-field>
          </v-row>
          <v-row cols="6">
            <v-text-field
              v-model="name"
              ref="name"
              label="이름"
              clearable
              :rules="rule[1]"
            ></v-text-field>
          </v-row>
          <v-row>
            <v-col cols="9" style="padding: 0">
              <v-text-field
                v-model="phoneNumber"
                ref="phoneNumber"
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
              ref="message"
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
        <v-col style="margin-left: 3%">
          <h3>입양 신청 시 주의사항</h3>
          <br />
          <v-card outlined>
            <v-card-text>
              1. 입양신청서의 이름과 회원정보의 이름이 불일치할 경우 입양에
              불이익이 있을 수 있습니다.<br /><br />
              2. 입양 상담 후 입양이 불가능할 수 있습니다.<br /><br />
              3. 상담 날짜는 신청서 작성 후 개별연락을 통해 이루어집니다.<br /><br />
              4. 문의하실 사항이 있다면 아래 번호로 연락바랍니다.<br /><br />
              Tel : 070-7874-1099 | Mobile : 010-0000-0000 <br />
              Fax : 070-7874-1098 | Email: daengnyang@daengnyang.com
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </div>
    <!-- 신청 폼 끝 -->
    <br />
    <div>
      <!-- 개인정보 수집 내용 -->
      <h3>개인정보 이용 및 수집 동의</h3>
      <p>
        (주)댕줍멍줍은 아래의 목적으로 개인정보를 수집 및 이용하며, 회원의
        개인정보를 안전하게 취급하는데 최선을 다합니다.
      </p>
      <br />
      <v-card outlined>
        <v-card-title>개인정보 수집 및 이용에 대한 안내</v-card-title>
        <v-card-text>
          1. 개인정보의 보유 및 이용기간: 3년(관계법령에서 정한 일정한 기간)
          <br />
          2. 수집하는 개인정보의 항목: 성명, 연락처, 이메일, 성별, 생년월일,
          주소 <br />
          3. 개인정보의 수집,이용 목적: 추후 댕줍멍줍의 소식을 전달하기 위함
          <br />
        </v-card-text>
      </v-card>
      <p>
        법률에서 정하는 경우를 제외하고 귀하의 동의 없이 개인정보를 제 3자에게
        제공하지 않습니다.
      </p>
      <v-checkbox
        v-model="agree"
        label="개인정보 이용에 동의합니다."
        color="#017648"
      ></v-checkbox>
    </div>
    <!-- 개인정보 끝 -->
    <div style="display: flex">
      <!-- 버튼 -->
      <div style="display: flex; justify-content: center; width: 100%">
        <v-btn dark rounded color="#017648" width="80%" @click="checkForm()"
          >신청하기</v-btn
        >
      </div>
      <div style="display: flex; justify-content: center; width: 100%">
        <v-btn dark rounded color="black" width="80%" @click="$router.go(-1)"
          >돌아가기</v-btn
        >
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import axios from "axios";
import SERVER from "@/api/url";
export default {
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
      agree: false,

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

  computed: {
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

    checkForm() {
      let err = true;
      let msg = "";
      !this.title &&
        ((msg = "제목을 입력해주세요."),
        (err = false),
        this.$refs.title.focus());
      err &&
        !this.name &&
        ((msg = "이름을 입력해주세요."),
        (err = false),
        this.$refs.name.focus());
      err &&
        !this.phoneNumber &&
        ((msg = "연락처를 입력해주세요."),
        (err = false),
        this.$refs.phoneNumber.focus());
      err &&
        !this.send &&
        ((msg = "인증절차를 완료해주세요."),
        (err = false),
        this.$refs.phoneNumber.focus());
      err &&
        !this.auth &&
        ((msg = "인증절차를 완료해주세요."),
        (err = false),
        this.$refs.message.focus());
      err &&
        !this.agree &&
        ((msg = "정보수집동의가 필요합니다."), (err = false));
      if (!err) alert(msg);
      else this.adoptionApply();
    },
  },
  watch: {
    agree() {},
    phoneNumber() {
      this.auth = false;
      this.send = false;
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
};
</script>