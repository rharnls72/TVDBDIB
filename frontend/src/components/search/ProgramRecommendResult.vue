<template>
    <div class="scroll-container">
        <div v-for="(program) in programs" v-bind:key="program.pno" class="card"  @click="goDetail(program.pno)">
            <img :src='program.thumbnail' width="75rem" height="120rem" class="img-fluid" alt="poster">
        </div>
    </div>

</template>

<script>
import tmdbApi from '@/api/tmdbApi.js';

export default {
    name: 'ProgramSearchResult',
  data: () => {
    return {
        imgBaseUrl: tmdbApi.IMAGE_BASE_URL
    };
  },
  props: {
    programs: Array
  },
  mounted() {
    console.log(this.programs)
  },
  methods:{
      goDetail(id){
          this.$router.push("/program/" + id);
      }
  }
}
</script>

<style scoped>
.scroll-container {
  overflow-x: scroll;
  overflow-y: hidden;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
  margin-bottom: 10%;
  margin-left: 5%;
  margin-right: 5%;
}

.card {
  display: inline-block;
}

</style>