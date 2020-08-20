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
    users_result: Array,
    choosed: Array
  },
  methods:{
      selectedUser(user){
        if(this.choosed.find(element => element.uno ==user.uno) == undefined){
          this.choosed.push(user);
           this.$emit('clear')
        }else{
          this.makeToast("이미 추가되었습니다.", "primary");
          this.$emit('clear')
        }
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