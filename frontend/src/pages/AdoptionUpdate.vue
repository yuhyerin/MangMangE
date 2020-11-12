<template>
  <div>
    <Header />
    <div class="container" style="height: 710px; margin-top: 65px">
      <div class="row">
        <div class="application col-lg-10 col-md-10 col-sm-10">
          <h4 class="application-title">입양신청서</h4>
          <div class="selfcheck">
            <h5 class="selfcheckstart">입양할 준비가 되셨는지 확인해보세요</h5>
            <ul class="selfchecklist">
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
          <hr class="dog-information-startline" />
          <h5>유기동물 정보</h5>
          <div class="flex">
            <div class="dog-information">
              <div class="row dog-information-serial">
                <div class="col-2">
                  <label>1. 일련번호</label>
                </div>
                <div class="col-10">
                  <p class="serial-p" style="border: 0.5px solid #bbb">
                    {{ dogSerial }}
                  </p>
                </div>
              </div>
              <div class="row dog-information-age">
                <div class="col-2">
                  <label>2. 추정나이</label>
                </div>
                <div class="col-10">
                  <p class="age-p" style="border: 0.5px solid #bbb">
                    {{ dogAge }}
                  </p>
                </div>
              </div>
              <div class="row dog-information-breed">
                <div class="col-2">
                  <label>3. 종류</label>
                </div>
                <div class="col-10">
                  <p class="breed-p" style="border: 0.5px solid #bbb">
                    {{ dogBreed }}
                  </p>
                </div>
              </div>
              <div class="row dog-information-gender">
                <div class="col-2">
                  <label>4. 성별</label>
                </div>
                <div class="col-10">
                  <p class="gender-p" style="border: 0.5px solid #bbb">
                    {{ dogGender }}
                  </p>
                </div>
              </div>
              <div class="row dog-information-fur">
                <div class="col-2">
                  <label>5. 털색</label>
                </div>
                <div class="col-10">
                  <p class="fur-p" style="border: 0.5px solid #bbb">
                    {{ dogFur }}
                  </p>
                </div>
              </div>
            </div>
          </div>
          <hr class="dog-information-endline" />

          <h5>입양희망자 정보</h5>
          <div class="adopter-information">
            <div class="row adopter-information-name">
              <div class="col-2">
                <label>1. 제목</label>
              </div>
              <div class="col-10">
                <input style="border: 0.5px solid #bbb" v-model="personTitle" />
                <label
                  class="personTitle"
                  v-if="checkPersonTitle === 0"
                  style="color: red; font-size: small"
                  >제목을 입력해주세요</label
                >
              </div>
              <div class="col-2">
                <label>2. 성명</label>
              </div>
              <div class="col-10">
                <input style="border: 0.5px solid #bbb" v-model="personName" />
                <label
                  class="personNameLabel"
                  v-if="checkPersonName === 0"
                  style="color: red; font-size: small"
                  >성명을 입력해주세요</label
                >
              </div>
            </div>
            <div class="row adopter-information-number">
              <div class="col-2">
                <label>3. 휴대폰</label>
              </div>
              <div class="col-2">
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
              <div class="col-2">
                <input
                  style="border: 0.5px solid #bbb"
                  v-model="middleNum"
                  placeholder="1234"
                />
              </div>
              <div class="col-2">
                <input
                  style="border: 0.5px solid #bbb"
                  v-model="lastNum"
                  placeholder="5678"
                />
              </div>
              <div class="col-4">
                <input
                  v-if="pressedAuthenticationBtn === 0"
                  class="check-number"
                  @click="phoneAuthentication"
                  style="
                    border: 0.5px solid #bbb;
                    background: black;
                    color: white;
                  "
                  type="button"
                  value="문자인증"
                />
                <div class="person-authentication-check">
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
            <div class="row adopter-information-email">
              <div class="col-2">
                <label>4. 이메일</label>
              </div>
              <div class="col-10">
                <input style="border: 0.5px solid #bbb" v-model="personEmail" />
              </div>
            </div>
          </div>
          <div class="row adopter-information-personal">
            <div class="col-12">
              <label>5. 개인정보 이용에 대한 동의</label>
            </div>
          </div>
          <div class="adopter-information-personal-list">
            <div class="list">
              <ul class="adopter-information-personal-ul">
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
          <div class="adopter-information-personal-allow">
            <p style="margin-bottom: 3px">
              법률에서 정하는 경우를 제외하고 귀하의 동의 없이 개인정보를 제
              3자에게 제공하지 않습니다.
            </p>
            <p style="margin-bottom: 3px">문의: 010-0000-0000</p>
            <div style="display: flex">
              <div>
                <input type="checkbox" style="width: 20px" checked disabled />
              </div>
              <div>
                <label>
                  <p style="margin-bottom: 0px">개인정보 이용에 동의합니다.</p>
                </label>
              </div>
            </div>
          </div>
          <div
            class="apply-finish"
            style="display: flex; justify-content: center"
          >
            <button class="apply-button" @click="adoptionCheck">수정</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import axios from "axios";
import SERVER from "@/api/url";

export default {
  name: "AdoptionUpdate",
  components: {
    Header,
  },
  data() {
    return {
      uid: 0,

      dogSerial: "",
      dogAge: "",
      dogBreed: "",
      dogGender: "",
      dogFur: "",

      personTitle: "",
      firstNum: "",
      middleNum: "",
      lastNum: "",

      pressedAuthenticationBtn: 0,
      personNumberAuthentication: 0,
      personNumberAuthenticationInput: "",
      personNumberAuthenticationFinish: 0,
      personNumberAuthenticationWrong: 0,
      personNumberAuthenticationNotFinish: 0,

      personPhone: "",
      personName: "",
      personEmail: "",
      personRegtime: "",

      checkPersonTitle: 1,
      checkPersonName: 1,
      checkPersonEmail: 1,
    };
  },
  methods: {
    phoneAuthentication() {
      console.log(
        "FE input Form : ",
        this.firstNum + "-" + this.middleNum + "-" + this.lastNum
      );
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
            console.log("then res : ", res.data);
            this.pressedAuthenticationBtn = 1;
            this.personNumberAuthentication = res.data.number;
          })
          .catch((err) => {
            console.log("catch err : ", err);
            SERVER.refreshToken(err);
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

      if (
        this.checkPersonTitle === 1 &&
        this.checkPersonName === 1 &&
        // 여기에 추후에 전화번호 인증 여부도 조건에 추가해야 한다!
        // 지금은 문자 포인트 문제로 빼놓음
        // this.personNumberAuthenticationFinish === 1 &&
        this.checkPersonEmail === 1
      ) {
        this.adoptionUpdate();
      }
    },

    adoptionUpdate() {
      SERER.tokenCheck(() => {
        axios
          .post(
            SERVER.URL + "/user/adopt/create",
            {
              uid: this.uid,
              ani_num: this.dogSerial,
              title: this.personTitle,
              user_phone:
                this.firstNum + "-" + this.middleNum + "-" + this.lastNum,
              user_name: this.personName,
              user_email: this.personEmail,
              regtime: this.personRegtime,
            },
            {
              headers: {
                Authorization: this.$cookies.get("accessToken"),
              },
            }
          )
          .then((res) => {
            console.log("then res : ", res.data);
            alert("수정이 완료됐습니다");
            this.$router.push("/adoptionlist");
          })
          .catch((err) => {
            console.log("catch err : ", err);
          });
      });
    },
  },

  created() {
    console.log(this.$route.params.uid);
    this.uid = this.$route.params.uid;
    // 내글 불러오기
    SERVER.tokenCheck(() => {
      axios
        .get(SERVER.URL + `/user/adopt/read/${this.$route.params.uid}`, {
          headers: {
            Authorization: this.$cookies.get("accessToken"),
          },
        })
        .then((res) => {
          console.log(res.data);
          this.dogSerial = res.data.apply.ani_num;
          this.personTitle = res.data.apply.title;
          this.personPhone = res.data.apply.user_phone;
          this.personName = res.data.apply.user_name;
          this.personEmail = res.data.apply.user_email;
          this.personRegtime = res.data.apply.regtime;
          console.log(this.personRegtime);
          console.log(this.personTitle);
          // 동물정보 불러오기
          axios
            .get(SERVER.URL + "/newuser/animal/detail", {
              params: {
                desertion_no: this.dogSerial,
              },
            })
            .then((res) => {
              console.log(res.data);
              console.log("받음");

              // 비동기라 앞선 요청에서 받은 personPhone 자르기를 여기서
              this.firstNum = this.personPhone.slice(0, 3);
              this.middleNum = this.personPhone.slice(4, 8);
              this.lastNum = this.personPhone.slice(9, 13);

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
              console.log(err.response);
              SERVER.RefreshToken(err);
            });
        })
        .catch((err) => {
          console.log("catch err : ", err);
        });
    });
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
        console.log("인증 완료");
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
  },
};
</script>

<style>
</style>