<template>
  <div class="section s1">
    <div class="container s2">
      <img
        class="section2-dog"
        src="../assets/image/mainpage/section2-dog.png"
      />
      <div style="height: 15vh">
        <h1 class="howitworks-h1" style="color: white">
          <strong> HOW IT WORKS? </strong>
        </h1>
      </div>

      <div
        style="
          display: flex;
          justify-content: space-around;
          align-items: center;
        "
      >
        <div class="section2-left">
          <img
            class="section2-mbti"
            src="../assets/image/mainpage/section2-mbti.png"
          />
          <h1 class="left" style="color: white"><strong>나의 MBTI</strong></h1>
          <br />
          <div style="color: white; line-height: 180%">
            <strong>
              <p>원하는 동물을 찾아보기 이전에 먼저</p>
              <p>MBTI를 통해 자신의 성격을 이해해요</p>
              <p>자신을 잘 아는게 자신에게 맞는</p>
              <p>강아지를 찾는데 큰 도움이 돼요</p>
            </strong>
          </div>
        </div>

        <div class="section2-middle">
          <img
            class="section2-survey"
            src="../assets/image/mainpage/section2-survey.png"
          />
          <h1 class="left"><strong>설문</strong></h1>
          <br />
          <div style="color: white; line-height: 180%">
            <strong>
              <p>설문을 통해 자신에게 어떤 성격의</p>
              <p>강아지가 맞는지 파악해봐요</p>
              <p>성격이 맞는 강아지를 입양해서</p>
              <p>파양률을 낮출 수 있어요</p>
            </strong>
          </div>
        </div>

        <div class="section2-right">
          <img
            class="section2-recommendation"
            src="../assets/image/mainpage/section2-recommendation.png"
          />
          <h1 class="right"><strong>추천리스트</strong></h1>
          <br />
          <div style="color: white; line-height: 180%">
            <strong>
              <p>MBTI와 설문을 거쳐서 자신의 성향에</p>
              <p>맞는 강아지를 추천 받아보세요</p>
              <p>자신과 가장 잘 맞을 강아지를</p>
              <p>입양신청해서 가족으로 맞이하세요</p>
            </strong>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Section2",
};
</script>

<style scoped>
.s1 {
  background: #ff9900;
  padding-top: 90px;
}

.container.s2 {
  height: 85vh;
  border-radius: 20px;
  text-align: center;
  margin-top: 50px;
}

.r1 {
  margin-top: 50px;
  height: 300px;
}

.r2 {
  margin-top: 70px;
  height: 300px;
}

img.section2-dog {
  width: 60px;
  height: 60px;
  margin-right: auto;
  margin-left: auto;
  display: block;
}

img.section2-mbti {
  width: 50px;
  height: 50px;
  margin-right: auto;
  margin-left: auto;
  display: block;
}

img.section2-survey,
img.section2-recommendation {
  width: 40px;
  height: 40px;
  margin-right: auto;
  margin-left: auto;
  display: block;
}

img.section2-survey,
img.section2-adoption,
img.section2-recommendation {
  margin-bottom: 10px;
}

h1.left,
h1.mid,
h1.right {
  text-align: center;
  color: white;
}

div.section2-left,
div.section2-middle,
div.section2-right {
  padding: 20px;
  transition: box-shadow 0.3s;
}

div.section2-left:hover,
div.section2-middle:hover,
div.section2-right:hover {
  box-shadow: 0 0 50px rgba(33, 33, 33, 0.2);
}

p.left-content,
p.right-content,
p.mid-content {
  text-align: center;
}
</style>