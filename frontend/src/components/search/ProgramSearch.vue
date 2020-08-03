<template>
<div>
    <b-nav justified class="myheader" >
      <b-input-group class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div @click="searchIcon()" class="input-group-text py-0" style="border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>

        <!-- 자동완성 기능 지원하는 검색 창 컴포넌트 -->
        <vue-bootstrap-typeahead
          :data="programs"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="p => p.name"
          :minMatchingChars='1'
          :maxMatches='20'
          placeholder="type a program name"
          @hit="selectedUser = $event"
          ref="searchInput"
          style="height: auto; width: 80%; border: 0px; background-color: #eee;"
        />
      </b-input-group>
    </b-nav>

    <ResultItems :programs="programs_result"/>
</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import tmdbApi from '@/api/tmdbApi.js';
import axios from "axios";
import ResultItems from "@/components/search/ProgramSearchResult.vue";

export default {

  data() {
    return {
      word: "",
      programs: [],
      programs_result: []
      //search_history: [],
      //selectedUser: {},
    }
  },
  
  watch: {
    word(newWord) {
        this.getProgramList(newWord);
    },
  },
  
  methods: {
    getProgramList(newWord) {
        axios.get(tmdbApi.BASE_URL + "search/tv?query=" + newWord + "&api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
                console.log(res);
                this.programs = res.data.results;
            })
            .catch(error => {
                console.log(error);
            });
    },

    searchIcon(){
        axios.get(tmdbApi.BASE_URL + "search/tv?query=" + this.word + "&api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
                console.log(res);
                this.programs_result = res.data.results;
            })
            .catch(error => {
                console.log(error);
            });
    }
  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead,
    ResultItems
  }
}
</script>
<style scoped>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
    position: sticky;
  }
</style>