<template>
<div>
    <div class="searchArea">
      <div @click="searchIcon" class="searchIcon">
            <b-icon-search style="width: 20px; height: 20px;"></b-icon-search>
      </div>
      <input class="searchInput" type="text" id="searchInput" placeholder="친구 검색" v-on:input='word = $event.target.value' v-on:keyup='searchIcon()'>
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
      word: "",
      users: [],
      users_result: [],
      part_users_result: [],
      search_history: [],
      selectedUser: {},
      loading_complete: false,
      isEndPoint: false
    }
  },
  created() {
    // 모든 유저 정보를 받아와도 크기가 많이 안클거같은데
    // 아니면 이거 안쓰고 아래 watch 에 주석 지워서 
    // 매 글자 입력마다 목록 새로 가져와도 되고
    
    SearchApi.getAllUser(
      'NoData'
      , res => {
        this.users = res.data.data;
        this.searchIcon();
      }
      , err => {
        console.log(err);
      }
    );

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
  watch: {
    // word(newWord) {
    //   // this.getUserList(newWord);
    // },
    selectedUser(newUser) {
      console.log(newUser);
      let data = {
        search_uno : newUser.uno
      };
      SearchApi.addHistory(
        data
        , res => {
        }
        , err => {
          console.log(err);
        }
      );
      this.$router.push("/profile/" + newUser.nick_name);
    }
  },
  methods: {
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
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    z-index: 1;
    position: sticky;
    height: 40px;
  }
  .mysearchbar {
    border: 0;
    border-radius: 0.25rem;
  }
  .searchArea{
    width: 100%;
    background-color: #D8BEFE;
    display: table;
  }
  .searchIcon{
    width: 60px;
    padding-left: 20px;
    vertical-align: middle;
    display: table-cell;
    border: 0px; 
    background-color: #D8BEFE;
  }
  .searchInput{
    padding: 0;
    border: 0px;
    color: #52565c;
    font-size: 17px;
    background-color: #D8BEFE;
    font-weight: medium;
    vertical-align: middle;
    display: table-cell;
  }
</style>