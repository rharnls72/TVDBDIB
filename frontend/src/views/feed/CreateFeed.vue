<template>
  <div>
    <div v-if="this.isArticle">
      <CreateVote v-if="page===3" :article="article" :fno="fno"/>
      <CreateArticle v-else-if="page===1" :article="article" :fno="fno"/>
      <CreateCountdown v-else :article="article" :fno="fno"/>
    </div>
    <div class="container">
      <div class="page-btns col row">
        <div @click="turnArticle" class="col d-flex justify-content-center">
          <div class="link-btn d-flex justify-content-center align-items-center">
            <b-icon icon="pencil-square" font-scale="1.4"></b-icon>
          </div>
        </div>
        <div @click="turnCountdown" class="col d-flex justify-content-center">
          <div class="link-btn d-flex justify-content-center align-items-center">
            <b-icon icon="stopwatch" font-scale="1.4"></b-icon>
          </div>
        </div>
        <div @click="turnVote" class="col d-flex justify-content-center">
          <div class="link-btn d-flex justify-content-center align-items-center">
            <b-icon icon="card-checklist" font-scale="1.4"></b-icon>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CreateVote from '@/components/feed/CreateVote.vue'
import CreateArticle from '@/components/feed/CreateArticle.vue'
import CreateCountdown from '@/components/feed/CreateCountdown.vue'
import axios from "axios"
import header from "@/api/header.js"

import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'CreateFeed',
  components: {
    CreateVote, CreateArticle, CreateCountdown
  },
  data() {
    return {
      page: 1,
      article: null,
      isArticle: true,
      fno: null,
    }
  },
  methods: {
    turnArticle() {this.page = 1},
    turnVote() {this.page = 3},
    turnCountdown() {this.page = 2},
  },
  mounted() {
    if (!this.$route.params.ftype === false) {
      this.page = Number(this.$route.params.ftype)
    } else {
      this.page = 1
    }
    if (!this.$route.params.feedId === false) {
      this.isArticle = false
      this.fno = Number(this.$route.params.feedId)
      console.log(this.$route.params.feedId)
      axios.get('http://localhost:9000/feed/detail/' + this.$route.params.feedId, header())
       .then(res => {
         console.log(res)
         this.article = {
           content: JSON.parse(res.data.data.content),
           tag: JSON.parse(res.data.data.tag),
           ctype: res.data.data.ctype,
           dibsNum: res.data.data.dibs_num,
           fno: res.data.data.fno,
           press_dibs: res.data.data.press_dibs,
           press_like: res.data.data.press_like,
           profile_pic: res.data.data.profile_pic,
           reply_content: res.data.data.reply_content,
           reply_num: res.data.data.reply_num,
           thumbnail: res.data.data.thumbnail,
           uno: res.data.data.uno,
        }
         console.log(this.article)
         this.isArticle = true
       })
       .catch(err => console.log(err))
    } else {
      this.fno = null
      this.article = null
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
}
</script>

<style scoped>
.link-btn {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: gray;
}
.page-btns {
  position: fixed;
  bottom: 0;
  width: 100%;
  margin-bottom: 3rem;
}
</style>