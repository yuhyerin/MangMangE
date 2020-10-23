<template>
  <div class="section s1">
    <div class="container s2">
      <div class="row r1">
        <div class="col-md-12 col-sm-12">
          <img class="section2-dog" src="../assets/image/mainpage/section2-dog.png">
          <h1 class="howitworks2">how it works</h1>
          <hr class="howitworks2-hr">
        </div>
      <div class="row r2">
        <div class="col-lg-4 col-md-4 col-sm-4" style="box-shadow: 1px #ddd;">
          <h2 class="left">MBTI & 설문</h2>
          <hr class="hr-left">
          <p>I saw the best minds of my generation destroyed by madness, starving hysterical naked,
dragging themselves through the negro streets at dawn looking for an angry fix,
angelheaded hipsters burning for the ancient heavenly connection to the starry dynamo in the machinery of night,</p>
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4">
          <h2 class="mid">이상적인 반려동물</h2>
          <hr class="hr-mid">
        </div>
        <div class="col-lg-4 col-md-4 col-sm-4">
          <h2 class="right">반려동물 추천리스트</h2>
          <hr class="hr-right">
        </div>
      </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Section2'
}
</script>

<style>
  .s1 {
    background-color: white;
  }

  .container.s2 {
    height: 754px;
    border-radius: 50px;
    background-color: rgb(244, 236, 225);
  }

  .r1 {
    margin-top: 150px;
    height: 300px;
  }

  .r2 {
    margin-top: 30px;
    height: 300px;
  }

  img.section2-dog {
    width: 30px;
    height: 30px;
    margin-right: auto;
    margin-left: auto;
    display: block;
  }

  h1.howitworks2 {
    text-align: center;
  }

  h2.left, h2.mid, h2.right {
    text-align: center;
  }

  hr.howitworks2-hr, hr.hr-left, hr.hr-mid, hr.hr-right {
    border-color: #ccc;
    border-top: 1px;
    margin: 0px auto;
    width: 230px;
  }

  .col-lg-4.col-md-4.col-sm-4 {
    transition: box-shadow .3s;
  }

  .col-lg-4.col-md-4.col-sm-4:hover {
    box-shadow: 0 0 10px rgba(33,33,33,.2);
  }
</style>