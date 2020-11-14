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
import StreamingTest from '../pages/StreamingTest.vue'
import StreamingViewer from '../pages/StreamingViewer.vue'
import VideoBoard from '../pages/VideoBoard.vue'
import AdoptionReview from '../pages/AdoptionReview.vue'
import AdoptionUpdate from '../pages/AdoptionUpdate.vue'
import Test from "../pages/Test.vue"
import VideoDetail from '../pages/VideoDetail.vue'
import UploadVideo from '../pages/UploadVideo.vue'
import PrivacyPolicy from '../pages/PrivacyPolicy.vue'
// import UploadTest from '../pages/UploadTest.vue'
import Live from '../pages/Live.vue'
import VideoDetailPopup from '../pages/VideoDetailPopup'

const requireAuth = (to, from, next) => {
  if ($cookies.get('accessToken') != null) {
    next();
  } else {
    alert("로그인이 필요합니다.");
    next("/");
  }
};

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
    path: '/adoptionlist',
    name: 'AdoptionList',
    beforeEnter: requireAuth,
    component: AdoptionList,
  },
  {
    path: '/adoption/:animalID',
    name: 'Adoption',
    beforeEnter: requireAuth,
    component: Adoption
  },
  {
    path: '/adoptionupdate/:uid',
    name: 'AdoptionUpdate',
    component: AdoptionUpdate,
  },
  {
    path: '/adoptionreview',
    name: 'AdoptionReview',
    beforeEnter: requireAuth,
    component: AdoptionReview,
  },
  {
    path: '/survey',
    name: 'Survey',
    beforeEnter: requireAuth,
    component: Survey
  },
  {
    path: '/videos',
    name: 'VideoBoard',
    component: VideoBoard,
  },

  {
    path: '/streamingTest',
    name: 'StreamingTest',
    component: StreamingTest
  },
  {
    path: '/viewer',
    name: 'StreamingViewer',
    component: StreamingViewer
  },

  {
    path: '/test',
    component: Test,
  },
  {
    path: '/video/:videoId',
    name: 'VideoDetail',
    component: VideoDetail,
  },
  {
    path: '/videos/upload',
    name: 'UploadVideo',
    beforeEnter: requireAuth,
    component: UploadVideo
  },
  {
    path: '/privacypolicy',
    name: 'PrivacyPolicy',
    component: PrivacyPolicy
  },
  // {
  //   path : '/video/upload',
  //   name:'UploadVideo',
  //   component: UploadTest
  // },
  {
    path: '/live',
    name: 'Live',
    component: Live
  },
  {
    path: '/videos/0',
    name: 'VideoDetailPopup',
    component: VideoDetailPopup,
    props: (route) => ({ query: route.query.q })
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
