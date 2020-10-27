import Vue from 'vue'
import VueRouter from 'vue-router'
import Mainpage from '../pages/Mainpage.vue'
import Adoption from '../pages/Adoption.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/mainpage',
    name: 'Mainpage',
    component: Mainpage
  },
  {
    path: '/adoption',
    name: 'Adoption',
    component: Adoption
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
