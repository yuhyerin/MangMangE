import Vue from 'vue'
import Vuex, { Store } from 'vuex'
import axios from 'axios'
import SERVER from '@/api/url'
import VueRouter from 'vue-router'
import router from '@/router'
import createPersistedState from "vuex-persistedstate";

// import { $cookies } from 'vue/types/umd'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [createPersistedState()],

  state: {
    page: 1,
    userMbti: '',
    dogMbtiArr: ["", "", "", ""],
    dogMbti: "",
    survey: [0, 0, 0, 0, 0, 0, 0, 0, 0],
    findUserId: '',
    userSurveyCheck: false,
    videoID: 0,
    videos: [],
    videos_cnt: 0,
    liveOnAir: false,

    // 1: survey to animalList, 2: main to animalList
    // 3: 로그인페이지 회원가입, 4: 로그인페이지 로그인
    // 5: videoDetail to videoSeeMore
    // 6: adoption Create 
    // 7: adoption etc.
    eventListener: 0,
  },

  // state를 (가공해서) 가져올 함수들 === computed
  getters: {

  },

  // state 변경하는 함수들, 동기적으로 동작, commit을 통해 실행
  mutations: {
    selectedUserMbti(state, userMbti) {
      state.userMbti = userMbti
      state.survey[0] = 1
      state.page = state.survey.findIndex((idx) => idx === 0) + 1
    },

    selectedDogMbti(state, payload) {
      state.survey[payload.idx] = payload.answer
      state.page = state.survey.findIndex((idx) => idx === 0) + 1
    },

    whatIsDogMbti(state, payload) {
      state.survey[payload.idx] = payload.answer
      state.survey[1] > 0 ? state.dogMbtiArr[0] = 'E' : state.dogMbtiArr[0] = 'Q';
      state.survey[2] + state.survey[5] + state.survey[8] > 0 ? state.dogMbtiArr[2] = 'S' : state.dogMbtiArr[2] = 'I';
      state.survey[3] + state.survey[4] + state.survey[6] > 0 ? state.dogMbtiArr[3] = 'W' : state.dogMbtiArr[3] = 'A';
      state.survey[7] > 0 ? state.dogMbtiArr[1] = 'F' : state.dogMbtiArr[1] = 'C';

    },

    resetSurvey(state, payload) {
      state.survey = [0, 0, 0, 0, 0, 0, 0, 0, 0]
      state.userMbti = ""
      state.page = 1
    },

    goPage(state, pageNum) {
      if (state.survey[pageNum - 1] !== 0 || (pageNum - 1) == state.survey.findIndex((idx) => idx === 0)) {
        state.page = pageNum
      }
    },

    setEventListener(state, payload) {
      state.eventListener = payload
    },

    setFindUserId(state, payload) {
      state.findUserId = payload
    },

    setUserSurveyCheck(state, payload) {
      state.userSurveyCheck = payload
    },

    setLiveOnAir(state, payload) {
      console.log("onAir!!", payload)
      state.liveOnAir = payload
    }
  },

  // mutations에서 정의한 함수를 actions에서 실행 가능, 비동기 로직, dispatch
  actions: {
    submitSurvey({ state, commit }, payload) {
      commit('whatIsDogMbti', payload)

      state.dogMbti = ""
      for (let i = 0; i < state.dogMbtiArr.length; i++) {
        state.dogMbti += state.dogMbtiArr[i];
      }

      SERVER.tokenCheck(() => {
        axios
          .post(SERVER.URL + SERVER.ROUTES.submitSurvey,
            {
              "mbti": state.userMbti,
              "answer": state.dogMbti,
            },
            {
              headers: {
                Authorization: $cookies.get("accessToken")
              }
            }
          )
          .then(res => {
            state.eventListener = 1
            commit('setUserSurveyCheck', true)
            router.push({ name: 'Animals' })
          })
          .catch(err => {
            SERVER.RefreshToken(err);
          })
      })
    },
    getAllVideos(state) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getAllVideos)
        .then(res => {
          state.videos = [...res.data.VideoList, ...res.data.VideoList]
          state.videos_cnt = state.videos.length
        })
    },
    moveToVideoDetail(state, idx) {
      state.videoID = idx
      router.push("/video" + `/${idx}`)
    },
  },
  modules: {
  }
})