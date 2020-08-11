<template>
  <div id="app" class="columns">
    <ChatListHeader />
        <h2>메시지</h2>
        
        <b-nav justified class="myheader">
      <b-input-group class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div @click="searchIcon" class="input-group-text py-0" style="border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>
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
      <h1>!</h1>
      <div class="wrapB">
        <ChatListItem v-if='loadComplete' :rooms="rooms"/>
      </div>
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
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';

export default {
  name: 'MessageList',
   components: {
    ChatListItem,
    Footer,
    ChatListHeader,
    VueBootstrapTypeahead
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
      selectedUser: null
  }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
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
    let uno = this.$store.state.userInfo.uno;
    db.collection("chat_room").where("uno", "==", uno).orderBy("time", "desc")
      .onSnapshot(this.getChatList);
  },
  watch: {
    selectedUser(user){
      // this.$router.push({name:'EmptyChatroom', query: {user: user}})
    }
  },
  methods: {
    searchIcon(){
      SearchApi.getUserList(
        this.word,
        res => {
          this.users_result = res.data.data;
          setTimeout(()=>{}, 1000)
          this.toNextPage();
        },
        err => {
          console.log(err);
        }
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
    getChatList(querySnapshot) {
        // this.makeToast("알림이 왔습니다.", "primary");
        let temp = [];
        let uno = [];
        querySnapshot.forEach(function(doc) {
          temp.push(doc.data());
          uno.push(doc.data().other[0]);
        });
        this.userInfos = uno;
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
              this.rooms = this.temprooms;
            }
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
<style>
.mysearchbar {
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
</style>
