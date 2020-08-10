<template>
<div>
    <h4>취향 저격 프로그램</h4>
    <ResultItems :programs="programs"/>
    <h4>요즘 뜨는 프로그램</h4>
    <ResultItems :programs="programs"/>
    <h4>새로 나온 프로그램</h4>
    <ResultItems :programs="programs"/>
</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import tmdbApi from '@/api/tmdbApi.js';
import axios from "axios";
import ResultItems from "@/components/search/ProgramRecommendResult.vue";

export default {

  data() {
    return {
      programs: [],
    }
  },

  created(){
      this.getProgramList();
  },
  
  methods: {
    getProgramList() {
        axios.get(tmdbApi.BASE_URL + "trending/tv/day?api_key=" + tmdbApi.API_KEY)
            .then(res => {
                console.log(res);
                this.programs = res.data.results;
            })
            .catch(error => {
                console.log(error);
            });
    },

  },
  components: {
    ResultItems,
  }
}
</script>
<style scoped>
  .myheader {
    background-color: #eee;
    width: 100%;
    height: 50px;
    z-index: 1;
    position: sticky;
  }

  h4 {
    margin: 5%;
  }
</style>