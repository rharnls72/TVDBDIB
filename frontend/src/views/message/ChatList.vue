<template>
  <div>
    <ChatListHeader />
    <div class="mymessage">
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
    </div>
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
  name: 'ChatList',
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
      }
      , err => {
        console.log(err);
      }
    );
    let uno = this.$store.state.userInfo.uno;
    db.collection("chat_room").where("users", "array-contains", uno).orderBy("time", "desc")
      .onSnapshot(this.getChatList);
  },
  watch: {
    selectedUser(user){
      let users = [this.$store.state.userInfo.uno, user.uno].sort();
      let room = {
          users: users,
          usersToString: JSON.stringify(users),
          mainUser: user
        }
      MessageApi.getChatRoom(
        {usersToString: room.usersToString},
        res => {
          if(res.room==null){
            this.$router.push({name:'EmptyChatroom', params: {room: room}})
          }else{
            room = res.room;
             MessageApi.requestUserInfo(
              [user.uno],
                res => {
                  if(res.userInfo!=null){
                    room.mainUser = res.userInfo[0];
                    this.$router.push({path: '/message/chatroom/' + room.cno, query: {room: room}})
                  }
                },
                error => {
                  this.$router.push({name:'Errors', query: {message: error.msg}})
                }
            );
          }
        },
        err => {
          console.log(err);
        }
      );
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
  .mymessage {
    padding-top: 50px;
  }
  .mysearchbar {
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
</style>
