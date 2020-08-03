<template>
    <b-nav justified class="myheader">
      <b-input-group class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div class="input-group-text py-0" style="border: 0px; background-color: #eee;">
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
</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import axios from "axios";

export default {
  data() {
    return {
      word: "",
      programs: [],
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
        let base_url = "https://api.themoviedb.org/3/";
        let api_key = "1436d388221956af7b6cd27a6a7ec9d8";

        axios.get(base_url + "search/tv?query=" + newWord + "&api_key=" + api_key + "&language=ko")
            .then(res => {
                console.log(res);
                this.programs = res.data.results;
            })
            .catch(error => {
                console.log(error);
            });
    }
  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead
  }
}
</script>
<style scoped>

</style>