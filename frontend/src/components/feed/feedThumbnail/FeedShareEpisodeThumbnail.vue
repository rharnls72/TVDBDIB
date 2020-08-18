<template>
  <div class="row">
    <img class="col-4" :src="curation.thumbnail">
    <div class="col-8 d-flex align-items-center">
      [{{curation.pname}}] {{curation.episode}}화
    </div>
  </div>
</template>

<script>
export default {
  name: 'FeedShareEpisodeThumbnail',
  props: {
    curation: Object,
  },
  mounted() {
    console.log(this.curation)
  }
}
</script>

<style>

</style>