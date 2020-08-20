<template>
  <div class="col-12 m-0 p-0">
    <div @click="moveDetail" class="col-12 row m-0 px-0 py-2 align-items-center">
      
      <img class="col-6 mythumbnail" :src="episode.thumbnail"/>

      <div class="col-6">
        <div class="content d-flex align-items-center">
          <div><span>[{{episode.pname}} </span><span>{{episode.episode}} 화]</span></div>
          <div>{{episode.episode_name}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DibEpisode',
  props: {
    episode: Object,
  },
  methods: {
    moveDetail(eno) {
      this.$router.push({path: `/episode/detail/${this.episode.pno}/${this.episode.season}/${this.episode.episode}`})
    }
  }
}
</script>