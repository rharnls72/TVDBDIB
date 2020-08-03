<template>
    <b-nav justified class="myheader">
      <b-input-group @click="searchIcon" class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div class="input-group-text py-0" style="border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>
        
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

        <vue-bootstrap-typeahead
          :data="users"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="u => u.nick_name"
          :minMatchingChars='1'
          placeholder="type a username"
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

export default {
    name: 'UserSearch',
  data() {
    return {
      tabState: 3,
      word: "",
      users: [],
      search_history: [],

      selectedUser: {}
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
        console.log(this.users);
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
        console.log(this.search_history);
      }
      , err => {
        console.log(err);
      }
    );
  },
  watch: {
    word(newWord) {
      // this.getUserList(newWord);
    },
    selectedUser(newUser) {
      console.log(newUser);
      let data = {
        search_uno : newUser.uno
      };
      SearchApi.addHistory(
        data
        , res => {
          console.log("Add History Success!!");
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
      // this.$refs.searchInput.focus();
    },
    // 탭을 클릭하면 해당 탭을 활성화
    moveTab(name) {
      this.$router.push({name: name})
    },
    getUserList(newWord) {
      SearchApi.getUserList(
        newWord,
        res => {
          console.log(res.data.data);
          this.users = res.data.data;
        },
        err => {
          console.log(err);
        }
      );
    }
  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead
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
  }
  .mysearchbar {
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
</style>