<template>
  <div class="search-bar">
    <input @keypress.enter="onInput" />
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  methods: {
    // 모든 이벤트리스너의 콜백함수는 event 객체가 같이 넘어옴
    onInput(event) {
      this.$emit("input-change", event.target.value);
    },
  },
};
</script>

<style>
/* vue-loader를 참고해보면 속도 이슈로 scoped 보다는 class나 id를 지정해주는 것을 추천한다. */
div.search-bar {
  text-align: center;
}

div.search-bar > input {
  width: 50%;
  border: 1px solid black;
}
</style>