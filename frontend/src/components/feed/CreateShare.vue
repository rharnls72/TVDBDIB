<template>
  <div class="feed-create">
    <!-- 헤더 -->

    <CreateHeader class="feed-header" @submit="submitShare"/>

      
    <!-- 공유글 작성 -->
    <div class="create-feed-form row justify-content-center">
      <ShareForm :article="editArticle" @CreateArticle="makeSubmitData"/>
    </div>

    <!-- 공유될 글 표기 -->
    <div class="feed-item px-5">
      <FeedArticleThumbnail v-if="article.ctype!==null && article.ctype === 1" :article="article"/>
      <FeedCountdownThumbnail v-if="article.ctype!==null && article.ctype === 2" :article="article"/>
      <FeedVoteThumbnail v-if="article.ctype!==null && article.ctype === 3" :article="article"/>
      <EpisodeThumbnail v-if="!!article.eno" :curation="article"/>
      <FeedProgramThumbnail v-else :program="article"/>
    </div>


  </div>
</template>

<script>
import FeedApi from '@/api/FeedApi.js'
import Curation from '@/api/CurationApi.js'

import CreateHeader from '@/components/feed/feedComponents/CreateHeader.vue'
import ShareForm from '@/components/feed/feedComponents/ShareForm.vue'
import FeedArticleThumbnail from '@/components/feed/feedThumbnail/FeedArticleThumbnail.vue'
import FeedCountdownThumbnail from '@/components/feed/feedThumbnail/FeedCountdownThumbnail.vue'
import FeedVoteThumbnail from '@/components/feed/feedThumbnail/FeedVoteThumbnail.vue'
import EpisodeThumbnail from '@/components/curation/episode/EpisodeThumbnail.vue'
import FeedProgramThumbnail from '@/components/feed/feedThumbnail/FeedProgramThumbnail.vue'

import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'CreateShare',
  data() {
    return {
      article: null,
      editArticle: null,
      submitData: null,
    }
  },
  components: {
    CreateHeader,
    ShareForm,
    FeedArticleThumbnail,
    FeedCountdownThumbnail,
    FeedVoteThumbnail,
    EpisodeThumbnail,
    FeedProgramThumbnail,
  },
  methods: {
    moveMain() {
      console.log(1)
      this.$router.push('/feed/main')
    },
    
    makeSubmitData(res) {
      res.content.article = this.article
      res.content = JSON.stringify(res.content)
      this.submitData = res
    },

    submitShare() {

      if (!this.$route.params.fno) {
        // Axios 요청

        console.log(this.submitData)

        FeedApi.createFeed(
          // 요청에 쓸 데이터 전달
          this.submitData
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log('createFeed Success: ' + res);
            this.$router.push({path: '/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log('createFeed Error: ' + err);
          } 
        )
      } else {

        this.submitData.fno = this.$route.params.fno

        FeedApi.updateFeed(
          // 요청에 쓸 데이터 전달
          this.submitData
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log('createFeed Success: ' + res);
            this.$router.push({path: '/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log('createFeed Error: ' + err);
          } 
        )
      }
    },
      
  },
  created() {
    
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

    if (this.$route.params.type === "0") {
      FeedApi.feedDetail(
        this.$route.params.no
        , res => {
          this.article = res.feed
          this.article.content = JSON.parse(this.article.content)
          this.article.tag = JSON.parse(this.article.tag)
        }
        , err => console.log(err)
      )
    } else if (this.$route.params.type === "1") {
      Curation.requestEpisodeDetail({
        pno: this.$route.params.pno,
        season: this.$route.params.season,
        episode: this.$route.params.no,
      }
      , res => {
        this.article = res.list
      }
      , err => console.log(err)
      )
    } else if (this.$route.params.type === "2") {
      console.log("프로그램")
      Curation.programDetail(
        this.$route.params.no
        , res => {
          console.log(res)
          this.article = res.data.data
          this.article.programDetail = JSON.parse(this.article.programDetail)
        }
        , err => console.log(err)
      )
    } else if (this.$route.params.fno !== null) {
      FeedApi.feedDetail(
        this.$route.params.fno
        , res => {
          console.log(res)
          res.feed.content = JSON.parse(res.feed.content)
          res.feed.tag = JSON.parse(res.feed.tag)
          this.article = res.feed.content.article
          this.editArticle = res.feed
        }
        , err => console.log(err)
      )
    }

    $('html').scrollTop(0)
    
  }
}
</script>

<style scoped>
.feed-item {
  border-bottom: none;
  border-top: none;
}
.create-feed-form {
  width: 100%;
  padding-top: 15vh;
  padding-bottom: 5vh;
  bottom: 0;
  margin-left: 0;
  margin-right: 0;
}
.feed-create {
  background-color: #f8e8f2;
}
.feed-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  padding: 20px;
}
</style>
