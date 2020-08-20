<template>
  <div class="row">
    <img v-if="program.programDetail.backdrop_path === null" class="col-4" :src="defaultImg">
    <img class="col-4" :src="'https://image.tmdb.org/t/p/w500'+program.programDetail.backdrop_path">
    <div class="col-8 d-flex align-items-center">
      {{program.programDetail.name}}
    </div>
  </div>
</template>

<script>
import defaultImg from '@/assets/images/custom/logo.png'
export default {
  name: 'ProgramThumbnail',
  data() {
    return {
      defaultImg,
    }
  },
  props: {
    program: Object,
  }
}
</script>