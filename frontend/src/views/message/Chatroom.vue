<template>
  <div>
    <ChatroomHeader :room="room"/>
    <MessageItem v-if='loadComplete' :messages="messages" class="mymessage"/>
    <div class="feed-item messageInputArea">
      <input class="messageInput" type="text" id="messageInput" v-model="content" autocomplete="off">
      <button class="sendIcon" @click.stop.prevent="sendMessage()">
        <b-icon-arrow-right-circle class="text-dark"></b-icon-arrow-right-circle>
      </button>
    </div>
  </div>
</template>

<script>
import "@/components/css/user.scss";
import ChatroomHeader from '@/components/message/ChatroomHeader.vue';
import MessageItem from '@/components/message/MessageItem.vue';
import GetUserApi from "@/api/GetUserApi";
import MessageApi from "@/api/MessageApi";
import db from '@/api/firebaseInit';

export default {
  name: 'Chatroom',
   components: {
    MessageItem,
    ChatroomHeader,
  },

  data() {
    return {
      loadComplete: false,
      messages: [],
      userInfos: [],
      room: this.$route.query.room,
      content: '',
    }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    MessageApi.requestUserInfo(
        this.room.users,
          res => {
            if(res.userInfo!=null){
              this.userInfos = res.userInfo;
              db.collection("message").where("cno", "==", this.room.cno).orderBy("time", "asc")
              .onSnapshot(this.getMessagesList);
            }
            this.loadComplete = true;
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
      );
  },
  methods: {
    getMessagesList(querySnapshot) {
        // this.makeToast("알림이 왔습니다.", "primary");
        let temp = [];
        let message = {};
        let tempUsers = this.userInfos;
        let me =  this.$store.state.userInfo;
        querySnapshot.forEach(function(doc) {
          message = doc.data();
          tempUsers.forEach(user => {
              if(user.uno == message.sender){
                message.user = user;
              }
          });
          temp.push(message);
        });
        this.messages = temp;
    },
    sendMessage(){
      if(this.content.length>0){
        let data={
          content: this.content,
          cno: this.room.cno,
          sender: this.$store.state.userInfo.uno,
        }
        MessageApi.sendMessage(
          data,
            res => {
              this.content = '';
            },
            error => {
              this.$router.push({name:'Errors', query: {message: error.msg}})
            }
        );
      }
    }
  }
};
</script>

<style scoped>
.messageInputArea{
    margin-bottom: 0;
    padding: 0;
    background-color: #eee;
    border-radius: 12px;
    position: fixed;
    bottom: 0;
    z-index: 1;
  }
  .sendIcon{
    width: 40px;
    height: 40px;
    float: right;
    vertical-align: middle;
    z-index: 1;
  }
  .messageInput{
    width: 100%;
    height: 40px;
    border: 0;
    color: rgb(112, 112, 112);
    font-size: 15px;
    font-weight: medium;
    vertical-align: middle;
    position: fixed;
    left: -40px;
    z-index: 1;
    padding-left: 60px;
    padding-right: 0;
  }
  .messageInput:focus {
    border:none;
  }
  .messageInput:hover {
    border:none;
  }
  .mymessage {
    padding-top: 70px;
  }
</style>
