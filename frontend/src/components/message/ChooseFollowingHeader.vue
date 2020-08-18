<template>
  <b-nav justified class="myheader d-flex justify-content-between align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>새 메시지</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
      <button @click="createChatroom">
      <b-icon icon="check-circle" class="text-dark"></b-icon>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
import MessageApi from "@/api/MessageApi";
export default {
  name: 'ChooseFollowingHeader',
  props: {
    choosed: Array,
  },
  methods: {
    backpage(){
      this.$router.go(-1);
    },
    createChatroom(){
      let users = [this.$store.state.userInfo.uno];
      let user = {};
      this.choosed.forEach(choose => {
        users.push(choose.uno);
        user = choose;
      });
      users.sort();
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
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    top: 0;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>