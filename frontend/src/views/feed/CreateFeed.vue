<template>
  <div class="feed-create">
    <CreateFeedHeader @submitArticle="submitArticle"/>
    <div v-if="this.isArticle" class="create-feed-form row justify-content-center">
      <CreateVote v-if="page===3" :submit="submit.vote" :article="article" :fno="fno"/>
      <CreateArticle v-else-if="page===1" :submit="submit.article" :article="article" :fno="fno"/>
      <CreateCountdown v-else :article="article" :submit="submit.countdown" :fno="fno"/>
    </div>
    <CreateFeedFooter class="page-btns" @changeArticle="turnArticle" @changeVote="turnVote" @changeCountdown="turnCountdown"/>
  </div>
</template>

<script>
import CreateVote from '@/components/feed/CreateVote.vue'
import CreateArticle from '@/components/feed/CreateArticle.vue'
import CreateCountdown from '@/components/feed/CreateCountdown.vue'
import CreateFeedFooter from '@/components/feed/CreateFeedFooter.vue'
import CreateFeedHeader from '@/components/feed/CreateFeedHeader.vue'
import FeedApi from '@/api/FeedApi'

import GetUserApi from "@/api/GetUserApi"

import $ from "jquery"

export default {
  name: 'CreateFeed',
  components: {
    CreateVote, CreateArticle, CreateCountdown,
    CreateFeedFooter,
    CreateFeedHeader,
  },
  data() {
    return {
      page: 1,
      article: null,
      isArticle: true,
      fno: null,
      submit: {
        article: false,
        countdown: false,
        vote: false,
      }
    }
  },
  methods: {
    turnArticle() {this.page = 1},
    turnVote() {this.page = 3},
    turnCountdown() {this.page = 2},
    submitArticle() {
      if (this.page === 1) {
        this.submit.article = !this.submit.article
      } else if (this.page === 2) {
        this.submit.countdown = !this.submit.countdown
      } else {
        this.submit.vote = !this.submit.vote
      }
      console.log(this.page)
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    if (!this.$route.params.ftype === false) {
      this.page = Number(this.$route.params.ftype)
    } else {
      this.page = 1
    }
    if (!this.$route.params.feedId === false) {
      this.isArticle = false
      this.fno = Number(this.$route.params.feedId)
      FeedApi.feedDetail(
        this.$route.params.feedId,
        res => {
          console.log(res)
          this.article = res.feed;
          this.article.content = JSON.parse(this.article.content);
          this.article.tag = JSON.parse(this.article.tag);
          this.article.dibsNum = this.article.dibs_num;
          
          this.isArticle = true
        },
        error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
    } else {
      this.fno = null
      this.article = null
    }
    document.documentElement.scrollTop = 0
  },
}
</script>

<style scoped>
  .page-btns {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    margin-bottom: 3rem;
  }
  .create-feed-form {
    width: 100%;
    padding-top: 20vh;
    bottom: 0;
    margin-left: 0;
    margin-right: 0;
  }
  .feed-create {
    background-color: #f8e8f2;
    height: 100%;
    /* bottom: 0; */
  }
</style>