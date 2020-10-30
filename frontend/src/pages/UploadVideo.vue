<template>
  <div class="container">
    <v-app id="inspire">
    <div>
      <v-file-input
        chips
        multiple
        label="File input w/ chips"
      ></v-file-input>
      <v-btn @click="upload">업로드</v-btn>
    </div>
  </v-app>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>