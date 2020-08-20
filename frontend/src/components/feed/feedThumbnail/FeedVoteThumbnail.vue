<template>
  <div class="feed-card">
    <div class="mythumbnail d-flex flex-column justify-content-center align-items-center">
      <div v-for="content in article.content.content" :key="content.id" style="width: 80%;">
        <div class="mt-2 mb-3">
          <label class="d-flex justify-content-between">
            <span>{{content.text}}</span>
            <span v-if="votePos == 0" @click="voteOption(content.id)" class="moreView"><b-icon-check-circle></b-icon-check-circle></span>
          </label>
          <b-progress class="myprogress" :value="voteInfo[content.id - 1]" :max="totalNum" variant="danger" show-progress></b-progress>
        </div>
      </div>
      <h5 v-if="votePos>0" @click="voteClear" class="moreView mb-3"><b-icon-arrow-counterclockwise></b-icon-arrow-counterclockwise></h5>
    </div>
  </div>
</template>

<script>
import VoteApi from "@/api/VoteApi.js";

export default {
  name: "feedVoteThumbnail",
  data() {
    return {
      totalNum: 0,

      votePos: 0,
      voteInfo: null
    };
  },
  props: {
    article: Object,
  },
  methods: {
    voteOption(pos) {
      // 프론트 우선 반영
      this.totalNum++;
      this.votePos = pos;
      this.voteInfo[pos - 1]++;

      VoteApi.createVote(
        {
          fno: this.article.fno
          , pos: pos
        }
        , res => {
          console.log("Vote 추가 성공", res);
        }
        , err => {
          console.log("Vote 추가 실패", err);
        }
      )
    },
    voteClear() {
      // Front 우선 적용
      this.totalNum--;
      this.voteInfo[this.votePos - 1]--;
      this.votePos = 0;

      VoteApi.deleteVote(
        this.article.fno
        , res => {
          console.log("Vote 삭제 성공", res);
        }
        , err => {
          console.log("Vote 삭제 실패", err);
        }
      )
    }
  },
  updated() {
    // this.totalNumber();
  },
  created() {
    console.log('FeedVoteThumbnail created()', this.article);

    // 항목 갯수 가져오기
    let len = this.article.content.content.length;

    // 기본적으로 모든 투표 수가 0 인 정보 만들기
    this.voteInfo = new Array(len);
    for(let idx = 0; idx < len; idx++) {
      this.voteInfo[idx] = 0;
    }

    // 투표 정보 가져오기
    VoteApi.readVote(
      this.article.fno
      , res => {
        console.log('readVote res', res);

        // 항목 번호와 투표수 정보 가져오기
        let list = res.data.data;

        // 유저가 어디에 투표했는지 정보 가져오기
        if(list.length > 0) {
          this.votePos = list[0].user_pos;
  
          // 총 투표수 계산, 투표 정보 가져오기
          this.totalNum = 0;
          for(let idx = 0; idx < list.length; idx++) {
            let d = list[idx];

            this.totalNum += d.count;
            this.voteInfo[d.pos - 1] = d.count;
          }
        }
      }
      , err => {
        console.log("Vote 정보를 가져오는데 실패했습니다 : " + this.article.fno)
      }
    )
  },
};
</script>

<style scoped>
  .mythumbnail {
    background-color: #f8e8f2;
    width: 100v;
    height: 55v;
  }
  .myprogress {
    background-color: lightpink;
  }
</style>
