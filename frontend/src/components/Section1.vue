<template>
  <div class="section s0">
    <div style="display: flex">
      <div style="width: 50vw; height: 100vh"></div>
      <div
        style="
          width: 50vw;
          display: flex;
          justify-content: center;
          align-items: center;
        "
      >
        <div class="mainWindow">
          <div style="height: 40%">
            지금 당신에게 맞는 반려동물을 찾아보세요
          </div>
          <div
            style="display: flex; width: 100%; justify-content: space-around"
          >
            <v-btn color="rgb(1,118,72)"
              ><div style="color: white">매칭하기</div></v-btn
            >
            <v-btn color="rgba(64,33,22)"
              ><div style="color: white">모든 동물 보기</div></v-btn
            >
          </div>
          <div>
            <a href="#page2"> How it works? </a>
          </div>
        </div>
      </div>
    </div>
    <!-- <div class="row">
        <div class="col-md-7 col-sm-7" style="margin-top: 100px"></div>
        <div
          class="col-md-4 col-sm-4"
          style="
            margin-top: 200px;
            background-color: rgb(227, 227, 227);
            height: 250px;
          "
        >
          <h1 class="slogan">당신에게 맞는 반려동물을 찾아보세요</h1>
          <div>
            <p class="match-box">매칭하기</p>
            <p class="all-animal">모든동물보기</p>
          </div>
          <div class="howitworks">
            <a class="moveToSection2" href="#page2">How it works</a>
          </div>
        </div>
        <div class="col-md-1 col-sm-1" style="margin-top: 100px"></div>
      </div> -->
  </div>
</template>

<script>
export default {
  name: "Section1",
};
</script>

<style>
.s0 {
  /* background-image: url("../assets/image/mainpage/section1.jpg"); */
  background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),
    url("../assets/image/mainpage/section1.jpg");
  background-size: cover;
  max-width: 100vw;
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
  background-color: rgb(1, 118, 72);
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

.mainWindow {
  width: 60%;
  height: 50%;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}
</style>