import Vue from 'vue'
import VueRouter from 'vue-router'
import Adoption from '../pages/Adoption.vue'
import Login from '../pages/Login.vue'
import AnimalList from '../pages/AnimalList.vue'
import AnimalDetail from '../pages/AnimalDetail.vue'
import Mainpage from '../pages/Mainpage.vue'
import Survey from '../pages/Survey.vue'
import AdoptionList from '../pages/AdoptionList.vue'
// import VideoList from '../pages/VideoList.vue'
// import UploadVideo from '../pages/UploadVideo.vue'
import Streaming from '../pages/StreamingHR.vue'
import StreamingTest from '../pages/StreamingTest.vue'
import StreamingFinal from '../pages/StreamingFinal.vue'
import VideoBoard from '../pages/VideoBoard.vue'
import AdoptionReview from '../pages/AdoptionReview.vue'
import AdoptionUpdate from '../pages/AdoptionUpdate.vue'
import Test from "../pages/Test.vue"
import VideoDetail from '../pages/VideoDetail.vue'
import PrivacyPolicy from '../pages/PrivacyPolicy.vue'
import Live from '../pages/Live.vue'

Vue.use(VueRouter)

const routes = [
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
    path: '/adoption/:animalID',
    name: 'Adoption',
    component: Adoption
  },
  {
    path: '/survey',
    name: 'Survey',
    component: Survey
  },
  {
    path: '/adoptionlist',
    name: 'AdoptionList',
    component: AdoptionList,
  },
  {
    path: '/videos',
    name: 'VideoBoard',
    component: VideoBoard,
  },
  {
    path: '/adoptionreview',
    name: 'AdoptionReview',
    component: AdoptionReview,
  },
  {
    path: '/streaming',
    name: 'Streaming',
    component: Streaming
  },
  {
    path: '/streamingTest',
    name: 'StreamingTest',
    component: StreamingTest
  },
  {
    path: '/streamingFinal',
    name: 'StreamingFinal',
    component: StreamingFinal
  },
  {
    path: '/adoptionupdate/:uid',
    name: 'AdoptionUpdate',
    component: AdoptionUpdate,
  },
  {
    path: '/test',
    component: Test,
  },
  {
    path: '/video/:videoID',
    name: 'VideoDetail',
    component: VideoDetail,
  },
  {
    path: '/privacypolicy',
    name: 'PrivacyPolicy',
    component: PrivacyPolicy
  },
  {
    path: '/live',
    name: 'Live',
    component: Live
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
