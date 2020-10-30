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
            <v-btn color="rgb(1,118,72)" @click="moveTo('/survey')"
              ><div style="color: white">매칭하기</div></v-btn
            >
            <v-btn color="rgba(64,33,22)" @click="moveTo('/animals')"
              ><div style="color: white">모든 동물 보기</div></v-btn
            >
          </div>
          <div>
            <a href="#page2"> How it works? </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Section1",
  methods: {
    moveTo(page) {
      this.$router.push(page);
    },
  },
};
</script>

<style>
.s0 {
  /* background-image: url("../assets/image/mainpage/section1.jpg"); */
  background: linear-gradient(rgba(0, 0, 0, 0.15), rgba(0, 0, 0, 0.15)),
    url("../assets/image/mainpage/section1.jpg");
  background-size: cover;
  max-width: 100vw;
  padding-top: 75px;
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