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
    <div class="feed-card">
      <div class="mythumbnail d-flex flex-column justify-content-center align-items-center">
        <div v-for="content in vote" :key="content.id" style="width: 80%;">
          <div class="my-2">
            <label>{{content.content}}</label>
            <b-progress :value="content.count" :max="totalNum" show-progress></b-progress>
          </div>
        </div>
      </div>
    </div>
    <!---->
    <div class="btn-group wrap justify-content-between" style="margin: 15px 0 0 0;">
      <div>
        <!-- 좋아요 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchLikeIcon">
            <b-icon-heart v-if="!likeIcon"></b-icon-heart>
            <b-icon-heart-fill v-else variant="danger"></b-icon-heart-fill>
          </button>
        </div>
        <!-- 댓글 -->
        <div class="mr-3">
          <button class="h6 mr-1">
            <b-icon-chat></b-icon-chat>
          </button>
        </div>
        <!-- 스크랩 -->
        <div class="mr-3">
          <button class="h6 mr-1" @click="touchScrapIcon">
            <b-icon-bookmark v-if="!scrapIcon"></b-icon-bookmark>
            <b-icon-bookmark-fill v-else variant="success"></b-icon-bookmark-fill>
          </button>
          0 
          <!-- 스크랩 카운트 -->
        </div>
        <!---->
      </div>
      <div class="mr-1">
        <!-- 명세에 있는 공유 (url만 복사하면 됨) -->
        <div>
          <button class="h5">
            <b-icon-reply></b-icon-reply>
          </button>
        </div>
      </div>
    </div>
    <div>
      <span class="font-weight-bold">좋아요 {{like_num}}명</span>
    </div>
    <div class="wrap mt-2">
      <span v-for="tag in tags" :key="tag" class="tag">#{{tag}} </span><br>
      <span class="moreView">댓글 {{reply_num}}개</span>
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
      likeIcon: false,
      scrapIcon: false,
      reply_num: 11,
    }
  },
  props: {
    // vote: Object,
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
    },
    touchLikeIcon() {
      this.likeIcon = !this.likeIcon
      if (this.likeIcon) {
        this.likeCount ++
      }
      else {
        this.likeCount --
      }
      // console.log(this.likeIcon)
    },
    touchScrapIcon() {
      this.scrapIcon = !this.scrapIcon
      if (this.scrapIcon) {
        this.scrapCount ++
      }
      else {
        this.scrapCount --
      }
      // console.log(this.scrapIcon)
    },
  },
  updated() {
    this.totalNumber()
  },
  created() {
    // console.log(this.vote)
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
.mythumbnail {
  background-color: beige;
  width: 100v;
  height: 55v;
}
</style>
