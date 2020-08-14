<template>
<div>
    <h4>취향 저격 프로그램</h4>
    <div v-if="!tasty_loading">로딩중입니다...</div>
    <ResultItems v-if="tasty_loading" :programs="tasty_programs"/>
    <h4>요즘 뜨는 프로그램</h4>
    <div v-if="!trending_loading">로딩중입니다...</div>
    <ResultItems v-if="trending_loading" :programs="trending_programs"/>
    <h4>새로 나온 프로그램</h4>
    <div v-if="!new_loading">로딩중입니다...</div>
    <ResultItems v-if="new_loading" :programs="new_programs"/>
</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import RecommendApi from '@/api/RecommendApi.js';
import tmdbApi from '@/api/tmdbApi.js';
import axios from "axios";
import ResultItems from "@/components/search/ProgramRecommendResult.vue";

export default {

  data() {
    return {
      tasty_programs: [],
      trending_programs: [],
      new_programs: [],
      tasty_loading: false,
      trending_loading: false,
      new_loading: false
    }
  },

  created(){
      this.getProgramList();
  },
  
  methods: {
    getProgramList() {

        RecommendApi.getTastyProgram(
          "noData"
          , res => {
            this.tasty_programs = res.data.data;
            console.log(this.tasty_programs);
            this.tasty_loading = true;
          }
          , err => {
            console.log(err);
          }
        );

        RecommendApi.getTrendingProgram(
          "noData"
          , res => {
            this.trending_programs = res.data.data;
            console.log(this.trending_programs);  
            this.trending_loading = true; 
          }
          , err => {
            console.log(err);
          }
        );

        RecommendApi.getNewProgram(
          "noData"
          , res => {
            this.new_programs = res.data.data;
            console.log(this.new_programs);
            this.new_loading = true;
          }
          , err => {
            console.log(err);
          }
        );
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