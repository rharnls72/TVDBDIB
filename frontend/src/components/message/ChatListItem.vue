<template>
  <div class="feed-item">
      <ul class="list-group">
        <li v-for="(room) in rooms" v-bind:key="room.cno" 
          class="list-group-item d-flex align-items-center" type="button" @click="movePage(room)">

        <div class="box">
          <img v-if="room.mainUser.profile_pic!=null" :src='room.mainUser.profile_pic' class="profile" alt="profile">
          <img v-else :src='defaultProfile' class="profile" alt="profile">
        </div>
        {{room.mainUser.nick_name}}<span v-if="room.users.length>2">님 외 {{room.users.length-2}}명</span>
          <div class="btnGroup">
            <div class="inbtnGroup">
                <button @click.stop.prevent="deleteChat(room)" class="delbtn">삭제</button>
            </div>
          </div>
        </li>

      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
import MessageApi from "@/api/MessageApi";
export default {
  name: 'ChatListItem',
  data: () => {
    return {
      defaultProfile
    };
  },
  props: {
    rooms: Array
  },
  methods: {
    movePage(room){
        this.$router.replace({path: '/message/chatroom/' + room.cno, query: {room: room}});
    },
    deleteChat(room, event){
      // event.stopPropagation();
      MessageApi.deleteChatroom( room.cno,
          res => {
            
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
      );
    },
  }
}

</script>

<style scoped>
ul {
    padding-top: 0px;
}
  .box {
    width: 40px;
    height: 40px; 
    border-radius: 70%;
    overflow: hidden;
    margin-right: 15px;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.btnGroup{
  display: table; 
  height: 50px; 
  width: 100px;
  position: absolute;
  right: 20px;
}
.inbtnGroup{
  display: table-cell; 
  vertical-align: middle;
}
.delbtn{
  margin-left: 25px;
  width: 60px;
  height: 30px;
  border-radius: 5px;
  font-size: 13px;
  background-color: #D8BEFE;
}
</style>