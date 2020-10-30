import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import SERVER from "@/api/url";
import VueRouter from "vue-router";
import router from "@/router";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    page: 1,
    // authToken: ,
    userMbti: "",
    dogMbti: "",
    survey: [0, 0, 0, 0, 0, 0, 0, 0, 0],
  },

  // state를 (가공해서) 가져올 함수들 === computed
  getters: {
    // config: state => ({ headers: {Auturization: }})
  },

  // state 변경하는 함수들, 동기적으로 동작, commit을 통해 실행
  mutations: {
    selectedUserMbti(state, userMbti) {
      state.userMbti = userMbti;
      state.survey[0] = 1;
      state.page = state.survey.findIndex((idx) => idx === 0) + 1;
    },
    // survey 마지막 0 바뀌면 자동 제출로 수정
    selectedDogMbti(state, payload) {
      state.survey[payload.idx] = payload.answer;
      state.page = state.survey.findIndex((idx) => idx === 0) + 1;
    },

    whatIsDogMbti(state, payload) {
      state.survey[payload.idx] = payload.answer;
      state.survey[1] > 0 ? (state.dogMbti += "E") : (state.dogMbti += "Q");
      state.survey[2] + state.survey[5] + state.survey[8] > 0
        ? (state.dogMbti += "S")
        : (state.dogMbti += "I");
      state.survey[3] + state.survey[4] + state.survey[6] > 0
        ? (state.dogMbti += "W")
        : (state.dogMbti += "A");
      state.survey[7] > 0 ? (state.dogMbti += "F") : (state.dogMbti += "C");
    },

    goPage(state, pageNum) {
      if (
        state.survey[pageNum - 1] !== 0 ||
        pageNum - 1 == state.survey.findIndex((idx) => idx === 0)
      ) {
        state.page = pageNum;
      }
    },
  },

  // mutations에서 정의한 함수를 actions에서 실행 가능, 비동기 로직, dispatch
  actions: {
    submitSurvey({ state, commit }, payload) {
      commit("whatIsDogMbti", payload);
      console.log(state.userMbti, state.dogMbti);
      // axios.post(SERVER.URL + SERVER.submitSurvey, {
      //   "MBTI": state.userMbti,
      //   "answers": state.dogMbti,
      // },
      // {
      //   headers: {
      //     "jwt-auth-token": localStorage.getItem("token"),
      //   }
      // })
    },
  },
  modules: {},
});
