<template>
  <div class="container" style="height: 700px">
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
        <h3>유기동물 정보</h3>
        <div class="dog-information">
          <div class="row dog-information-serial">
            <div class="col-2">
              <label>일련번호</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black">???</p>
            </div>
          </div>
          <div class="row dog-information-name">
            <div class="col-2">
              <label>이름</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black">???</p>
            </div>
          </div>
          <div class="row dog-information-breed">
            <div class="col-2">
              <label>종류</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black">???</p>
            </div>
          </div>
          <div class="row dog-information-gender">
            <div class="col-2">
              <label>성별</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black">???</p>
            </div>
          </div>
        </div>
        <hr class="dog-information-endline" />

        <h3>입양희망자 정보</h3>
        <div class="adopter-information">
          <div class="row adopter-information-name">
            <div class="col-2">
              <label>성명</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black" />
            </div>
          </div>
          <div class="row adopter-information-number">
            <div class="col-2">
              <label>연락처</label>
            </div>
            <div class="col-2">
              <input style="border: 1px solid black" v-model="firstNum"/>
            </div>
            <div class="col-4">
              <input style="border: 1px solid black" v-model="middleNum" />
            </div>
            <div class="col-4">
              <input style="border: 1px solid black" v-model="lastNum"/>
            </div>
            <div class="col-2">
              <input
                v-model="phoneNum"
                style="border: 1px solid black; background: black; color: white"
                type="text"
                placeholder="휴대폰번호"
              />
              <button class="check-number" @click="test">문자인증</button>
            </div>
          </div>
          <div class="row adopter-information-email">
            <div class="col-2">
              <label>이메일</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black" />
            </div>
          </div>
          <div class="row adopter-information-gender">
            <div class="col-2">
              <label>성별</label>
            </div>
            <div class="col-2" style="display: flex">
              <div>
                <label for="female">여성</label>
              </div>
              <div>
                <input
                  type="radio"
                  name="gender"
                  id="female"
                  style="width: 20px"
                />
              </div>
            </div>
            <div class="col-2" style="display: flex">
              <div>
                <label for="male">남성</label>
              </div>
              <div>
                <input
                  type="radio"
                  name="gender"
                  id="male"
                  style="width: 20px"
                />
              </div>
            </div>
          </div>
          <div class="row adopter-information-birthday">
            <div class="col-2">
              <label>생년월일</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black" />
            </div>
          </div>
        </div>
        <div class="row adopter-information-address">
          <div class="col-2">
            <label>주소</label>
          </div>
          <div class="col-10">
            <input
              style="border: 1px solid black"
              @click="findAddress()"
              v-model="address"
            />
          </div>
        </div>
        <div class="row adopter-information-personal">
          <div class="col-12">
            <label>개인정보 이용에 대한 동의</label>
          </div>
        </div>
        <div class="adopter-information-personal-list">
          <div class="list">
            <ul class="adopter-information-personal-ul">
              <li>
                개인정보의 보유 및 이용기간: 3년(관계법령에서 정한 일정한 기간)
              </li>
              <li>
                수집하는 개인정보의 항목: 성명, 연락처, 이메일, 성별, 생년월일,
                주소
              </li>
              <li>
                개인정보의 수집,이용 목적: 추후 댕줍멍줍의 소식을 전달하기 위함
              </li>
            </ul>
          </div>
        </div>
        <div class="adopter-information-personal-allow">
          <h5>
            댕줍멍줍은 법률에서 정하는 경우를 제외하고는 귀하의 동의 없이
            개인정보를 제 3자에게 제공하지 않습니다.
          </h5>
          <h5>문의: 010-0000-0000</h5>
          <div style="display: flex">
            <div>
              <label>
                <h5>개인정보 이용에 동의합니다.</h5>
              </label>
            </div>
            <div>
              <input type="checkbox" style="width: 20px" />
            </div>
          </div>
        </div>
        <div
          class="apply-finish"
          style="display: flex; justify-content: center"
        >
          <button class="apply-button">신청</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import Header from '../components/Header.vue'
import axios from 'axios'
import SERVER from '@/api/url'

export default {
  name: "Adoption",
  components: {
    // Header,
  },
  data() {
    return {
      address: "",
      phoneNum: "",
      firstNum: "",
      middleNum: "",
      lastNum: ""
    };
  },
  methods: {
    findAddress() {
      new daum.Postcode({
        // oncomplete: function(data) {
        //   var fulladdress = data.address;
        //   this.address = fulladdress
        //   console.log(this.address)
        // }
        oncomplete: (data) => {
          var fullAddr = data.address;
          var extraAddr = "";

          if (data.addressType === "R") {
            if (data.bname !== "") {
              extraAddr += data.bname;
            }
            if (data.buildingName !== "") {
              extraAddr +=
                extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
            }
            fullAddr += extraAddr !== "" ? " (" + extraAddr + ")" : "";
            this.address = fullAddr;
          } else {
            this.address = fullAddr;
          }
        },
      }).open();
    },
    test() {
      console.log("FE input Form : ", this.firstNum + "-" + this.middleNum + "-" + this.lastNum)
      axios.get(SERVER.URL + '/user/adopt/create', {
          params: {
            phone: this.firstNum + "-" + this.middleNum + "-" + this.lastNum
          }
        })
      .then((res) => {
        console.log("then res : ",res.data)
      })
      .catch((err) => {
        console.log("catch err : ",err)
      })
    }
  },
};
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
  list-style-position: inside;
  padding: 12px;
}

h3.selfcheckstart {
  text-align: center;
}

ul.selfchecklist {
  padding-left: 15px;
}

div.list {
  background: rgb(244, 236, 225);
  list-style-position: inside;
  padding: 12px;
}

button.check-number {
  background: black;
  color: white;
}

ul.adopter-information-personal-ul {
  padding-left: 15px;
}

div.col-2,
div.col-10 {
  padding-bottom: 5px;
}

hr.dog-information-endline {
  border-top: 1px solid black;
  margin-top: 10px;
  margin-bottom: 10px;
}

p {
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