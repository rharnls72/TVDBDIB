<template>
  <div id="app" class="columns">
    <MessageHeader />
        <h1>메시지</h1>
      <div class="wrapB">
        <ChatListItem v-if='loadComplete' :rooms="rooms"/>
      </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import Footer from '@/components/common/custom/Footer.vue';
import MessageHeader from '@/components/message/MessageHeader.vue';
import ChatListItem from '@/components/message/ChatListItem.vue';
import GetUserApi from "@/api/GetUserApi";
import MessageApi from "@/api/MessageApi";
import db from '@/api/firebaseInit';

export default {
  name: 'MessageList',
   components: {
    ChatListItem,
    Footer,
    MessageHeader,
  },

  data() {
    return {
      loadComplete: false,
      temprooms: [],
      rooms: [],
      userInfos: [],
  }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    let uno = this.$store.state.userInfo.uno;
    db.collection("chat_room").where("uno", "==", uno).orderBy("time", "desc")
      .onSnapshot(this.getChatList);
  },

  methods: {
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
              console.log(this.rooms);
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
