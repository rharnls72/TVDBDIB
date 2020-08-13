<template>
  <div>
    <ProgramHeader :programTitle="program.programDetail.name"/>
    <div class="container mycontainer">
      <ProgramPageInformation @checkFollowers="readFollower" :program="program" :followers="followers"/>
      <ProgramTap @addReply="addReply" @delReply="res => delReply(res)" :episodes="episodes" :program="program"/>
    </div>
    <Footer/>
  </div>
</template>

<script>
import ProgramHeader from '@/components/curation/program/ProgramHeader.vue'
import ProgramPageInformation from '@/components/curation/program/ProgramPageInformation.vue'
import ProgramTap from "@/components/curation/program/ProgramTap.vue"
import Footer from '@/components/common/custom/Footer.vue'

import CurationApi from '@/api/CurationApi.js'
import GetUserApi from '@/api/GetUserApi.js'

export default {
  name: 'ProgramPage',
  data() {
    return {
      program: null,
      episodes: [],
      followers: null,
    }
  },
  components: {
    ProgramHeader,
    ProgramPageInformation,
    ProgramTap,
    Footer,
  },
  methods: {
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
    }
  },
  watch: {
    program: function(e, n) {
      this.readFollower()
    }
  },
  created() {

    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  
    CurationApi.programDetail(
      this.$route.params.pno
      , res => {
        console.log(res.data.data)
        res.data.data.programDetail = JSON.parse(res.data.data.programDetail);
        res.data.data.episodeGroup = JSON.parse(res.data.data.episodeGroup);
        this.program = res.data.data
        for (let i=this.program.programDetail.last_episode_to_air.episode_number; i>0; i--) {
          CurationApi.requestEpisodeDetail({
            pno: this.program.programDetail.id,
            season: this.program.programDetail.last_episode_to_air.season_number,
            episode: i
          }
          ,res => {
            this.episodes.push(res.list)
          }
          ,err => console.log(err)
          )
        }
      }
      , err => console.log(err)
    )

  }
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>