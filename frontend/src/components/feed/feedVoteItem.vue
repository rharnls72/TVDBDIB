<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>SSAFY</button>
        </div>
        <p class="date">9시간 후</p>
      </div>
      <div class="content">
        <p>{{feedTitle}}</p>
      </div>
    </div>
    <div class="wrap">
      <div class="p-2 pt-3 d-flex flex-column justify-content-center align-items-center">
        <div v-for="content in vote" :key="content.id" style="width: 100%;">
          <label>{{content.content}}</label>
          <b-progress :value="content.count" :max="totalNum" show-progress class="mb-3"></b-progress>
        </div>
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
      <span v-if="isLong">... <span class="moreView" @click="changeIsLong">더 보기</span></span>
      <span v-else>
        <span>{{content}}</span><br>
        <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span>
      </span>
    </div>
    <div v-if="!isLong" class="wrap mt-2">
      <div class="row d-flex align-items-center px-3">
        <b-form-input style="border:none;" type="text" class="m-0 col rounded-pill" v-model="comment" placeholder="댓글 입력!!!">
        </b-form-input>
        <b-icon icon="plus-circle" class="ml-1 text-right" font-scale="1.4"></b-icon>
      </div>
    </div>
  </div>
</template>

<script>
import defaultImage from "@/assets/images/img-placeholder.png";
import defaultProfile from "@/assets/images/profile_default.png";
export default {
  data: () => {
    return { 
      defaultImage, defaultProfile,
      feedTitle: '투표투표',
      vote: [
        {id: 0, content:"항목 1", count:10},
        {id: 1, content:"항목 2", count:40},
        {id: 2, content:"항목 3", count:8},
      ],
      totalNum: null,
      comment: null,
      tags: ['소통', '맞팔', '너무', '귀엽당', 'ㅎㅎ'],
      reply: ['wow', '너무 좋아용 ㅎㅎ'],
      like_num: 12,
      isLong: true,
    }
  },
  methods: {
    totalNumber() {
      let t = 0
      this.vote.foreach(res => {
        t += res.count
      })
      this.totalNum = t
    },
    changeIsLong() {
      this.isLong=false
    }
  },
  updated() {
    this.totalNumber()
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
