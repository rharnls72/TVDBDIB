<template>
    <div class="feed-item">
      <ul class="list-group"> 
          <li v-for="(user) in users_result" v-bind:key="user.uno" 
           class="list-group-item d-flex align-items-center" type="button" @click="goDetail(user)">
            
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
import SearchApi from '@/api/SearchApi.js';
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
      goDetail(user){
        let data = {
        search_uno : user.uno
      };
      SearchApi.addHistory(
        data
        , res => {
          this.$router.push("/profile/" + user.nick_name);
        }
        , err => {
          console.log(err);
        }
      );
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
</style>