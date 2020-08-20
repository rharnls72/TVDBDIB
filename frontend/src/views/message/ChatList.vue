<template>
  <div id="app" class="columns">
    <ChatListHeader/>
    <div class="searchArea">
      <div @click="searchIcon" class="searchIcon">
        <b-icon-search style="width: 20px; height: 20px;"></b-icon-search>
      </div>
      <input class="searchInput" type="text" id="searchInput" placeholder="친구 검색" 
      v-on:input='word = $event.target.value' v-on:keyup='searchIcon()' autocomplete="off" 
      @focus="magic_flag = true" @blur="magic_flag = false">
      <div class="vbt-autcomplete-list" v-if="(word!='' && magic_flag) || userSelect" @mousedown="userSelect=true">
        <UserSearchResult :users_result="part_users_result" />
      </div>
    </div>
    <ChatListItem v-if='loadComplete' :rooms="rooms"/>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import Footer from '@/components/common/custom/Footer.vue';
import ChatListHeader from '@/components/message/ChatListHeader.vue';
import ChatListItem from '@/components/message/ChatListItem.vue';
import GetUserApi from "@/api/GetUserApi";
import MessageApi from "@/api/MessageApi";
import db from '@/api/firebaseInit';
import SearchApi from '@/api/SearchApi.js';
import UserSearchResult from '@/components/message/UserSearchResult.vue'
export default {
  name: 'ChatList',
   components: {
    ChatListItem,
    Footer,
    ChatListHeader,
    UserSearchResult
  },

  data() {
    return {
      loadComplete: false,
      temprooms: [],
      rooms: [],
      userInfos: [],
      users: [],
      users_result: [],
      word: "",
      selectedUser: null,
      part_users_result: [],
      magic_flag: false,
      userSelect: false,
  }
  },

  created(){
    this.selectedUser = false;
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    let uno = this.$store.state.userInfo.uno;
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
    db.collection("chat_room").where("users", "array-contains", uno).orderBy("time", "desc")
      .onSnapshot(this.getChatList);
  },
  methods: {
    searchIcon(){
      this.part_users_result = this.users.filter(
          (user) => user.nick_name.toUpperCase().includes(this.word.toUpperCase())
      );
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
    getChatList(querySnapshot) {
        let temp = [];
        let unos = [];
        let myUno = this.$store.state.userInfo.uno;
        querySnapshot.forEach(function(doc) {
          temp.push(doc.data());
          if(doc.data().users[0] == myUno){
            unos.push(doc.data().users[1]);
          }else{
            unos.push(doc.data().users[0]);
          }
        });
        this.userInfos = unos;
        this.temprooms = temp;
        this.getUserInfo();
    },
    getUserInfo(){
      MessageApi.requestUserInfo(
        this.userInfos,
          res => {
            if(res.userInfo!=null){
              let temp;
              res.userInfo.forEach(user => {
                temp = this.userInfos.indexOf(user.uno);
                while(temp!= -1){
                  this.temprooms[temp].mainUser = user;
                  this.userInfos[temp] =-1;
                  temp = this.userInfos.indexOf(user.uno);
                }
              });
            }
            this.rooms = this.temprooms;
            this.loadComplete = true;
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
      );
    }
  }

};
</script>

<style scoped>
  .searchArea{
    padding-top: 55px;
    width: 100%;
    background-color: #f8e8f2;
    display: table;
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
