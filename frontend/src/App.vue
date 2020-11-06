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
</style>

