import Vue from 'vue'
import VueRouter from 'vue-router'
import Adoption from '../pages/Adoption.vue'
import Login from '../pages/Login.vue'
import AnimalList from '../pages/AnimalList.vue'
import AnimalDetail from '../pages/AnimalDetail.vue'
import Mainpage from '../pages/Mainpage.vue'
import Survey from '../pages/Survey.vue'
import AdoptionList from '../pages/AdoptionList.vue'
import VideoBoard from '../pages/VideoBoard.vue'
import AdoptionUpdate from '../pages/AdoptionUpdate.vue'
import Test from "../pages/Test.vue"
import VideoDetail from '../pages/VideoDetail.vue'
import UploadVideo from '../pages/UploadVideo.vue'
import PrivacyPolicy from '../pages/PrivacyPolicy.vue'
import Live from '../pages/Live.vue'
import VideoDetailPopup from '../pages/VideoDetailPopup'
import axios from 'axios'
import SERVER from '@/api/url'
import LiveChat from '../pages/LiveChat.vue'
import ViewerChat from '../pages/ViewerChat.vue'
import StreamingDetail from '../pages/StreamingDetail.vue'


const requireAuth = (to, from, next) => {
  if ($cookies.get('accessToken') != null) {
    next();
  } else {
    alert("로그인이 필요합니다.");
    next("/");
  }
};

const noRequireAuth = (to, from, next) => {
  if ($cookies.get('accessToken') == null) {
    next();
  } else {
    alert("회원으로는 접근할 수 없습니다.");
    next("/");
  }
};

const adminAuth = (to, from, next) => {
  if ($cookies.get('refreshToken') != null) {
    SERVER.tokenCheck(() => {
      axios.get(SERVER.URL + "/user/userId", {
        headers: {
          Authorization: $cookies.get('accessToken')
        }
      }).then((res) => {
        if (res.data.success) {
          next();
        } else {
          alert("접근 권한이 없습니다.");
          next('/')
        }
      }).catch((err) => {
        alert("잘못된 접근입니다.");
        next('/')
      })
    })
  } else {
    alert("접근 권한이 없습니다.");
    next('/')
  }
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    beforeEnter: noRequireAuth,
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
    path: '/adoptionupdate/:uid',
    name: 'AdoptionUpdate',
    beforeEnter: requireAuth,
    component: AdoptionUpdate,
  },
  {
    path: '/test',
    // beforeEnter: adminAuth,
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
    beforeEnter: adminAuth,
    component: UploadVideo
  },
  {
    path: '/privacypolicy',
    name: 'PrivacyPolicy',
    component: PrivacyPolicy
  },
  {
    path: '/live',
    name: 'Live',
    // beforeEnter: adminAuth,
    component: Live
  },
  {
    path: '/videos/0',
    name: 'VideoDetailPopup',
    component: VideoDetailPopup,
    props: (route) => ({ query: route.query.q })
  },
  {
    path: '/livechat',
    name: 'LiveChat',
    component: LiveChat
  },
  {
    path:'/viewerchat',
    name:'ViewerChat',
    component:ViewerChat
  },
  {
    path: '/streaming',
    name: 'StreamingDetail',
    component: StreamingDetail
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router