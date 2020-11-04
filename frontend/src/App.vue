<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",

  components: {},

  data: () => ({
    //
  }),
};
</script>

<!--<style scoped>
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
</style>-->
<style>
/* @import "./assets/css/minireset.min.css"; */
@import "./assets/css/fonts.css";

#app {
  font-family: "hoonwhitecatr";
}

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

