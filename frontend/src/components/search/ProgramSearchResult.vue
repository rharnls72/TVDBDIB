<template>
    <div class="topBox">
      <ul> 
          <li v-for="(program) in programs" v-bind:key="program.id"
            @click="goDetail(program.id)">
        <div class="imageBox">
          <div>
              <img v-if="program.poster_path != null" :src='imgBaseUrl + program.poster_path'
               class="img-fluid" alt="poster">
              <img v-else :src='defaultImage'
               style="padding-top:35px; padding-bottom: 35px;" class="img-fluid" alt="poster">
          </div>
            {{program.name}}
          </div>
          </li>
    </ul>
    </div>

</template>

<script>
import tmdbApi from '@/api/tmdbApi.js';
import defaultImage from "@/assets/images/custom/logo.png"

export default {
    name: 'ProgramSearchResult',
  data: () => {
    return {
        imgBaseUrl: tmdbApi.IMAGE_BASE_URL,
        defaultImage
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
.topBox{
  padding-bottom: 50px;
  margin: auto;
}
ul li {
  list-style-type: none;
  float: left;
}
ul {
  padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}
li{
  width: 100px;
  height: 200px;
  margin: 5px;
}
</style>