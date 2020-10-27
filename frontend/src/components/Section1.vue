<template>
  <div class="section s0">
    <div class="container" style="height: 754px;">
      <div class="row">
        <div class="col-md-7 col-sm-7" style="margin-top: 100px;"></div>
        
        <div class="col-md-4 col-sm-4" style="margin-top: 200px; background-color: rgb(227, 227, 227); height: 250px;">
          <h1 class="slogan">당신에게 맞는 반려동물을 찾아보세요</h1>
          <div>
            <p class="match-box">매칭하기</p>
            <p class="all-animal">모든동물보기</p>
          </div>
          <div class="howitworks"><a class="moveToSection2" href="#page2">How it works</a></div>
        </div>
        <div class="col-md-1 col-sm-1" style="margin-top: 100px;"></div>
      </div>
    </div>
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
    text-align: center;
  }

  .match-box {
    margin-top: 20px;
    background-color: black;
    color: white;
    text-align: center;
    cursor: pointer;
    font-size: medium;
  }

  .match-box:hover {
    background-color: darkgray;
  }

  .all-animal {
    background-color: rgb(1,118,72);
    color: white;
    text-align: center;
    cursor: pointer;
    font-size: medium;
  }

  .all-animal:hover {
    background-color: lightgreen;
  }

  .howitworks {
    font-size: large;
    color: black;
    cursor: pointer;
    text-align: center;
    text-decoration: underline;
  }
  
  .howitworks:hover {
    background-color: gray;
  }

  a.moveToSection2 {
    color: black;
  }

  a.moveToSection2:link {
    color: black;
  }

  a.moveToSection2:visited {
    color: black;
  }
</style>