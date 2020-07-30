<template>
  <div id="app" class="columns">
    <FollowingHeader />
        <div>
      <!-- tabIndex라는 값을 v-model 이용해서 제어해서 탭 이동을 구현. 하위 b-tab에서 class만 바꾸는 걸로는 안 됐다 -->
    <b-tabs v-model="tabIndex" class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
    </b-tabs>
    </div>

      <div class="wrapB">
        <UserListItem :users="users"/>
      </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import http from '@/api/http-common.js';
import Footer from '@/components/common/custom/Footer.vue';
import FollowingHeader from '@/components/account/FollowingHeader.vue';
import UserListItem from '@/components/account/UserListItem.vue';
import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'AlertTest',
   components: {
    UserListItem,
    Footer,
    FollowingHeader,
  },

  data() {
    return {
      users:[],
      my_following:[]
  }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    let uno = this.$route.params.uno;

    http.get('/following/user/followings/' + uno)
    .then(res => {
        console.log(res);
        this.users = res.data.data.user_list;
    })
    .catch(err => console.error(err))

    uno = 
    http.get('/following/user/followings/' + uno)



  },

};
</script>
