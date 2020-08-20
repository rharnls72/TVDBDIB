<template>
    <div class="userList">
      <ul class="list-group"> 
          <li v-for="(user) in users_result" v-bind:key="user.uno" 
           class="list-group-item d-flex align-items-center shadow" type="button" @click="selectedUser(user)" >
            
        <div class="box">
              <img v-if="user.profile_pic!=null" :src='user.profile_pic' class="profile" alt="profile">
              <img v-else :src='defaultProfile' class="profile" alt="profile">
          </div>
          {{user.nick_name}}
          </li>      
    </ul>
</div>

</template>

<script>
import defaultProfile from '@/assets/images/profile_default.png'
import MessageApi from "@/api/MessageApi";
export default {
    name: 'UserSearchResult',
  data: () => {
    return {
      defaultProfile: defaultProfile,
    };
  },
  props: {
    users_result: Array
  },
  methods:{
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
  }
}
</script>

<style scoped>
.userList{
  max-height: 400px;
  overflow:scroll;
}
ul {
    padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}
li{
  background-color: rgb(241, 241, 241);
}
  .box {
    width: 30px;
    height: 30px; 
    border-radius: 70%;
    overflow: hidden;
    margin-right: 15px;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>