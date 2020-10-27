<template>
  <!-- <v-app>
    <v-app-bar
      app
      color="white"
      dark
    >
      <v-app-bar-nav-icon><img class="footprint" src="@/assets/image/nav/footprint.png"></v-app-bar-nav-icon>
      <v-toolbar-title style="color: black; font-weight: bold; font-size: xx-large;">댕줍멍줍</v-toolbar-title>
      <v-spacer></v-spacer>
      <template v-slot:extension>
        <v-tabs>
          <v-tab style="color: black; margin-left: 80%;">동물목록</v-tab>
          <v-tab style="color: black;">동영상게시판</v-tab>
        </v-tabs>
      </template>
      <v-btn>회원가입</v-btn>
      <v-btn>로그인/로그아웃</v-btn>

    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app> -->

  <div id="app">
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
    <router-view />
  </div>
</template>

<script>

export default {
  name: 'App',

  components: {
  },

  data: () => ({
    //
  }),
};
</script>

<style scoped>
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

</style>
