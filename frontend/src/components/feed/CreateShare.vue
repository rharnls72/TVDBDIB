<template>
  <div>
    <!-- 헤더 -->
    <CreateHeader @submit="submitShare"/>

    <div class="px-5">
      
      <!-- 공유글 작성 -->
      <ShareForm @CreateArticle="makeSubmitData"/>

      <!-- 공유될 글 표기 -->
      <div class="feed-item mt-3">
        <FeedArticleThumbnail v-if="article.ctype!==null && article.ctype === 1" :article="article"/>
        <FeedCountdownThumbnail v-if="article.ctype!==null && article.ctype === 2" :article="article"/>
        <FeedVoteThumbnail v-if="article.ctype!==null && article.ctype === 3" :article="article"/>
        <EpisodeThumbnail v-if="!!article.eno" :curation="article"/>
      </div>

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

import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: 'CreateShare',
  data() {
    return {
      article: null,
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
      
      // Axios 요청
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
    }
  }
}
</script>

<style scoped>
.feed-item {
  border-bottom: none;
  border-top: none;
}
</style>
