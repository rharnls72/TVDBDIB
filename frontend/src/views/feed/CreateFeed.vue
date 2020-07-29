<template>
  <div>
    <div v-if="this.isArticle">
      <CreateVote v-if="page===3" :article="article"/>
      <CreateArticle v-else-if="page===1" :article="article"/>
      <CreateCountdown v-else :article="article"/>
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
    }
  },
  methods: {
    turnArticle() {this.page = 1},
    turnVote() {this.page = 3},
    turnCountdown() {this.page = 2},
  },
  mounted() {
    if (!this.$route.params.ftype === false) {this.page = Number(this.$route.params.ftype)}
    if (!this.$route.params.feedId === false) {
      this.isArticle = false
      console.log(this.$route.params.feedId)
      axios.get('http://localhost:9000/feed/detail/' + this.$route.params.feedId, header())
       .then(res => {
         this.article = {
           content: JSON.parse(res.data.data.content),
           tag: JSON.parse(res.data.data.tag),
         }
         this.isArticle = true
       })
       .catch(err => console.log(err))
    }
  }
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