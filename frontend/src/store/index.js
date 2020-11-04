import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import SERVER from '@/api/url'
import VueRouter from 'vue-router'
import router from '@/router'
// import { $cookies } from 'vue/types/umd'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    page: 1,
    userMbti: '',
    dogMbtiArr: ["", "", "", ""],
    dogMbti: "",
    survey: [0, 0, 0, 0, 0, 0, 0, 0, 0],
    findUserId: '',

    // 1: survey to animalList, 2: main to animalList
    // 3: 로그인페이지 회원가입, 4: 로그인페이지 로그인
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
      state.survey[1] > 0 ? state.dogMbtiArr[0] = 'E' : state.dogMbtiArr[0] += 'Q';
      state.survey[2] + state.survey[5] + state.survey[8] > 0 ? state.dogMbtiArr[2] = 'S' : state.dogMbtiArr[2] = 'I';
      state.survey[3] + state.survey[4] + state.survey[6] > 0 ? state.dogMbtiArr[3] = 'W' : state.dogMbtiArr[3] = 'A';
      state.survey[7] > 0 ? state.dogMbtiArr[1] = 'F' : state.dogMbtiArr[1] = 'C';

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

      console.log('설문조사 결과:', state.userMbti, state.dogMbti)
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
          console.log('userMbit & dogMbti = ', res.data)
          state.eventListener = 1
          router.push({ name: 'Animals' })
        })
        .catch(err => {
          console.log('MBTI XXX', err)
          SERVER.RefreshToken(err);
        })
    },

  },
  modules: {
  }
})