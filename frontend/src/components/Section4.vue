<template>
  <div class="section s3">
    <div class="container s4">
      <div class="cards">
        <div class="first" @click="moveToPrivacyPolicy">
          <img
            class="img2"
            src="../assets/image/mainpage/section4-notice.jpg"
          />
          <h2 class="first-word">개인정보 처리방침</h2>
        </div>
        <div class="second">
          <a href='https://www.youtube.com/channel/UCee1MvXr6E8qC_d2WEYTU5g' target="_blank" style="text-decoration: none; color: black;">
          <img 
            class="img1"
            src="../assets/image/mainpage/section4-dog.jpg" />
          <h2 class="second-word">강아지 정보</h2>
          </a>
        </div>
        <div class="third" @click="moveToAdoptionList">
          <img class="img3" src="../assets/image/mainpage/section4-adopt.jpg" />
          <h2 class="third-word">입양 신청 목록</h2>
        </div>
      </div>
    </div>
    <div class="footer" style="display: flex; justify-content: space-around">
      <div class="footer-first" style="margin-top: 20px">
        <h2 style="margin-bottom: 5px">CONTACT</h2>
        <br />
        <p><i class="fas fa-envelope fa-sm" style="margin-right: 5px"></i>MYLITTLE@DANG.COM</p>
        <p><i class="fas fa-phone-alt fa-sm" style="margin-right: 5px"></i>010-1234-5678</p>
      </div>
      <div class="footer-second" style="margin-top: 20px">
        <h2 style="margin-bottom: 5px">오시는길</h2>

        <br />
        <p>대전시 유성구 동서대로</p>
        <p>5번길 19-1 (구암동)</p>
      </div>
      <div class="footer-third" style="margin-top: 20px">
        <h2 style="margin-bottom: 5px">영업시간</h2>
        <br />
        <p>평일 09:00 ~ 18:00</p>
        <br />
        <br />
      </div>
      <div class="footer-fourth" style="margin-top: 20px">
        <h2 style="margin-bottom: 5px">개발자</h2>
        <br />
        <p>유혜린 박경수</p>
        <p>정현희 고소영</p>
        <p>김승환 최규식</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Section4",
  methods: {
    moveToPrivacyPolicy() {
      this.$router.push("/privacypolicy");
    },

    moveToAdoptionList() {
      if (this.$cookies.get("accessToken")) {
        this.$router.push("/adoptionlist");
      } else {
        alert("로그인해주세요");
      }
    },
  },
};
</script>

<style scoped>
.section.s3 {
  background: #ff9900;
  padding-top: 75px;
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

div.first:hover,
div.second:hover,
div.third:hover {
  text-decoration: None;
  padding: 3px;
}

.first-word,
.second-word,
.third-word {
  text-align: center;
  color: white;
}

div.footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 30vh;
  background: white;
  margin: 0;
}

div.footer-first {
  box-sizing: border-box;
}

div.footer-second {
  box-sizing: border-box;
}

div.footer-third {
  box-sizing: border-box;
}

div.footer-fourth {
  box-sizing: border-box;
}

div.footer-first,
div.footer-second,
div.footer-third,
div.footer-fourth {
  text-align: center;
}
</style>