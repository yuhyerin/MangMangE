<template>
  <div
    style="display: flex; width: 47%; height: 25vh; margin: 20px 10px 20px 10px"
  >
    <div
      style="
        width: 40%;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
        background-color: gray;
      "
    >
      <img :src="animalInfo.popfile" alt="이미지" style="width: 100%" />
      <!-- <div
        style="width: 100%%; border: 1px solid black"
        :style="{
          'background-image': 'url(' + animalInfo.popfile + ')',
          'background-size': 'cover',
        }"
      >
        이미지 구간
      </div> -->
    </div>
    <div style="width: 60%; border: 1px solid black">
      <div
        style="
          border: 1px solid black;
          height: 20%;
          display: flex;
          justify-content: center;
          align-items: center;
          width: 100%;
        "
      >
        <div style="width: 85%; height: 100%; border: 1px solid black">
          태그 구간
        </div>
        <div style="width: 15%; height: 100%; border: 1px solid black">
          하뚜
        </div>
      </div>
      <div style="border: 1px solid black; height: 80%">내용구간</div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["animalInfo"],
};
</script>

<style>
</style>