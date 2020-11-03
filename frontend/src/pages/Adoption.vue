<template>
  <div>
    <Header />
    <div class="container" style="height: 700px; margin-top: 65px;">
      <div class="row">
        <div class="application col-lg-8 col-md-8 col-sm-8">
          <h2 class="application-title">입양신청서</h2>
          <div class="selfcheck">
            <h3 class="selfcheckstart">입양할 준비가 되셨는지 확인해보세요</h3>
            <ul class="selfchecklist">
              <li>입양에 필요한 비용을 감당하실 수 있나요?</li>
              <li>정기적인 검진에 따른 비용을 감당하실 수 있나요?</li>
              <li>사료, 간식, 장난감 등의 비용을 감당하실 수 있나요?</li>
              <li>매일 반려견과 30분 이상 산책하실 수 있나요?</li>
              <li>가족과 함께 살고 있다면 가족 구성원 모두 반려견 입양에 동의하셨나요?</li>
              <li>입양을 희망하는 견종의 특성과 성향을 파악하고 계신가요?</li>
              <li>휴가를 갈때 반려견이 함께 갈 수 없는 경우에 반려견을 어떻게 할지 생각해보셨나요?</li>
            </ul>
          </div>
          <hr class="dog-information-startline">
          <h3>유기동물 정보</h3>
          <div class="dog-information">
            <div class="row dog-information-serial">
              <div class="col-2">
                <label>1. 일련번호</label>
              </div>
              <div class="col-10">
                <p class="serial-p" style="border: 1px solid black;">{{ dogSerial }}</p>
              </div>
            </div>
            <div class="row dog-information-name">
              <div class="col-2">
                <label>2. 이름</label>
              </div>
              <div class="col-10">
                <p class="name-p" style="border: 1px solid black;">{{ dogName }}</p>
              </div>
            </div>
            <div class="row dog-information-breed">
              <div class="col-2">
                <label>3. 종류</label>
              </div>
              <div class="col-10">
                <p class="breed-p" style="border: 1px solid black;">{{ dogBreed }}</p>
              </div>
            </div>
            <div class="row dog-information-gender">
              <div class="col-2">
                <label>4. 성별</label>
              </div>
              <div class="col-10">
                <p class="gender-p" style="border: 1px solid black;">{{ dogGender }}</p>
              </div>
            </div>
          </div>
          <hr class="dog-information-endline">

        <h3>입양희망자 정보</h3>
        <div class="adopter-information">
          <div class="row adopter-information-name">
            <div class="col-2">
              <label>1. 제목</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black" v-model="personTitle"/>
              <label class="personTitle" v-if="checkPersonTitle === 0" style="color: red; font-size: small;">제목을 입력해주세요</label>
            </div>
            <div class="col-2">
              <label>2. 성명</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black" v-model="personName"/>
              <label class="personNameLabel" v-if="checkPersonName === 0" style="color: red; font-size: small;">성명은 입력해주세요</label>
            </div>
          </div>
          <div class="row adopter-information-number">
            <div class="col-2">
              <label>3. 휴대폰</label>
            </div>
            <div class="col-2">
              <input style="border: 1px solid black" v-model="firstNum" placeholder="010"/>
            </div>
            <div class="col-2">
              <input style="border: 1px solid black" v-model="middleNum" placeholder="1234"/>
            </div>
            <div class="col-2">
              <input style="border: 1px solid black" v-model="lastNum" placeholder="5678"/>
            </div>
            <div class="col-4">
              <input v-if="pressedAuthenticationBtn === 0" class="check-number" @click="phoneAuthentication" style="border: 1px solid black; background: black; color: white;" type="button" value="문자인증">
              <div class="flex">
                <input v-if="pressedAuthenticationBtn === 1" placeholder="인증번호를 입력해주세요" style="border: 1px solid black;" v-model="personNumberAuthenticationInput"/>
                <label style="color: blue;" v-if="personNumberAuthenticationFinish">인증완료</label>
                <label style="color: red;" v-if="personNumberAuthenticationWrong">틀렸습니다</label>
              </div>
            </div>
          </div>
            <div class="row adopter-information-email">
              <div class="col-2">
                <label>4. 이메일</label>
              </div>
              <div class="col-10">
                <input style="border: 1px solid black;" v-model="personEmail" placeholder="naver@naver.com">
                <label v-if="checkPersonEmail === 0" style="color: red; font-size: small;">이메일을 확인해주세요</label>
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
                  <li>개인정보의 보유 및 이용기간: 3년(관계법령에서 정한 일정한 기간)</li>
                  <li>수집하는 개인정보의 항목: 성명, 연락처, 이메일, 성별, 생년월일, 주소</li>
                  <li>개인정보의 수집,이용 목적: 추후 댕줍멍줍의 소식을 전달하기 위함</li>
                </ul>
              </div>
            </div>
            <div class="adopter-information-personal-allow">
              <p style="margin-bottom: 0px;">법률에서 정하는 경우를 제외하고 귀하의 동의 없이 개인정보를 제 3자에게 제공하지 않습니다.</p>
              <p style="margin-bottom: 0px;">문의: 010-0000-0000</p>
              <div style="display: flex">
                <div>
                  <input type="checkbox" style="width: 20px;" v-model="personCheck"/>
                </div>
                <div>
                  <label>
                    <p style="margin-bottom: 0px;">개인정보 이용에 동의합니다.</p>
                    <label v-if="checkPersonCheck === 0" style="color: red; font-size: small;">개인정보 이용 동의에 체크해주세요</label>
                  </label>
                </div>
              </div>
            </div>
            <div class="apply-finish" style="display: flex; justify-content: center;">
              <button class="apply-button" @click="adoptionCheck()">신청</button>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Header from '../components/Header.vue'
import Datepicker from 'vuejs-datepicker';
import { en, ko } from 'vuejs-datepicker/dist/locale/'
import axios from 'axios'
import SERVER from '@/api/url'

export default {
  name: "Adoption",
  components: {
    Header,
    Datepicker
  },
  data() {
    return {
      dogInfo: [],
      dogSerial: "",
      dogName: "",
      dogBreed: "",
      dogGender: "",

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
      format: "yyyy년 MMMM dd일",
      en: en,
      ko: ko,
      checkPersonTitle: 1,
      checkPersonName: 1,
      checkPersonEmail: 1,
      checkPersonCheck: 1,
    };
  },

  computed: {
    ...mapState(["address"])
  },

  methods: {
    ...mapMutations(["findPersonAddress"]),
    changePersonCheck() {
      if (this.personCheck === 0) {
        this.personCheck = 1
      } else {
        this.personCheck = 0
      }
    },

    phoneAuthentication() {
      console.log("FE input Form : ", this.firstNum + "-" + this.middleNum + "-" + this.lastNum)

      axios.get(SERVER.URL + '/user/adopt/create', {
          params: {
              phone: this.firstNum + "-" + this.middleNum + "-" + this.lastNum
          },
          headers: {
            Authorization: this.$cookies.get("accessToken"),
            refreshToken: this.$cookies.get("refreshToken")
          }
        })
        .then((res) => {
          console.log("then res : ",res.data)
          this.pressedAuthenticationBtn = 1
          this.personNumberAuthentication = res.data.number
          console.log(this.personNumberAuthentication)
        })
        .catch((err) => {
          console.log("catch err : ",err)
          if (err.response.status == 401) {
            axios
              .post(
                SERVER.URL + "/newuser/refresh",
                {},
                {
                  headers: {
                    accessToken: this.$cookies.get("accessToken"),
                    refreshToken: this.$cookies.get("refreshToken"),
                  },
                }
              )
              .then((res) => {
                console.log(res.data)
                if (res.data.success) {
                  this.$cookies.set("accessToken", res.data.accessToken)
                  console.log(this.$cookies.get("accessToken"))

                  axios.get(SERVER.URL + '/user/adopt/create', {
                      params: {
                          phone: this.firstNum + "-" + this.middleNum + "-" + this.lastNum
                      },
                      headers: {
                        Authorization: this.$cookies.get("accessToken"),
                        refreshToken: this.$cookies.get("refreshToken")
                      }
                    })
                    .then((res) => {
                      console.log("then res : ",res.data)
                      this.pressedAuthenticationBtn = 1
                      this.personNumberAuthentication = res.data.number
                    })
                    .catch((err) => {
                      console.log("catch err : ",err)
                })
              }
              })
              .catch((err) => {
                console.log(err);
              })
          }
        })
    },
    
    adoptionCheck() {
      console.log(this.personAddress)
      if (this.personTitle.length === 0) {
        this.checkPersonTitle = 0
      } else {
        this.checkPersonTitle = 1
      }

      if (this.personName.length === 0) {
        this.checkPersonName = 0
      } else {
        this.checkPersonName = 1
      }

      if (this.personEmail.length > 0) {
         var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
          if (this.personEmail.match(regExp) != null) {
            this.checkPersonEmail = 1
          }
          else {
            this.checkPersonEmail = 0
          }
      } else {
          this.checkPersonEmail = 0
      }

      if (this.personCheck === false) {
        this.checkPersonCheck = 0
      } else {
        this.checkPersonCheck = 1
      }

      if (this.checkPersonName === 1 && 
      this.checkPersonEmail === 1 &&
      this.checkPersonCheck === 1) {
        this.adoptionApply()
      }
    },

    adoptionApply() {
      console.log('adoptionAPPLY')
      console.log(this.personName,' ',this.firstNum,'-',this.middleNum,'-',this.lastNum,' ',this.personEmail,' ',this.personTitle)
      axios.post(SERVER.URL + '/user/adopt/create',
        {  
          ani_num: 0,
          user_name: this.personName,
          user_phone: this.firstNum + "-" + this.middleNum + "-" + this.lastNum,
          user_email: this.personEmail,
          title: this.personTitle,
        },
        {
          headers: {
            Authorization: this.$cookies.get("accessToken")
          }
        }
      )
      .then((res) => {
        console.log("then res : ",res.data)
      })
      .catch((err) => {
          console.log("catch err : ",err)
          if (err.response.status == 401) {
            //accessToken만료
            axios
              .post(
                SERVER.URL + "/newuser/refresh",
                {},
                {
                  headers: {
                    accessToken: this.$cookies.get("accessToken"),
                    refreshToken: this.$cookies.get("refreshToken"),
                  },
                }
              )
              .then((res) => {
                console.log(res);
                if (res.data.success) {
                  this.$cookies.set("accessToken", res.data.accessToken);
                  console.log(this.$cookies.get("accessToken"));
                }
              })
              .catch((err) => {
                console.log(err);
              })
          }
        })
    }
  },
  watch: {
    personTitle() {
      if (this.personTitle.length === 0) {
        this.checkPersonTitle = 0
      } else {
        this.checkPersonTitle = 1
      }
    },
    
    personName() {
      if (this.personName.length === 0) {
        this.checkPersonName = 0
      } else {
        this.checkPersonName = 1
      }
    },

    personEmail() {
      if (this.personEmail.length > 0) {
         var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
          if (this.personEmail.match(regExp) != null) {
            this.checkPersonEmail = 1
          }
          else {
            this.checkPersonEmail = 0
          }
      } else {
          this.checkPersonEmail = 0
      }
    },

    personGender() {
      if (this.personGender === 0 ) {
        this.checkPersonGender = 0
      } else {
        this.checkPersonGender = 1
      }
    },

    personBirthday() {
      if (this.personBirthday.length === 0) {
        this.checkPersonBirthday = 0
      } else {
        this.checkPersonBirthday = 1
      }

    },

    personAddress() {
      if (this.personAddress.length === 0) {
        this.checkPersonAddress = 0
      } else {
        this.checkPersonAddress = 1
      }
    },

    personCheck() {
      if (this.personCheck === false) {
        this.checkPersonCheck = 0
      } else {
        this.checkPersonCheck = 1
      }
    },

    personNumberAuthenticationInput() {
      if (this.personNumberAuthenticationInput == this.personNumberAuthentication) {
        console.log('인증 완료')
        this.personNumberAuthenticationFinish = 1
      }

      if (this.personNumberAuthenticationInput.length === 6) {
        if (this.personNumberAuthenticationInput != this.personNumberAuthentication) {
          this.personNumberAuthenticationWrong = 1
        } else {
          this.personNumberAuthenticationWrong = 0
        }
      }
    }
  },
}
</script>

<style>
  div.application {
    margin: 0 auto;
  }

  hr.dog-information-startline {
    border-top: 1px solid black;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  h2.application-title {
    text-align: center;
    margin-bottom: 10px;
  }

  div.selfcheck {
      background: rgb(244, 236, 225);
      padding: 12px 12px 5px 12px;
    }

  h3.selfcheckstart {
    text-align: center;
  }

  ul.selfchecklist {
    list-style-position: inside;
    padding-left: 15px;
    list-style-type: square;
  }

  div.list {
    background: rgb(244, 236, 225);
    padding: 12px 12px 1px 12px;
  }

  button.check-number {
    background: black;
    color: white;
  }

  div.adopter-information-personal-allow{
    margin-top: 10px;
  }

  ul.adopter-information-personal-ul {
    list-style-position: inside;
    padding-left: 15px;
    list-style-type: square;
  }
  
  div.col-2, div.col-10, div.col-8 {
    padding: 12px 12px 5px 12px;
  }

  hr.dog-information-endline {
    border-top: 1px solid black;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  p.serial-p, p.name-p, p.breed-p, p.gender-p {
    margin-bottom: 0;
  }

  input {
    width: 100%;
  }

  button.apply-button {
    background: green;
    color: white;
    width: 30%;
    border: 10px solid green;
  }
</style>