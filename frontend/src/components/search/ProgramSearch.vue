<template>
<div>
    <b-nav justified class="myheader" >
      <b-input-group class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div @click="searchIcon()" class="input-group-text py-0" style="border: 0px; background-color: #D8BEFE;">
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
    <!-- v-if를 넣어서 처음 페이지 띄웠을 때 (검색버튼 안 눌렀을 때) 는 동작 안하게 함 -->
    <infinite-loading v-if="loading_complete" @infinite="infiniteHandler"></infinite-loading>

</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import tmdbApi from '@/api/tmdbApi.js';
import axios from "axios";
import ResultItems from "@/components/search/ProgramSearchResult.vue";
import InfiniteLoading from 'vue-infinite-loading';

export default {
  name: 'ProgramSearch',
  data() {
    return {
      word: "",
      programs: [],
      programs_result: [],
      total_pages: 0,
      current_page: 0,
      loading_complete: false
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
    infiniteHandler($state) {
      setTimeout(() => {
        if (this.loading_complete == true && this.current_page < this.total_pages){
          this.toNextPage();
        }
        $state.loaded();
      }, 1000);
    },

    toNextPage(){
      this.loading_complete = false;
      this.current_page++;
        axios.get(tmdbApi.BASE_URL + "search/tv?query=" + this.word + "&api_key=" + tmdbApi.API_KEY + "&page=" + this.current_page + "&language=ko")
            .then(res => {
                console.log(res);
                this.programs_result = this.programs_result.concat(res.data.results);
                this.loading_complete = true;
            })
            .catch(error => {
                console.log(error);
            });
    },

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
                this.total_pages = res.data.total_pages;
                this.current_page = 1;
                this.loading_complete = true;
            })
            .catch(error => {
                console.log(error);
            });
    }
  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead,
    ResultItems,
    InfiniteLoading,
  }
}
</script>
<style scoped>
  .myheader {
    background-color: #D8BEFE;
    width: 100%;
    height: 50px;
    z-index: 1;
    position: sticky;
  }
</style>