<template>
<div>
  <div v-if="show">
    <ProgramHeader :programTitle="program.programDetail.name"/>
    <div class="container mycontainer">
      <ProgramPageInformation @changeSeason="changeSeason" @checkFollowers="readFollower" :program="program" :followers="followers" :seasonNum="seasonNum"/>
      <ProgramTap @addReply="addReply" @delReply="res => delReply(res)" :episodes="episodes" :program="program"/>
    </div>
    <infinite-loading v-if="episodeNum !== 0" @infinite="infiniteHandler"></infinite-loading>
    <Footer/>
  </div>
  <LoadingItem v-else/>
</div>
</template>

<script>
import ProgramHeader from '@/components/curation/program/ProgramHeader.vue'
import ProgramPageInformation from '@/components/curation/program/ProgramPageInformation.vue'
import ProgramTap from "@/components/curation/program/ProgramTap.vue"
import Footer from '@/components/common/custom/Footer.vue'
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import CurationApi from '@/api/CurationApi.js'
import GetUserApi from '@/api/GetUserApi.js'

import InfiniteLoading from 'vue-infinite-loading'

export default {
  name: 'ProgramPage',
  data() {
    return {
      program: null,
      episodes: [],
      followers: null,
      show: false,
      pickEpisode: 10,
      seasonNum: 1,
      episodeNum: null,
    }
  },
  components: {
    ProgramHeader,
    ProgramPageInformation,
    ProgramTap,
    Footer,
    LoadingItem,
  },
  methods: {
    changeSeason(res) {
      this.seasonNum = res
    },
    addReply() {
      this.program.reply_num++
    },
    delReply(num) {
      this.program.reply_num -= num
    },
    readFollower() {
      CurationApi.programFollower(
        this.program.programDetail.id
        , res => {
          console.log(res)
          this.followers = res.data.data.user_list
        }
        , err => console.log(err)
      )
    },
    takeEpisode(cnt) {
      if (this.pickEpisode === cnt) {
        return
      } else if (this.episodeNum - cnt <= 0) {
        this.episodeNum = 0
        return
      }
      CurationApi.requestEpisodeDetail({
        pno: this.program.programDetail.id,
        season: this.seasonNum,
        episode: this.episodeNum--
      }
      ,res => {
        this.episodes.push(res.list)
        this.takeEpisode(cnt+1)
      }
      ,err => console.log(err)
      )
    },
    infiniteHandler($state) {
      setTimeout(() => {
        if (this.episodeNum !== 0){
          this.takeEpisode(0)
        }
        $state.loaded();
      }, 300);
    },
  },
  watch: {
    program: function(e, n) {
      this.readFollower()
    },
    seasonNum: function(e, n) {
      this.episodes = []
      this.episodeNum = this.program.programDetail.seasons[this.seasonNum].episode_count
    },
  },
  created() {

    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  
    CurationApi.programDetail(
      this.$route.params.pno
      , res => {
        res.data.data.programDetail = JSON.parse(res.data.data.programDetail);
        res.data.data.episodeGroup = JSON.parse(res.data.data.episodeGroup);
        this.program = res.data.data
        this.episodeNum = res.data.data.programDetail.seasons[this.seasonNum].episode_count
        this.takeEpisode(0)
        // this.episodes.sort((a, b) => a.episode - b.episode)
      }
      , err => console.log(err)
    )
    this.show = !this.show
  }
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>