

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1>
        Kakao Login........
      </h1>

      <h2>
        {{msg}}
      </h2>

      <button
        class="btn-bottom">
        Go back
    </button>

    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import axios from 'axios';
import UserApi from '@/api/UserApi.js';

export default {
  methods: {
    loginWithKakaoInfo(info) {
      this.msg = info.email + '/' + info.nickname + '/' + info.profile_pic;

      // Email exist
      if(info.email && info.nickname) {
        // Check if user
        UserApi.requestFindEmail(
          {email: info.email}
          , res => {
            // User exists => Login
            if(res.isEmail==true) {
              this.doLogin(info);
            }
            // User not exists => Join
            else {
              this.doJoin(info);
            }
          }
          , err => {
            this.msg = err.msg;
          }
        );
      }
      // Do not join(or login)
      else {
        // Go to error page(Require email)
        // And go main(login) page, not the back page
        this.msg = "email not exists";
      }
    }
    , doLogin(info) {
      this.msg = "Do Login ...";
      console.log('Do login: ', info.email);
      UserApi.loginWithSocial(
        info.email
        , res => {
          // 로그인 완료 시 세션 저장소에 받은 토큰 정보 저장
          sessionStorage.setItem('jwt-token', res.jwtToken);

          let isAuto = this.$store.state.userInfo.isAutoLogin;
          res.userInfo.isAutoLogin = isAuto;

          // 로그인 정보를 vuex 에 저장
          this.$store.commit('addUserInfo', res.userInfo);

          // Save it also a local storage
          localStorage.setItem('tvility', JSON.stringify(res.userInfo));

          // curation/main 페이지로 이동
          this.$router.push({path:"/curation/main"});
        }
        , error => {
          this.$router.push({name:'Errors', query: {message: error.msg}});
        }
      )
    }
    , doJoin(info) {
      this.msg = "Do Join ...";
      console.log('Do join(info)');
      console.log(info);
      let data = {
        nick_name: info.nickname,
        email: info.email
      };
      console.log('Do join(data)');
      console.log(data);
      UserApi.joinWithSocial(
        data
        , res => {
            this.doLogin(info);
        }
        , error => {
          this.$router.push({name:'Errors', query: {message: error.msg}});
        }
      )
    }
  },
  mounted() {
    let code = this.$route.query.code;

    // Error
    if(!code) {
      this.msg = 'Login fail';
    }
    // Get kakao authorize code
    else {
      this.msg = 'Get Code Success!!';

      const qs = require('qs');

      let accessToken = null;

      const parameter = {
        grant_type: 'authorization_code',
        client_id: '72d5297d8562a4a38d4878b5b111dc9c',
        code: code
      };

      axios.post('https://kauth.kakao.com/oauth/token',
                  qs.stringify(parameter), 
                  {
                    headers: {
                      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                    }
                  }
        )
        .then((res) => {
          this.msg = "Get Token Success!!";

          accessToken = res.data.access_token;
          
          // regist token to kakao sdk
          window.Kakao.Auth.setAccessToken(accessToken);


          // Get User info
          let this_vue = this;
          window.Kakao.API.request({
            url: '/v2/user/me',
            success: function(res) {
                this_vue.msg = 'Get User info success!!';

                let email = res.kakao_account.email;
                let nickname = res.kakao_account.profile.nickname;
                let profile_pic = res.kakao_account.profile.profile_image;

                // Join or Login
                this_vue.loginWithKakaoInfo({email, nickname, profile_pic});
            },
            fail: function(error) {
                this_vue.msg = 'Get user info fail';
            }
          });
        })
        .catch(err => {
          this.msg = "Get Token fail";
          alert('Token not Receiv');
        });
    }
  },
  data: () => {
    return {
      msg: ''
    }
  }
};
</script>