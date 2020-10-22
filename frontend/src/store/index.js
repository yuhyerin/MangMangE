import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import SERVER from '@/api/url'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    mbti: '',
    answers: '',
  },

  // state를 (가공해서) 가져올 함수들
  getters: {
    // config: state => ({headers: {Aut}})
  },

  // state 변경하는 함수들
  // commit을 통해 실행
  mutations: {
    submitSurvey(state, mbti) {
      console.log("mbti is", state)
      state.mbti = mbti
    }
  },

  // mutations에서 정희한 함수를 actions에서 실행 가능
  // dispatch를 통해 실행
  actions: {
    // survey 결과 전송
    // submitSurvey(context, mbti) {
    //   context.commmit('SET')
    //   console.log(mbti)
    //   axios.post(SERVER.URL + SERVER.ROUTES.submitSurvey, mbti, )
    // }
  },
  modules: {
  }
})
