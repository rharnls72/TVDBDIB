<template>
<div>
    <div v-if="word==''">
    <div calss="listSubject">최근 검색</div>
    <ResultItems :users_result="search_history"/>
    <div calss="listSubject">전체</div>
    </div>
    <ResultItems :users_result="part_users_result"/>
    <infinite-loading v-if="loading_complete && !isEndPoint" @infinite="infiniteHandler"></infinite-loading>
</div>

</template>

<script>
import SearchApi from '@/api/SearchApi.js';
import ResultItems from "@/components/search/UserSearchResult.vue";
import InfiniteLoading from 'vue-infinite-loading';

export default {
  name: 'UserSearch',
  data() {
    return {
      startPoint: 0,
      interval: 20,
      users: [],
      users_result: [],
      part_users_result: [],
      search_history: [],
      selectedUser: {},
      loading_complete: false,
      isEndPoint: false
    }
  },
  props:{
    word: String,
  },
  watch: {
    word: function () {
      this.searchIcon()
    },
  },
  mounted() {
    SearchApi.getAllUser(
      'NoData'
      , res => {
        this.users = res.data.data;
        this.part_users_result = this.users;
        this.searchIcon();
      }
      , err => {
        console.log(err);
      }
    );
    this.getHistoryList();
  },
  methods: {
    getHistoryList(){
      // 유저 히스토리 가져오기
      SearchApi.getHistoryList(
        "noData"
        , res => {
          this.search_history = res.data.data;
        }
        , err => {
          console.log(err);
        }
      );
    },
    searchIcon(){
      this.part_users_result = this.users.filter(
          (user) => user.nick_name.toUpperCase().includes(this.word.toUpperCase())
        );
    },
    // 탭을 클릭하면 해당 탭을 활성화
    moveTab(name) {
      this.$router.push({name: name})
    },
    getUserList(newWord) {
      SearchApi.getUserList(
        newWord,
        res => {
          this.users = res.data.data;
        },
        err => {
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
        if (i >= this.users_result.length){
            this.isEndPoint = true;
            break;
        }
        temp.push(this.users_result[i])
      }
      this.part_users_result = this.part_users_result.concat(temp);
      this.startPoint += this.interval;
      this.loading_complete = true;
      //console.log(this.part_users_result);
    },

  },
  components: {
    ResultItems,
    InfiniteLoading
  }
}
</script>
<style scoped>
  .listSubject{
    background-color: #D8BEFE;
    color: #52565c;
  }
</style>