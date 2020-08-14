<template>
    <div>
      <ul class="list-group"> 
          <li v-for="(user) in users" v-bind:key="user.uno" 
           class="list-group-item d-flex align-items-center" type="button" @click="movePage(user)">
            
        <div class="box">
              <img v-if="user.profile_pic!=null" :src='user.profile_pic' class="profile" alt="profile">
              <img v-else :src='defaultProfile' class="profile" alt="profile">
          </div>
          {{user.nick_name}}

          <div class="btnGroup">
            <div class="inbtnGroup">
                <button @click="unfollow(user)" class="unfollowbtn" :class="{hidden: !user.isFollowing}">언팔로우</button>
                <button @click="follow(user)" class="followbtn" :class="{hidden: user.isFollowing}">팔로우</button>
            </div>
          </div>
        </li>      
    </ul>
</div>

</template>

<script>
import defaultProfile from '@/assets/images/profile_default.png'
import http from '@/api/http-common.js';
import header from "@/api/header.js"

export default {
    name: 'FollowListItem',
  data: () => {
    return {
      defaultProfile,
    };
  },
  props: {
    users: Array
  },
  methods:{
    movePage(user){
        this.$router.push('/profile/' + user.nick_name);
    },

    unfollow(user){
        http.post('/following/user/cancel', {
            follower: this.$store.state.userInfo.uno,
            following: user.uno
        }, header())
        .then(res => {
          this.makeToast("언팔로우를 완료했습니다.", "primary");
            let delete_index = this.$props.users.findIndex(x => x.uno == user.uno);
            this.$props.users.splice(delete_index, 1);
            user.isFollowing = 0;
            this.$props.users.splice(delete_index, 0, user);
        })
        .catch(err => this.makeToast(err, "danger"))
    },

    follow(user){
        console.log(user);
        http.post('/following/user/add', {
            follower: this.$store.state.userInfo.uno,
            following: user.uno
        }, header())
        .then(res => {
          this.makeToast("팔로우에 성공했습니다.", "primary");
            let delete_index = this.$props.users.findIndex(x => x.uno == user.uno);
            this.$props.users.splice(delete_index, 1);
            user.isFollowing = 1;
            this.$props.users.splice(delete_index, 0, user);
        })
        .catch(err => this.makeToast(err, "danger"))
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
    padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
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
.hidden{
    display: none;
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
.followbtn{
  width: 80px;
  height: 30px;
  border-radius: 5px;
  font-size: 13px;
  background-color: #D8BEFE;
}
.unfollowbtn{
  width: 80px;
  height: 30px;
  border-radius: 5px;
  font-size: 13px;
  background-color: #f8e8f2;
}
</style>