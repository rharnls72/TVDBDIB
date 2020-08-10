<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <!-- 새 글 작성 -->
      <button @click="createFeed">
        <b-icon-plus-circle class="text-dark"></b-icon-plus-circle>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center" @click="test"> <!-- 여기에 test 이벤트 바인딩해서 아직 구현되지 않은 기능 테스트 해봤음 -->
      <img class="mylogo mb-0\" :src="HeaderLogo" alt="header-logo">
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
      <!-- 메시지 -->
      <button>
        <b-icon-chat-dots class="text-dark" @click="message"></b-icon-chat-dots>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
//////////// 테스트용 임포트 //////////////////
import Api from "@/api/FeedApi.js";
//////////////////////////////////////////////
import http from "@/api/http-common.js";
import header from "@/api/header.js"
import HeaderLogo from '../../assets/images/custom/header-logo.png'

export default {
  name: 'IndexCuarationHeader',
  data() {
    return {
      HeaderLogo,
    }
  },
  methods: {
    // 피드 작성 페이지로 이동
    createFeed() {
      this.$router.push({name: 'CreateFeed'})
    },

    // 테스트용 메서드
    test() {
      let data = {
        is_dibs: 1
        , num: 1
      };

      http.post('/episode/dibs/list', data, header())
        .then(res => {
            if(res == null) console.log("res == null");
            else            console.log(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    message(){
      this.$router.push({name: 'MessageList'});
    }
  },
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
  .mylogo {
    height: 35px;
  }
</style>