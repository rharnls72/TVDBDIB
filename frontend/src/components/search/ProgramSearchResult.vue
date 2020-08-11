<template>
    <div class="feed-item">
      <ul class="list-group"> 
          <li v-for="(program) in programs" v-bind:key="program.id" 
           class="list-group-item d-flex justify-content-between align-items-center" type="button" @click="goDetail(program.id)">
            {{program.name}}
        <div class="image-parent">
              <img :src='imgBaseUrl + program.poster_path' width="75rem" height="120rem" class="img-fluid" alt="poster">
          </div>
          </li>
          
    </ul>
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
  methods:{
      goDetail(id){
          this.$router.push("/program/" + id);
      }
  }
}
</script>

<style scoped>
ul {
  padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}
</style>