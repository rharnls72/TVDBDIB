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
          :data="tags"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="t => '#' + t.name"
          :minMatchingChars='1'
          :maxMatches='15'
          placeholder="search feeds by tag name"
          ref="searchInput"
          style="height: auto; width: 80%; border: 0px; background-color: #eee;"
        />
      </b-input-group>
    </b-nav>

    <ResultItems :feeds="feeds_result"/>
</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import ResultItems from "@/components/search/FeedSearchResult.vue";

export default {

  data() {
    return {
      word: "",
      tags: [],
      feeds_result: []
      //search_history: [],
      //selectedUser: {},
    }
  },
  
  watch: {
    word(newWord) {
        this.getTagList(newWord);
    },
  },
  
  methods: {
    getTagList(newWord) {
        SearchApi.getTagList(
        newWord
        , res => {
            console.log(res);
            this.tags = res.data.data;
            
        }
        , err => {
            console.log(err);
        }
        );
    },

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