import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../pages/Login.vue'
import AnimalList from '../pages/AnimalList.vue'
import AnimalDetail from '../pages/AnimalDetail.vue'
import Mainpage from '../pages/Mainpage.vue'
import Survey from '../pages/Survey.vue'
import Test from '../pages/Test.vue'

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/animals',
    name: 'Animals',
    component: AnimalList
  },
  {
    path: '/animalDetail/:animalID',
    name: 'AnimalDetail',
    component: AnimalDetail
  },
  {
    path: '/',
    name: 'Mainpage',
    component: Mainpage
  },
  {
    path: '/survey',
    name: 'Survey',
    component: Survey
  },
  {
    path: '/test',
    name: 'Test',
    component: Test
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
