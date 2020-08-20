<template>
  <div>
    <div v-if="article != null" class="feed newsfeed">
      <EpisodeHeader :episode="article"/>
      <div class="wrapB">

        <div class="myfeed">
          <EpisodeItem :curation="article" :detail="true" :isStretch="isStretch" class="myei"/>
          <ReplyItem @addReply="addReplyCount" @delReReply="delReReply" @delReply="res=>delReply(res)" :auno="0" :eno="article.eno"/>
        </div>

      </div>
      <Footer />
    </div>
    <LoadingItem v-else />
  </div>
</template>

<script>
import EpisodeItem from "@/components/curation/episode/EpisodeItem.vue"
import ReplyItem from "@/components/ReplyItem.vue"
import Footer from '@/components/common/custom/Footer.vue';
import EpisodeHeader from '@/components/curation/episode/EpisodeHeader.vue'
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import CurationApi from "@/api/CurationApi.js"
import GetUserApi from "@/api/GetUserApi.js"

export default {
  name: "EpisodeDetail",
  components: {
    EpisodeItem,
    ReplyItem,
    EpisodeHeader,
    Footer,
    LoadingItem
  },
  data() {
    return {
      article: null,
      isStretch: true,
    }
  },
  methods: {
    delReply(res) {
      console.log(res)
      this.article.reply_num -= res
    },
    delReReply() {
      this.article.reply_num --
    },
    addReplyCount() {
      this.article.reply_num ++
    }
  },
  created() {

    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

    CurationApi.requestEpisodeDetail({
      pno: this.$route.params.pno,
      season: this.$route.params.season,
      episode: this.$route.params.episode
    }
    , res=> {
      console.log(res)
      this.article = res.list
    }
    , err=> console.log(err))
  }
}
</script>

<style scoped>
  .myfeed {
    padding-top: 50px;
    border-bottom: none;
  }
  .myei {
    border-bottom: none;
  }
</style>