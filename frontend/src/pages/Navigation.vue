<template>
  <div class="navigation">
    <div class="row" style="height: 100%; padding: 0;">
      <div class="nav-left col-lg-6 col-md-6 col-sm-6">
        <h1><img class="footprint" src="@/assets/image/nav/footprint.png">댕줍냥줍</h1>
      </div>
      <div class="nav-right col-lg-6 col-md-6 col-sm-6">
        <div class="right-upper">
          <span>회원가입</span>
          <span>로그인/로그아웃</span>
        </div>
        <div class="right-lower">
          <span>동물목록</span>
          <span>동영상게시판</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Navigation'
}
</script>

<style>
 img.footprint {
   height: 60px;
   width: 60px;
 }

 div.navigation {
   height: 10%;
   position: fixed;
   top: 0;
   width: 100%;
   background: white;
   z-index: 1;
 }

  div.right-upper {
    text-align: right;
  }

  div.right-lower {
    text-align: center;
  }
</style>