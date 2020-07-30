<template>
  <div>
    <CreateVote v-if="page==='vote'"/>
    <CreateArticle v-else-if="page==='article'"/>
    <CreateCountdown v-else/>
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
import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'CreateFeed',
  components: {
    CreateVote, CreateArticle, CreateCountdown
  },
  data() {
    return {
      page: "article",
    }
  },
  methods: {
    turnArticle() {this.page = 'article'},
    turnVote() {this.page = 'vote'},
    turnCountdown() {this.page = 'countdown'},
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