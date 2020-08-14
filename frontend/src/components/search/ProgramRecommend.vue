<template>
<div>
    <h4>취향 저격 프로그램</h4>
    <ResultItems :programs="tasty_programs"/>
    <h4>요즘 뜨는 프로그램</h4>
    <ResultItems :programs="trending_programs"/>
    <h4>새로 나온 프로그램</h4>
    <ResultItems :programs="new_programs"/>
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
      new_programs: []
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