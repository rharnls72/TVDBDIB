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
        <!-- <span class="" type="button">
          <button @click.stop.prevent="deleteChat(room)" class="">삭제</button>
        </span> -->
          <div class="btnGroup">
            <div class="inbtnGroup">
                <button @click.stop.prevent="deleteChat(room)" class="delbtn">삭제</button>
            </div>
          </div>
        <!-- <div class="form-group row" style="width: 100%;">
            <img class="my-auto col-3 col-sm-3" src="@/assets/images/profile_default.png">


            <span class="user-content my-auto col-6 col-sm-6">
              
            </span>
            <span class="float-right my-auto col-3 col-sm-3 removeBtn" type="button">
                <button @click.stop.prevent="deleteChat(room)" class="float-right btn btn-danger btn-sm">삭제</button>
            </span>
        </div> -->
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
  methods:{
    movePage(room){
        this.$router.push({path: '/message/chatroom/' + room.cno, query: {room: room}});
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
    makeToast(message, variant){
        this.$bvToast.toast(message, {
          title: '알림',
          toaster: "b-toaster-bottom-right",
          variant: variant,
          autoHideDelay: 3000,
          appendToast: false
        })
     }
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
