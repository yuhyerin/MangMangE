<template>
  <div class="section s1">
    <div class="container s4">
      <div class="cards">
        <div class="first">
          <img class="img1" src="../assets/image/mainpage/section4-dog.jpg" />
          <h2 class="first-word">강아지 정보</h2>
        </div>
        <div class="second">
          <img
            class="img2"
            src="../assets/image/mainpage/section4-notice.jpg"
          />
          <h2 class="second-word">공지사항</h2>
        </div>
        <div class="third">
          <img class="img3" src="../assets/image/mainpage/section4-adopt.jpg" @click="moveToAdoptionList" />
          <h2 class="third-word">입양 신청 목록</h2>
        </div>
      </div>
    </div>
    <div class="footer" style="color: white;">
      <div class="footer-first" style="margin-top: 20px;">
        <h2 style="margin-bottom: 5px;">CONTACT</h2>
        <hr width="100px;" style="margin: 0 auto;">
        <br>
        <h4>SSAFY@SSAFY.COM</h4>
        <h4>010-1234-5678</h4>
      </div>
      <div class="footer-second" style="margin-top: 20px;">
        <h2 style="margin-bottom: 5px;">오시는길</h2>
        <hr width="100px;" style="margin: 0 auto;">
        <br>
        <h4>대전시 유성구</h4>
        <h4>덕명동 111번지</h4>
      </div>
      <div class="footer-third" style="margin-top: 20px;">
        <h2 style="margin-bottom: 5px;">영업시간</h2>
        <hr width="100px;" style="margin: 0 auto;">
        <br>
        <h4>09시 00분 ~</h4>
        <h4>18시 00분</h4>
      </div>
      <div class="footer-fourth" style="margin-top: 20px;">
        <h2 style="margin-bottom: 5px;">개발자</h2>
        <hr width="100px;" style="margin: 0 auto;">
        <br>
        <h4>유혜린 박경수</h4>
        <h4>정현희 고소영</h4>
        <h4>김승환 최규식</h4>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Section4",
  methods: {
    moveToAdoptionList() {
      if (this.$cookies.get("accessToken")) {
        this.$router.push('/adoptionlist')  
      } else {
        alert('로그인해주세요')
      }
    }
  }
}
</script>

<style>
.s3 {
  background-color: white;
}

.container.s4 {
  height: 754px;
  margin-top: 20px;
}

img.img1,
img.img2,
img.img3 {
  height: 200px;
  width: 100%;
  border-radius: 10px;
}

div.cards {
  margin: 10px auto;
}

div.cards:hover {
  cursor: pointer;
}

div.first {
  float: left;
  width: 30%;
  box-sizing: border-box;
}

div.second {
  float: left;
  margin-left: 5%;
  width: 30%;
  box-sizing: border-box;
}

div.third {
  float: right;
  width: 30%;
  box-sizing: border-box;
}

.first-word,
.second-word,
.third-word {
  text-align: center;
}

.first-word:hover,
.second-word:hover,
.third-word:hover {
  text-decoration: None;
  border-bottom: 1px solid black;
}

div.footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 30vh;
  background: #ccc;
  margin: 0;
}

div.footer-first {
  float: left;
  width: 20%;
  box-sizing: border-box;
}

div.footer-second {
  float: left;
  margin-left: 5%;
  width: 20%;
  box-sizing: border-box;
}

div.footer-third {
  float: left;
  margin-left: 10%;
  width: 20%;
  box-sizing: border-box;
}

div.footer-fourth {
  float: right;
  width: 20%;
  box-sizing: border-box;
}

div.footer-first,
div.footer-second,
div.footer-third,
div.footer-fourth {
  text-align: center;
}
</style>