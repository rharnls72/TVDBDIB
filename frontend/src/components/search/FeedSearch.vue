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

    <ResultItems :feeds_result="feeds_result"/>
</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import ResultItems from "@/components/search/FeedSearchResult.vue";

export default {

  data() {
    return {
      requestCount: 1,
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

    searchIcon(){
        // 맨앞에 # 붙여서 검색했을 경우 알아서 빼주기
        if (this.word.startsWith("#"))
            this.word = this.word.substring(1);

        SearchApi.searchByTag(
        {
            word: this.word,
            requestCount : this.requestCount
        }
        , res => {
            this.feeds_result = res.data.data;

          for (let i=0; i<this.feeds_result.length; i++) {
            this.feeds_result[i].content = JSON.parse(this.feeds_result[i].content)
            this.feeds_result[i].tag = JSON.parse(this.feeds_result[i].tag)
          }

          this.requestCount++
          console.log(this.feeds_result);
          setTimeout(()=>{}, 1000)
        }
        , err => {
            console.log(err);
        }
        );
    },

    removeFeed(fno) {this.feeds = this.feeds.filter(res => res.fno!==fno)}

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