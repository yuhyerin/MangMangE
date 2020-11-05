<template>
  <div class="section s1">
    <div class="container s2">
      <div style="height: 15vh">How it works?</div>
      <div
        style="
          display: flex;
          justify-content: space-around;
          align-items: center;
        "
      >
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </div>
      <!-- <div class="row r1">
        <div class="col-md-12 col-sm-12">
          <img
            class="section2-dog"
            src="../assets/image/mainpage/section2-dog.png"
          />
          <h1 class="howitworks2"><strong>How it works</strong></h1>
          <br />
          <hr class="howitworks2-hr" />
        </div>
        <div class="row r2">
          <div class="col-lg-4 col-md-4 col-sm-4" style="box-shadow: 1px #ddd">
            <img
              class="section2-survey"
              src="../assets/image/mainpage/section2-survey.png"
            />
            <h2 class="left"><strong>MBTI & 설문</strong></h2>
            <br />
            <hr class="hr-left" />
            <br />
            <p class="left-content">
              I saw the best minds of my generation destroyed by madness,
              starving hysterical naked, dragging themselves through the negro
              streets at dawn looking for an angry fix, angelheaded hipsters
              burning for the ancient heavenly connection to the starry dynamo
              in the machinery of night,
            </p>
          </div>
          <div class="col-lg-4 col-md-4 col-sm-4">
            <img
              class="section2-adoption"
              src="../assets/image/mainpage/section2-adoption.png"
            />
            <h2 class="mid"><strong>이상적인 반려동물</strong></h2>
            <br />
            <hr class="hr-mid" />
            <br />
            <p class="mid-content">
              who poverty and tatters and hollow-eyed and high sat up smoking in
              the supernatural darkness of cold-water flats floating across the
              tops of cities contemplating jazz, who bared their brains to
              Heaven under the El and saw Mohammedan angels staggering on
              tenement roofs illuminated, who passed through universities with
              radiant cool eyes hallucinating Arkansas and Blake-light tragedy
              among the scholars of war,
            </p>
          </div>
          <div class="col-lg-4 col-md-4 col-sm-4">
            <img
              class="section2-recommendation"
              src="../assets/image/mainpage/section2-recommendation.png"
            />
            <h2 class="right"><strong>반려동물 추천리스트</strong></h2>
            <br />
            <hr class="hr-right" />
            <br />
            <p class="right-content">
              who were expelled from the academies for crazy & publishing
              obscene odes on the windows of the skull, who cowered in unshaven
              rooms in underwear, burning their money in wastebaskets and
              listening to the Terror through the wall, who got busted in their
              pubic beards returning through Laredo with a belt of marijuana for
              New York,
            </p>
          </div>
        </div>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "Section2",
};
</script>

<style>
.s1 {
  background-color: white;
  padding-top: 75px;
}

.container.s2 {
  height: 85vh;
  border-radius: 20px;
  /* background-color: rgb(244, 236, 225); */
  text-align: center;
  margin-top: 20px;
}

.r1 {
  margin-top: 50px;
  height: 300px;
}

.r2 {
  margin-top: 70px;
  height: 300px;
}

img.section2-dog,
img.section2-survey,
img.section2-adoption,
img.section2-recommendation {
  width: 30px;
  height: 30px;
  margin-right: auto;
  margin-left: auto;
  display: block;
}

img.section2-survey,
img.section2-adoption,
img.section2-recommendation {
  margin-bottom: 10px;
}

h1.howitworks2 {
  text-align: center;
}

h2.left,
h2.mid,
h2.right {
  text-align: center;
}

hr.howitworks2-hr,
hr.hr-left,
hr.hr-mid,
hr.hr-right {
  border-top: 1px solid #ccc;
  margin: 0px auto;
  width: 230px;
}

.col-lg-4.col-md-4.col-sm-4 {
  transition: box-shadow 0.3s;
}

.col-lg-4.col-md-4.col-sm-4:hover {
  box-shadow: 0 0 10px rgba(33, 33, 33, 0.2);
}

p.left-content,
p.right-content,
p.mid-content {
  text-align: center;
}
</style>