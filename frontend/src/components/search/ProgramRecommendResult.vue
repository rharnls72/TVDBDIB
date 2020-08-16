<template>
    <div class="scroll-container">
        <div v-if="programs == null">추천을 해주기에 아직 데이터가 부족해요. 
          <br>아래에서 관심있는 프로그램을 찾아 팔로우해보세요!
        </div>
        <div v-for="(program) in programs" v-bind:key="program.pno" class="card">
            <img :src='program.thumbnail' @click="goDetail(program.pno)" width="75rem" height="120rem" class="img-fluid" alt="poster">
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