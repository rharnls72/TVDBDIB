<template>
<div>
    <!-- <b-nav justified class="myheader">
      <b-input-group class="align-items-center" style="height: 40px;">
          <div @click="searchIcon" class="input-group-text" style="border: 0px; background-color: #D8BEFE;">
            <b-icon-search></b-icon-search>
          </div> -->
        
        <!-- <b-form-input
          type="search"
          placeholder="검색"
          ref="searchInput"
          v-model="word"
          class="p-0"
          style="height: auto; border: 0px; background-color: #eee;">
        </b-form-input> -->
       
       <!-- 이 컴포넌트 쓰면 목록 내에서 AutoComplete 가 되는데
            css 를 어떻게 줘야하지
            https://github.com/alexurquhart/vue-bootstrap-typeahead
         -->

<!-- @hit="selectedUser = $event" -->
        <!-- <vue-bootstrap-typeahead
          :data="users"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="u => u.nick_name"
          :minMatchingChars='1'
          placeholder="type a username"
          ref="searchInput"
          style="height: auto; width: 80%; border: 0px; background-color: #D8BEFE;"
        />
      </b-input-group>
    </b-nav> -->
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
// import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
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
      // SearchApi.getUserList(
      //   this.word,
      //   res => {
      //     this.users_result = res.data.data;
      //     // setTimeout(()=>{}, 1000)
      //     this.toNextPage();
      //   },
      //   err => {
      //     console.log(err);
      //   }
      // );
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
    //SearchApi,
    // VueBootstrapTypeahead,
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