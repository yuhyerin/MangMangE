<template>
  <div class="container" style="height: 700px; margin-top: 75px;">
    <div class="row">
      <div class="application col-lg-8 col-md-8 col-sm-8">

        <h2 class="application-title">입양신청서</h2>
        <hr class="application-title-endline">
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

        <h3>유기동물 정보</h3>
        <div class="dog-information">
          <div class="row dog-information-serial">
            <div class="col-2">
              <label>일련번호</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black;">???</p>
            </div>
          </div>
          <div class="row dog-information-name">
            <div class="col-2">
              <label>이름</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black;">???</p>
            </div>
          </div>
          <div class="row dog-information-breed">
            <div class="col-2">
              <label>종류</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black;">???</p>
            </div>
          </div>
          <div class="row dog-information-gender">
            <div class="col-2">
              <label>성별</label>
            </div>
            <div class="col-10">
              <p style="border: 1px solid black;">???</p>
            </div>
          </div>
        </div>
        <hr class="dog-information-endline">

        <h3>입양희망자 정보</h3>
        <div class="adopter-information">
          <div class="row adopter-information-name">
            <div class="col-2">
              <label>성명</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black;">
            </div>
          </div>
          <div class="row adopter-information-number">
            <div class="col-2">
              <label>연락처</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black;">
            </div>
          </div>
          <div class="row adopter-information-email">
            <div class="col-2">
              <label>이메일</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black;">
            </div>
          </div>
          <div class="row adopter-information-gender">
            <div class="col-2">
              <label>성별</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black;">
            </div>
          </div>
          <div class="row adopter-information-birthday">
            <div class="col-2">
              <label>생년월일</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black;">
            </div>
          </div>
        </div>
        <div class="row adopter-information-address">
            <div class="col-2">
              <label>주소</label>
            </div>
            <div class="col-10">
              <input style="border: 1px solid black;">
            </div>
          </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Adoption'
}
</script>

<style>
  div.application {
    margin: 0 auto;
  }

  hr.application-title-endline {
    border-top: 1px solid black;
  }

  h2.application-title {
    text-align: center;
  }

  div.selfcheck {
    background: rgb(244, 236, 225);
    list-style-position: inside;
  }

  h3.selfcheckstart {
    text-align: center;
  }

  ul.selfchecklist {
    padding-left: 15px;
  }

  div.col-2, div.col-10 {
    padding-bottom: 0px;
  }

  hr.dog-information-endline {
    border-top: 1px solid black;
  }

  p {
    margin-bottom: 0;
  }

  input {
    width: 100%;
  }
</style>