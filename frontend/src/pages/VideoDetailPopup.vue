<template>
  <div style="margin-left: 10px;">
    <h2>
      후원이 완료됐습니다
    </h2>
    <h2>
      잠시 후 창을 자동으로 닫습니다
    </h2>
    <h2>
      감사합니다
    </h2>
  </div>
</template>

<script>
export default {
  name: 'VideoDetailPopup',
  data() {
    return {
      location: "",
    }
  },
  methods: {

  },
  mounted() {
    setTimeout(function() {
      window.close(this.location)
    }, 2000)
  },
  created() {
    var loc = location.href
    this.location = loc
  }
}
</script>

<style>

</style>