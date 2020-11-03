import axios from 'axios'

export default {
  URL: 'http://localhost:8084',
  ROUTES: {
    submitSurvey: '/user/survey/create',
    // updateSurvey: '/survey/update',
  },

  // Token 만료시 갱신 함수 -> SERVER.RefreshToken(err) 로 사용 가능
  RefreshToken: function (err, afterFunction) {
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
            afterFunction()
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

}