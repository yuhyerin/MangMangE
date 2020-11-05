<template>
  <div
    style="height: 60vh; width: 27vw; background-color: white; margin: 20px"
    :style="{
      transform: 'rotate(' + this.degree + 'deg)',
    }"
  >
    <div
      style="
        height: 70%;
        margin: 5%;
        background-color: black;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
      "
    >
      <img :src="animalInfo.popfile" alt="이미지" style="width: 100%" />
    </div>
    <div></div>
  </div>
</template>

<script>
export default {
  props: ["animalInfo"],
  data() {
    return {
      degree: "",
    };
  },
  created() {
    this.degree = Math.floor(Math.random() * 7) - 3;
  },
};
</script>

<style>
</style>