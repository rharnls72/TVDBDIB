<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <!-- 새 글 작성 -->
      <button @click="createFeed">
        <b-icon-plus-circle class="text-dark"></b-icon-plus-circle>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center" @click="test"> <!-- 여기에 test 이벤트 바인딩해서 아직 구현되지 않은 기능 테스트 해봤음 -->
      <p class="text-dark mb-0"><strong>TVility</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
      <!-- 메시지 -->
      <button>
        <b-icon-chat-dots class="text-dark"></b-icon-chat-dots>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
//////////// 테스트용 임포트 //////////////////
import Api from "@/api/FeedApi.js";
//////////////////////////////////////////////

export default {
  name: 'IndexCuarationHeader',
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

      Api.feedDetail(
        3
        , res => console.log(res)
        , err => console.log(err)
      )
    }
  },
}
</script>

<style>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>