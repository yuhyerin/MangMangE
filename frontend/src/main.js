import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueFullPage from 'vue-fullpage.js'
import vuetify from './plugins/vuetify';
import VueCookies from "vue-cookies";
import VuePlyr from 'vue-plyr'
import vue2Dropzone from 'vue2-dropzone'

Vue.use(vue2Dropzone)
Vue.use(VuePlyr, {
  plyr: {
    fullscreen: { enabled: true }
  }
})
Vue.use(VueCookies);
Vue.use(VueFullPage);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
