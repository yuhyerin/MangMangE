<template>
  <div>
    <Header />
    <div class="container">
      <div>
        <div>
          <h3>입양신청서</h3>
          <div>
            <h4>입양할 준비가 되셨는지 확인해보세요</h4>
            <ul>
              <li>입양에 필요한 비용을 감당하실 수 있나요?</li>
              <li>정기적인 검진에 따른 비용을 감당하실 수 있나요?</li>
              <li>사료, 간식, 장난감 등의 비용을 감당하실 수 있나요?</li>
              <li>매일 반려견과 30분 이상 산책하실 수 있나요?</li>
              <li>
                가족과 함께 살고 있다면 가족 구성원 모두 반려견 입양에
                동의하셨나요?
              </li>
              <li>입양을 희망하는 견종의 특성과 성향을 파악하고 계신가요?</li>
              <li>
                휴가를 갈때 반려견이 함께 갈 수 없는 경우에 반려견을 어떻게 할지
                생각해보셨나요?
              </li>
            </ul>
          </div>
          <hr/>
          <h3>유기동물 정보</h3>
          <div>
            <div>
              <div>
                <div>
                  <label>1. 일련번호</label>
                </div>
                <div>
                  <!-- <p class="serial-p" style="border: 0.5px solid #bbb"> -->
                  <p style="border-bottom : 0.5px solid #bbb; width: 30%;">
                    {{ dogSerial }}
                  </p>
                </div>
              </div>
              <div>
                <div>
                  <label>2. 추정나이</label>
                </div>
                <div>
                  <p style="border-bottom : 0.5px solid #bbb; width: 30%;">
                  <!-- <p class="age-p" style="border: 0.5px solid #bbb"> -->
                    {{ dogAge }}
                  </p>
                </div>
              </div>
              <div>
                <div>
                  <label>3. 종류</label>
                </div>
                <div>
                  <p style="border-bottom : 0.5px solid #bbb; width: 30%;">
                  <!-- <p class="breed-p" style="border: 0.5px solid #bbb"> -->
                    {{ dogBreed }}
                  </p>
                </div>
              </div>
              <div>
                <div>
                  <label>4. 성별</label>
                </div>
                <div>
                  <p style="border-bottom : 0.5px solid #bbb; width: 30%;">
                  <!-- <p class="gender-p" style="border: 0.5px solid #bbb"> -->
                    {{ dogGender }}
                  </p>
                </div>
              </div>
              <div>
                <div>
                  <label>5. 털색</label>
                </div>
                <div>
                  <p style="border-bottom : 0.5px solid #bbb; width: 30%;">
                  <!-- <p class="fur-p" style="border: 0.5px solid #bbb"> -->
                    {{ dogFur }}
                  </p>
                </div>
              </div>
            </div>
          </div>
          <hr/>

          <h4>입양희망자 정보</h4>
          <div>
            <div>
              <v-col>
                <v-text-field
                  v-model="personTitle"
                  label="제목"
                  clearable
                  :rules="rule[0]"
                ></v-text-field>
              </v-col>
              <v-col>
                <v-text-field
                  v-model="personName"
                  label="이름"
                  clearable
                  :rules="rule[1]"
                ></v-text-field>
              </v-col>
            </div>
            <div>
              <div>
                <label>3. 휴대폰</label>
              </div>
              <div>
                <input
                  style="border: 0.5px solid #bbb"
                  v-model="firstNum"
                  placeholder="ex> 010"
                />
                <label
                  style="color: red; font-size: small"
                  v-if="personNumberAuthenticationNotFinish"
                  >인증을 완료해주세요</label
                >
              </div>
              <div>
                <input
                  style="border: 0.5px solid #bbb"
                  v-model="middleNum"
                  placeholder="1234"
                />
              </div>
              <div>
                <input
                  style="border: 0.5px solid #bbb"
                  v-model="lastNum"
                  placeholder="5678"
                />
              </div>
              <div>
                <input
                  v-if="pressedAuthenticationBtn === 0"
                  @click="phoneAuthentication"
                  style="
                    border: 0.5px solid #bbb;
                    background: black;
                    color: white;
                  "
                  type="button"
                  value="문자인증"
                />
                <div>
                  <input
                    v-if="pressedAuthenticationBtn === 1"
                    placeholder="인증번호를 입력해주세요"
                    style="border: 0.5px solid #bbb"
                    v-model="personNumberAuthenticationInput"
                  />
                  <label
                    style="color: blue; font-size: small"
                    v-if="personNumberAuthenticationFinish"
                    >인증완료</label
                  >
                  <label
                    style="color: red; font-size: small"
                    v-if="personNumberAuthenticationWrong"
                    >틀렸습니다</label
                  >
                </div>
              </div>
            </div>
            <div>
              <div>
                <label>4. 이메일</label>
              </div>
              <div>
                <input
                  style="border: 0.5px solid #bbb"
                  v-model="personEmail"
                  placeholder="ex> naver@naver.com"
                />
                <label
                  v-if="checkPersonEmail === 0"
                  style="color: red; font-size: small"
                  >이메일을 확인해주세요</label
                >
              </div>
            </div>
            <div>
              <div>
                <label>5. 개인정보 이용에 대한 동의</label>
              </div>
            </div>
            <div>
              <div>
                <ul>
                  <li>
                    개인정보의 보유 및 이용기간: 3년(관계법령에서 정한 일정한
                    기간)
                  </li>
                  <li>
                    수집하는 개인정보의 항목: 성명, 연락처, 이메일, 성별,
                    생년월일, 주소
                  </li>
                  <li>
                    개인정보의 수집,이용 목적: 추후 댕줍멍줍의 소식을 전달하기
                    위함
                  </li>
                </ul>
              </div>
            </div>
            <div>
              <p style="margin-bottom: 3px">
                법률에서 정하는 경우를 제외하고 귀하의 동의 없이 개인정보를 제
                3자에게 제공하지 않습니다.
              </p>
              <p style="margin-bottom: 3px">문의: 010-0000-0000</p>
              <div style="display: flex">
                <div>
                  <input
                    type="checkbox"
                    style="width: 20px"
                    v-model="personCheck"
                  />
                </div>
                <div>
                  <label>
                    <p style="margin-bottom: 0px">
                      개인정보 이용에 동의합니다.
                    </p>
                    <label
                      v-if="checkPersonCheck === 0"
                      style="color: red; font-size: small"
                      >개인정보 이용 동의에 체크해주세요</label
                    >
                  </label>
                </div>
              </div>
            </div>
            <div
              style="display: flex; justify-content: center"
            >
              <button @click="adoptionCheck()">
                신청
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Header from "../components/Header.vue";
import Datepicker from "vuejs-datepicker";
import { en, ko } from "vuejs-datepicker/dist/locale/";
import axios from "axios";
import SERVER from "@/api/url";

export default {
  name: "Adoption",
  components: {
    Header,
    Datepicker,
  },
  data() {
    return {
      dogInfo: [],
      dogSerial: "",
      dogAge: "",
      dogBreed: "",
      dogGender: "",
      dogFur: "",

      personTitle: "",
      personName: "",
      personEmail: "",
      personCheck: false,
      firstNum: "",
      middleNum: "",
      lastNum: "",
      pressedAuthenticationBtn: 0,
      personNumberAuthentication: 0,
      personNumberAuthenticationInput: "",
      personNumberAuthenticationFinish: 0,
      personNumberAuthenticationWrong: 0,
      personNumberAuthenticationNotFinish: 0,

      checkPersonTitle: 1,
      checkPersonName: 1,
      checkPersonEmail: 1,
      checkPersonCheck: 1,

      rule: [
        [(value) => !!value || "제목을 입력해 주세요."],
        [(value) => !!value || "이름을 입력해 주세요."]
      ]
    };
  },

  computed: {
    ...mapState(["address"]),
  },

  methods: {
    ...mapMutations(["findPersonAddress"]),
    changePersonCheck() {
      if (this.personCheck === 0) {
        this.personCheck = 1;
      } else {
        this.personCheck = 0;
      }
    },

    phoneAuthentication() {
      SERVER.tokenCheck(() => {
        axios
          .get(SERVER.URL + "/user/adopt/create", {
            params: {
              phone: this.firstNum + "-" + this.middleNum + "-" + this.lastNum,
            },
            headers: {
              Authorization: this.$cookies.get("accessToken"),
            },
          })
          .then((res) => {
            this.pressedAuthenticationBtn = 1;
            this.personNumberAuthentication = res.data.number;
          })
          .catch((err) => {
            SERVER.RefreshToken(err);
          });
      });
    },

    adoptionCheck() {
      if (this.personTitle.length === 0) {
        this.checkPersonTitle = 0;
      } else {
        this.checkPersonTitle = 1;
      }

      if (this.personName.length === 0) {
        this.checkPersonName = 0;
      } else {
        this.checkPersonName = 1;
      }

      if (this.personNumberAuthenticationFinish !== 1) {
        this.personNumberAuthenticationNotFinish = 1;
      } else {
        this.personNumberAuthenticationNotFinish = 0;
      }

      if (this.personEmail.length > 0) {
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (this.personEmail.match(regExp) != null) {
          this.checkPersonEmail = 1;
        } else {
          this.checkPersonEmail = 0;
        }
      } else {
        this.checkPersonEmail = 0;
      }

      if (this.personCheck === false) {
        this.checkPersonCheck = 0;
      } else {
        this.checkPersonCheck = 1;
      }

      if (
        this.checkPersonTitle === 1 &&
        this.checkPersonName === 1 &&
        // 여기에 추후에 전화번호 인증 여부도 조건에 추가해야 한다!
        // 지금은 문자 포인트 문제로 빼놓음
        // this.personNumberAuthenticationFinish === 1 &&
        this.checkPersonEmail === 1 &&
        this.checkPersonCheck === 1
      ) {
        this.adoptionApply();
      }
    },

    adoptionApply() {
      SERVER.tokenCheck(() => {
        axios
          .post(
            SERVER.URL + "/user/adopt/create",
            {
              ani_num: this.dogSerial,
              user_name: this.personName,
              user_phone:
                this.firstNum + "-" + this.middleNum + "-" + this.lastNum,
              user_email: this.personEmail,
              title: this.personTitle,
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
    personTitle() {
      if (this.personTitle.length === 0) {
        this.checkPersonTitle = 0;
      } else {
        this.checkPersonTitle = 1;
      }
    },

    personName() {
      if (this.personName.length === 0) {
        this.checkPersonName = 0;
      } else {
        this.checkPersonName = 1;
      }
    },

    personNumberAuthenticationInput() {
      if (
        this.personNumberAuthenticationInput == this.personNumberAuthentication
      ) {
        this.personNumberAuthenticationFinish = 1;
      }

      if (this.personNumberAuthenticationInput.length === 6) {
        if (
          this.personNumberAuthenticationInput !=
          this.personNumberAuthentication
        ) {
          this.personNumberAuthenticationWrong = 1;
        } else {
          this.personNumberAuthenticationWrong = 0;
        }
      }
    },

    personEmail() {
      if (this.personEmail.length > 0) {
        var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (this.personEmail.match(regExp) != null) {
          this.checkPersonEmail = 1;
        } else {
          this.checkPersonEmail = 0;
        }
      } else {
        this.checkPersonEmail = 0;
      }
    },

    personCheck() {
      if (this.personCheck === false) {
        this.checkPersonCheck = 0;
      } else {
        this.checkPersonCheck = 1;
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
          this.dogSerial = res.data.animalList.desertion_no;
          this.dogAge = 2020 - res.data.animalList.age + "살";
          this.dogBreed = res.data.animalList.kind_c;
          if (res.data.animalList.sex_cd == "M") {
            this.dogGender = "남";
          } else {
            this.dogGender = "여";
          }
          this.dogFur = res.data.animalList.color_cd;
        })
        .catch((err) => {
          SERVER.RefreshToken(err);
        });
    });
  },
};
</script>