<template>
  <div>
    <EpisodeItem :episodeId="id"/>
  </div>
</template>

<script>
import EpisodeItem from '@/components/curation/episode/EpisodeItem.vue'
import GetUserApi from "@/api/GetUserApi"
import CurationApi from "@/api/CurationApi"

export default {
  name: 'EpisodeDetail',
  data() {
    return {
      curations: [],
      id: 0,
    }
  },
  components: {
    EpisodeItem,
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    CurationApi.requestEpisodeDetail(
        {id : this.id},
        res => {
            this.curations = res.detail;
            this.id = res.detail.episode_id;
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
    );
  }
}
</script>

<style>

</style>