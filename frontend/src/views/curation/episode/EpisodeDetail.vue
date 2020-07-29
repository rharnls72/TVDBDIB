<template>
  <div>
    <EpisodeItem :episodeId="id"/>
  </div>
</template>

<script>
import EpisodeItem from '@/components/curation/episode/EpisodeItem.vue'
import axios from 'axios'
import GetUserApi from "@/api/GetUserApi"

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
    axios.get(`http://localhost:9000/episode/${this.id}`)
      .then(res => {
        this.curations = res.data.data
        this.id = res.data.data.episode_id
        console.log(this.curations)
      })
      .catch(err => console.error(err))
  }
}
</script>

<style>

</style>