<template>
  <v-btn
      class="mx-2"
      fab
      dark
      small
      color="rgb(1, 118, 72)"
      @click="scrollToTop"
      style="position: fixed; bottom: 70px; right: 55px"
    >
      <v-icon dark>mdi-chevron-up</v-icon>
    </v-btn>
</template>

<script>
export default {
  methods: {
    scrollToTop() {
      scroll(0, 0);
    },
  }
}
</script>

<style>

</style>