<template>
  <div>
    <ChooseFollowingHeader :choosed="choosed"/>
        <div class="searchArea">
          <div @click="searchIcon" class="searchIcon">
                <b-icon-search style="width: 20px; height: 20px;"></b-icon-search>
          </div>
          <input class="searchInput" type="text" id="searchInput" placeholder="친구 검색" 
          v-on:input='word = $event.target.value' v-on:keyup='searchIcon()' autocomplete="off" 
          @focus="magic_flag = true" @blur="magic_flag = false" ref="searchInput">
          <div class="vbt-autcomplete-list" v-if="(word!='' && magic_flag) || userSelect" @mousedown="userSelect=true">
            <UserSearchResult2 :users_result="part_users_result" :choosed="choosed" v-on:clear="clear()"/>
          </div>
        </div>
      <ChoosedItem :choosed="choosed"/>
      <ChooseFollowingItem v-if='loadComplete' :followings="followings" :choosed="choosed"/>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import Footer from '@/components/common/custom/Footer.vue';
import ChooseFollowingHeader from '@/components/message/ChooseFollowingHeader.vue';
import ChooseFollowingItem from '@/components/message/ChooseFollowingItem.vue';
import ChoosedItem from '@/components/message/ChoosedItem.vue';
import GetUserApi from "@/api/GetUserApi";
import MessageApi from "@/api/MessageApi";
import SearchApi from '@/api/SearchApi.js';
import header from "@/api/header.js"
import http from '@/api/http-common.js';
import UserSearchResult2 from '@/components/message/UserSearchResult2.vue'
export default {
  name: 'ChooseFollowing',
   components: {
    ChooseFollowingItem,
    Footer,
    ChooseFollowingHeader,
    ChoosedItem,
    UserSearchResult2
  },

  data() {
    return {
      loadComplete: false,
      choosed: [],
      followings: [],
      users_result: [],
      users: [],
      word: "",
      selectedUser: null,
      magic_flag: false,
      userSelect: false,
      part_users_result: [],
  }
  },

  created(){
    this.selectedUser = false;
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

    let uno = this.$store.state.userInfo.uno;

    // 팔로잉 유저 리스트 받아오기
    http.get('/following/user/followings/' + uno, header())
    .then(res => {
        this.followings = res.data.data.user_list;
        this.loadComplete = true;
    })
    .catch(err => console.error(err));

    SearchApi.getAllUser(
      'NoData'
      , res => {
        this.users = res.data.data;
        let user = this.users.find(user => user.uno == uno);
        let idx = this.users.indexOf(user)
        if (idx > -1) 
          this.users.splice(idx, 1)
      }
      , err => {
        console.log(err);
      }
    );
  },
  methods: {
    clear(){
      this.$refs.searchInput.value = "";
      this.word = '';
      this.userSelect = false;
    },
    searchIcon(){
      this.part_users_result = this.users.filter(
          (user) => user.nick_name.toUpperCase().includes(this.word.toUpperCase())
      );
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
    },
  }

};
</script>
<style>
 .searchArea{
    padding-top: 55px;
    width: 100%;
    background-color: #f8e8f2;
    display: table;
    margin-bottom: 15px;
  }
  .searchIcon{
    width: 60px;
    padding-left: 20px;
    vertical-align: middle;
    display: table-cell;
    border: 0px; 
    background-color: #f8e8f2;
  }
  .searchInput{
    padding: 0;
    border: 0px;
    width: 80%;
    color: #52565c;
    font-size: 17px;
    background-color: #f8e8f2;
    font-weight: medium;
    vertical-align: middle;
    display: table-cell;
  }
  #searchInput:focus{
    outline: none;
    border: 0;
  }
  #searchInput:hover{
    outline: none !important;
    border: 0 !important;
  }
  .vbt-autcomplete-list{
    width: 75%;
    position: absolute;
    max-height: 350px;
    /* overflow-y: auto; */
    z-index: 999;
  }
</style>
