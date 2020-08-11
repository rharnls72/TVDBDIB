<template>
  <div class="row">
    <img class="col-4" :src="'https://image.tmdb.org/t/p/w500'+program.programDetail.backdrop_path">
    <div class="col-8">
      {{program.programDetail.name}}
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProgramThumbnail',
  props: {
    program: Object,
  }
}
</script>

<style>

</style>