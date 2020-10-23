<template>
  <div>
    <full-page ref="fullpage" :options="options" id="fullpage">

      <Section1 />

      <Section2/>

      <div class="section s2">
        Third section ...
      </div>
      <div class="section s3">
        Fourth section ...
      </div>
    </full-page>
  </div>
</template>

<script>
import Section1 from '../components/Section1.vue'
import Section2 from '../components/Section2.vue'

export default {
  name: 'Mainpage',
  components: {
    Section1,
    Section2,
  },
  data() {
    return {
      options: {
        scrollBar: false,
        navigation: true,
        menu: '#menu',
        anchors: ['page1', 'page2', 'page3', 'page4'],
        sectionsColor: [null, null, '#0798ec', '#41b883']
      },
    }
  },
  methods: {

  }
}
</script>

<style>

</style>