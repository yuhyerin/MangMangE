import axios from 'axios'

export default {
  URL: 'http://localhost:8084',
  ROUTES: {
    submitSurvey: '/user/survey/create',
    // updateSurvey: '/survey/update',
  },

  EXPIRETIME: 600,

  // setTimer: function (value) {
  //   console.log("call timer")
  //   console.log("timer payload", value)
  //   if (value > 0) {
  //     console.log(value)
  //     setTimeout(() => {
  //       value -= 1;
  //       this.setTimer(value)
  //     }, 1000);
  //   }
  // }
  // ,
  // Token 만료시 갱신 함수 -> SERVER.RefreshToken(err) 로 사용 가능
  RefreshToken: function (err) {
    if (err.response.status == 401) {
      //accessToken만료
      axios
        .post(
          this.URL + "/newuser/refresh",
          {},
          {
            headers: {
              accessToken: $cookies.get("accessToken"),
              refreshToken: $cookies.get("refreshToken"),
            },
          }
        )
        .then((res) => {
          console.log(res);
          if (res.data.success) {
            $cookies.set("accessToken", res.data.accessToken);
            alert("세션이 만료되어 메인페이지로 이동합니다.");
            location.href = "/"
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

}