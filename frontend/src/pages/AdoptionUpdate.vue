<template>
    <div>
    <Header />
    <div class="container" style="height: 710px; margin-top: 65px;">
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
              <li>가족과 함께 살고 있다면 가족 구성원 모두 반려견 입양에 동의하셨나요?</li>
              <li>입양을 희망하는 견종의 특성과 성향을 파악하고 계신가요?</li>
              <li>휴가를 갈때 반려견이 함께 갈 수 없는 경우에 반려견을 어떻게 할지 생각해보셨나요?</li>
            </ul>
          </div>
          <hr class="dog-information-startline">
          <h5>유기동물 정보</h5>
          <div class="flex">
          <div class="dog-information">
            <div class="row dog-information-serial">
              <div class="col-2">
                <label>1. 일련번호</label>
              </div>
              <div class="col-10">
                <p class="serial-p" style="border: 0.5px solid #bbb;">{{ dogSerial }}</p>
              </div>
            </div>
            <div class="row dog-information-age">
              <div class="col-2">
                <label>2. 추정나이</label>
              </div>
              <div class="col-10">
                <p class="age-p" style="border: 0.5px solid #bbb;">{{ dogAge }}</p>
              </div>
            </div>
            <div class="row dog-information-breed">
              <div class="col-2">
                <label>3. 종류</label>
              </div>
              <div class="col-10">
                <p class="breed-p" style="border: 0.5px solid #bbb;">{{ dogBreed }}</p>
              </div>
            </div>
            <div class="row dog-information-gender">
              <div class="col-2">
                <label>4. 성별</label>
              </div>
              <div class="col-10">
                <p class="gender-p" style="border: 0.5px solid #bbb;">{{ dogGender }}</p>
              </div>
            </div>
            <div class="row dog-information-fur">
              <div class="col-2">
                <label>5. 털색</label>
              </div>
              <div class="col-10">
                <p class="fur-p" style="border: 0.5px solid #bbb;">{{ dogFur }}</p>
              </div>
            </div>
          </div>
          </div>
          <hr class="dog-information-endline">

        <h5>입양희망자 정보</h5>
        <div class="adopter-information">
          <div class="row adopter-information-name">
            <div class="col-2">
              <label>1. 제목</label>
            </div>
            <div class="col-10">
              <p style="border: 0.5px solid #bbb;">{{ personTitle }}</p>
            </div>
            <div class="col-2">
              <label>2. 성명</label>
            </div>
            <div class="col-10">
              <p style="border: 0.5px solid #bbb;">{{ personName }}</p>
            </div>
          </div>
          <div class="row adopter-information-number">
            <div class="col-2">
              <label>3. 휴대폰</label>
            </div>
            <div class="col-10">
              <p style="border: 0.5px solid #bbb;">{{ personPhone }}</p>
            </div>

          </div>
            <div class="row adopter-information-email">
              <div class="col-2">
                <label>4. 이메일</label>
              </div>
              <div class="col-10">
                <p style="border: 0.5px solid #bbb;">{{ personEmail }}</p>
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
              <p style="margin-bottom: 3px;">법률에서 정하는 경우를 제외하고 귀하의 동의 없이 개인정보를 제 3자에게 제공하지 않습니다.</p>
              <p style="margin-bottom: 3px;">문의: 010-0000-0000</p>
              <div style="display: flex">
                <div>
                  <input type="checkbox" style="width: 20px;" checked />
                </div>
                <div>
                  <label>
                    <p style="margin-bottom: 0px;">개인정보 이용에 동의합니다.</p>
                    
                  </label>
                </div>
              </div>
            </div>
            <div class="apply-finish" style="display: flex; justify-content: center;">
              <button class="apply-button">신청</button>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue"
import axios from 'axios'
import SERVER from '@/api/url'

export default {
  name: 'AdoptionUpdate',
  components: {
    Header,
  },
  data() {
    return {
      dogSerial: "",
      dogAge: "",
      dogBreed: "",
      dogGender: "",
      dogFur: "",

      personTitle: "",
      personPhone: "",
      personName: "",
      personEmail: "",
    }
  },
  created() {
    console.log(this.$route.params.uid)

      // 내글 불러오기
      axios
        .get(SERVER.URL + "/user/adopt/readOne", {
          params: {
            uid: this.$route.params.uid
          },
          headers: {
            Authorization: this.$cookies.get("accessToken"),
          },
        })
        .then((res) => {
          console.log(res.data)
          this.personTitle = res.data.apply.title
          this.personPhone = res.data.apply.user_phone
          this.personName = res.data.apply.user_name
          this.personEmail = res.data.apply.user_email
          console.log(this.personTitle)
        })
        .catch((err) => {
          console.log("catch err : ", err);
        });

      // 동물정보 불러오기
      axios
      .get(SERVER.URL + "/newuser/animal/detail", {
        params: {
          desertion_no: 430364202000067,
        },
      })
      .then((res) => {
        console.log(res.data);
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
            });
        }
      });
  }
}
</script>

<style>

</style>