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
      rooms: [],
      my_followings: []
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
        querySnapshot.forEach(function(doc) {
          temp.push(doc.data());
        });
        this.rooms = temp;
        this.getUserInfo();
    },
    getUserInfo(){
      
      this.loadComplete = true;
    }
  }

};
</script>
