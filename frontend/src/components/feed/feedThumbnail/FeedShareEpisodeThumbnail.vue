<template>
  <div class="row" @click="moveDetail">
    <img v-if="curation.thumbnail===null" class="col-4" :src="defaultImg" alt="default-image">
    <img v-else class="col-4" :src="curation.thumbnail" alt="thumbnail-image">
    <div class="col-8 d-flex align-items-center">
      [{{curation.pname}}] {{curation.episode}}화
    </div>
  </div>
</template>

<script>
import defaultImg from '@/assets/images/custom/logo.png'
export default {
  name: 'FeedShareEpisodeThumbnail',
  data() {
    return {
      defaultImg,
    }
  },
  props: {
    curation: Object,
  },
  mounted() {
    console.log(this.curation)
  },
  methods: {
    moveDetail() {
      this.$router.push({path:`/episode/detail/${this.curation.pno}/${this.curation.season}/${this.curation.episode}`})
    }
  },
}
</script>