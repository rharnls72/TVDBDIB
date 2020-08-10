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

    <ResultItems :feeds_result="part_feeds_result"/>
    <infinite-loading v-if="loading_complete && !isEndPoint" @infinite="infiniteHandler"></infinite-loading>
</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import ResultItems from "@/components/search/FeedSearchResult.vue";
import InfiniteLoading from 'vue-infinite-loading';

export default {
  name: 'FeedSearch',
  data() {
    return {
      startPoint: 0,
      interval: 5,
      word: "",
      tags: [],
      total_feeds_result: [],
      part_feeds_result: [],
      loading_complete: false,
      isEndPoint: false // 끝까지 도달했으면 더이상 무한스크롤 돌아가는거 안 보이게
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
            requestCount : 0
        }
        , res => {
            this.total_feeds_result = res.data.data;

          for (let i=0; i<this.total_feeds_result.length; i++) {
            this.total_feeds_result[i].content = JSON.parse(this.total_feeds_result[i].content)
            this.total_feeds_result[i].tag = JSON.parse(this.total_feeds_result[i].tag)
          }

          console.log(this.total_feeds_result);
          setTimeout(()=>{}, 1000)

          this.toNextPage();
        }
        , err => {
            console.log(err);
        }
        );
    },

    infiniteHandler($state) {
      setTimeout(() => {
        if (this.loading_complete == true){
          this.toNextPage();
        }
        $state.loaded();
      }, 1000);
    },

    toNextPage() {
      let temp = []
      for (let i = this.startPoint; i < this.startPoint + this.interval; i++) {
        if (i >= this.total_feeds_result.length){
            this.isEndPoint = true;
            break;
        }
        temp.push(this.total_feeds_result[i])
      }
      this.part_feeds_result = this.part_feeds_result.concat(temp);
      this.startPoint += this.interval;
      this.loading_complete = true;
    },

  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead,
    ResultItems,
    InfiniteLoading
  }
}
</script>
<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
    position: sticky;
  }
</style>