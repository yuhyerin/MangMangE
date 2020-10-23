<template>
  <div class="section s0">
    <h1 class="box-1"></h1>
    <p class="slogan">당신에게 맞는 반려동물을 찾아보세요</p>
    <span>
      <p class="match-box">매칭하기</p>
      <p class="all-animal">모든동물보기</p>
    </span>
    <p class="howitworks">how it works</p>
  </div>
</template>

<script>
export default {
  name: 'Section1'
}
</script>

<style>
  .s0 {
    background-image: url('../assets/image/mainpage/section1.jpg');
    background-size: cover;
    position: relative;
  }

  .slogan {
    font-size: large;
    position: absolute;
    top: 40%;
    left: 58%;
  }

  .box-1 {
    background-color: rgb(227, 227, 227);
    height: 35%;
    width: 25%;
    float: right;
    position: absolute;
    top: 30%;
    left: 55%;
  }

  .match-box {
    background-color: black;
    height: 10%;
    width: 10%;
    float: right;
    position: absolute;
    top: 50%;
    left: 57%;
    color: white;
    text-align: center;
    padding: 26px;
    cursor: pointer;
    font-size: medium;
  }

  .match-box:hover {
    background-color: darkgray;
  }

  .all-animal {
    background-color: rgb(1,118,72);
    height: 10%;
    width: 10%;
    float: right;
    position: absolute;
    top: 50%;
    left: 68%;
    color: white;
    text-align: center;
    padding: 26px;
    cursor: pointer;
    font-size: medium;
  }

  .all-animal:hover {
    background-color: lightgreen;
  }

  .howitworks {
    font-size: large;
    position: absolute;
    top: 61%;
    left: 64%;
    color: black;
    float: right;
    cursor: pointer;
    text-decoration: underline;
  }
  
  .howitworks:hover {
    background-color: gray;
  }
</style>