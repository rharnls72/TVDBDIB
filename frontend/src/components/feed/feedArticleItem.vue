<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>{{article.nick_name}}</button>
        </div>
        <p class="date">9시간 후</p>
      </div>
      <div class="content">
        <p>이 글은 아주 좋습니다.</p>
      </div>
    </div>
    <div class="wrap">
      <div class="d-flex justify-content-center align-items-center">
        <p class="p-4">
          #{{tags[0]}}
        </p>
      </div>
    </div>
    <!---->
    <div class="wrap d-flex justify-content-between">
      <div class="text-align-left">
        <b-icon class="mr-2" icon="heart" font-scale="1.5"></b-icon>
        <b-icon class="mr-2" icon="chat-square" font-scale="1.5"></b-icon>
        <b-icon icon="cursor" font-scale="1.5"></b-icon>
      </div>
      <div>
        <b-icon class="text-align-right" icon="bookmark" font-scale="1.5"></b-icon>
      </div>
    </div>
    <div class="wrap mt-2">
      <span class="font-weight-bold">좋아요 {{like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <span class="font-weight-bold">유저이름 </span>
      <span v-if="isLong">{{cc.content.slice(0, 10)}}... <span class="moreView" @click="changeIsLong">더 보기</span></span>
      <span v-else>
        <span>{{cc.content}}</span><br>
        <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span>
      </span>
    </div>
    <div v-if="!isLong" class="wrap mt-2">
      <div class="row d-flex align-items-center px-3">
        <b-form-input style="border:none;" type="text" class="m-0 col rounded-pill" v-model="additionReply" placeholder="댓글 입력!!!">
        </b-form-input>
        <b-icon @click="pushReply" icon="plus-circle" class="ml-1 text-right" font-scale="1.4"></b-icon>
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import {mapState} from "vuex"
import header from "@/api/header.js"
import axios from "axios"
export default {
  data: () => {
    return { 
      defaultImage, defaultProfile,
      content: 'ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ',
      comment: null,
      tags: ['소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ'],
      reply: ['wow', '너무 좋아용 ㅎㅎ'],
      additionReply: "",
      like_num: 12,
      isLong: false,
      cc: null,
    };
  },
  computed: {
    ...mapState([
      'userInfo',
    ])
  },
  props:{
    article: Object,
  },
  methods: {
    changeIsLong() {
      this.isLong=false
    },
    pushReply() {
      axios.post('http://localhost:9000/reply/feed/create',
      {
        no: this.article.fno,
        content: this.additionReply,
        writer_uno: 1,
      }, header())
    }
  },
  created() {
    console.log(this.article.content)
    this.cc = JSON.parse(this.article.content)
    console.log(this.cc)
    if (this.cc.content.length > 10) {this.isLong=true}
  }
};
</script>

<style scoped>
/* .content {
 border-bottom: 1px solid gray;
} */
.moreView {
  color: darkgray; 
}
.tag {
  color:deepskyblue;
}
</style>
