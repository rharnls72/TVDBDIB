<template>
  <div>
    <ChatroomHeader :room="room"/>
        <h1>채팅룸</h1>
      <div class="feed-item messageInputArea">
          <input class="messageInput" type="text" id="messageInput" v-model="content" autocomplete="off">
          <button class="sendIcon" @click.stop.prevent="sendMessage()"><b-icon-arrow-right-circle class="text-dark">
            </b-icon-arrow-right-circle></button>
      </div>
  </div>
</template>

<script>
import "@/components/css/user.scss";
import ChatroomHeader from '@/components/message/ChatroomHeader.vue';
import GetUserApi from "@/api/GetUserApi";
import MessageApi from "@/api/MessageApi";
import db from '@/api/firebaseInit';

export default {
  name: 'EmptyChatroom',
   components: {
    ChatroomHeader,
  },

  data() {
    return {
      loadComplete: false,
      room: this.$route.params.room,
      content: '',
    }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    this.loadComplete = true;
  },
  methods: {
    sendMessage(){
      if(this.content.length>0){
        MessageApi.createChatRoom(
          this.room,
          res => {
            this.room.cno = res.cno;
            let data={
              content: this.content,
              cno: this.room.cno,
              sender: this.$store.state.userInfo.uno,
            }
            MessageApi.sendMessage(
              data,
                res => {},
                error => {
                  this.$router.push({name:'Errors', query: {message: error.msg}})
                }
            );
            this.$router.replace({path: '/message/chatroom/' + this.room.cno, query: {room: this.room}});
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
  .sendIcon {
    width: 40px;
    height: 40px;
    float: right;
    vertical-align: middle;
    z-index: 1;
  }
  .messageInput {
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
</style>
