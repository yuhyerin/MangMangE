import axios from 'axios'

export default {
  URL: 'https://localhost:8080',
  // URL: 'https://k3b306.p.ssafy.io:8080',
  KakaopayURL: 'https://kapi.kakao.com/v1/payment/ready',
  ROUTES: {
    submitSurvey: '/user/survey/create',
    getAllVideos: '/newuser/video/allvideo',
    getVideo: '/newuser/video/detailvideo',
  },


  EXPIRETIME: 600,

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
  },

  tokenCheck: function (toDoFunc) {
    let today = new Date();
    let expireTime =
      $cookies.get("expireTime").substring(11, 13) * 3600 +
      $cookies.get("expireTime").substring(14, 16) * 60 +
      $cookies.get("expireTime").substring(17, 19) * 1;

    let userTime =
      today.getHours() * 3600 +
      today.getMinutes() * 60 +
      today.getSeconds() * 1;

    // if (expireTime > 85800) {
    //   expireTime -= 86400;
    //   userTime -= 86400;
    // }

    if (expireTime <= userTime) {
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
          if (res.data.success) {
            $cookies.set("accessToken", res.data.accessToken);
            $cookies.set("expireTime", res.data.expireTime);
            toDoFunc()
          }
        })
        .catch((err) => {
          alert('권한이 만료되었습니다. 다시 로그인 해주세요.');
          location.href = "/"
          $cookies.remove('refreshToken');
          $cookies.remove('accessToken');
          $cookies.remove('expireTime');
          console.log(err);
        });
    } else {
      toDoFunc()
    }
  }

}