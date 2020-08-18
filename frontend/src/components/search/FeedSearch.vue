<template>
<div>
  <FeedTagSearchResult :tags="tags" :selectTag="selectTag" @update:selectTag="tag => selectTag = tag"/>
    <ResultItems :feeds_result="part_feeds_result"/>
    <infinite-loading v-if="loading_complete && !isEndPoint" @infinite="infiniteHandler"></infinite-loading>
</div>

</template>

<script>
import SearchApi from '@/api/SearchApi.js';
import ResultItems from "@/components/search/FeedSearchResult.vue";
import InfiniteLoading from 'vue-infinite-loading';
import FeedTagSearchResult from '@/components/search/FeedTagSearchResult.vue';

export default {
  name: 'FeedSearch',
  data() {
    return {
      startPoint: 0,
      interval: 5,
      alltags: [],
      tags: [],
      total_feeds_result: [],
      part_feeds_result: [],
      loading_complete: false,
      isEndPoint: false, // 끝까지 도달했으면 더이상 무한스크롤 돌아가는거 안 보이게
      selectTag: '',
    }
  },
  props:{
    word: String,
  },
  watch: {
    word: function (){
      this.selectTag = '';
      // this.searchIcon();
      this.getTagList();
    },
    selectTag: function(){
      if(this.selectTag!=''){
        this.tags = [];
        this.searchIcon();
      }
    }
  },
  mounted() {
    SearchApi.getTagList(
        res => {
            this.alltags = res.data.data;
            this.getTagList();
        }
        , err => {
            console.log(err);
        }
    );
  },
  methods: {
    getTagList() {
      this.tags = this.alltags.filter(
          (tag) => tag.name.toUpperCase().includes(this.word.toUpperCase())
        );
    },

    searchIcon(){
      let searchWord = this.word;
        // 맨앞에 # 붙여서 검색했을 경우 알아서 빼주기
        if (this.word.startsWith("#"))
            searchWord = this.word.substring(1);

        SearchApi.searchByTag(
        {
            word: searchWord,
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
    ResultItems,
    InfiniteLoading,
    FeedTagSearchResult
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