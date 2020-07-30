<template>
  <div id="app" class="columns">
    <FollowerHeader />
        <div>
      <!-- tabIndex라는 값을 v-model 이용해서 제어해서 탭 이동을 구현. 하위 b-tab에서 class만 바꾸는 걸로는 안 됐다 -->
    <b-tabs v-model="tabIndex" class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
    </b-tabs>
    </div>

      <div class="wrapB">
        <UserListItem v-if='loadComplete' :users="users"/>
      </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import http from '@/api/http-common.js';
import Footer from '@/components/common/custom/Footer.vue';
import FollowerHeader from '@/components/account/FollowerHeader.vue';
import UserListItem from '@/components/account/UserListItem.vue';
import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'AlertTest',
   components: {
    UserListItem,
    Footer,
    FollowerHeader,
  },

  data() {
    return {
    loadComplete: false,
      users: [],
      my_followings: []
  }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    let uno = this.$route.params.uno;

    // 이 사람을 팔로우하는 사람의 리스트를 가져온다
    http.get('/following/user/followers/' + uno)
        .then(res => {
            console.log(res);
            this.users = res.data.data.user_list;
            this.requestMyFollowing();
        })
        .catch(err => console.error(err))
  },

  methods: {
      requestMyFollowing(){
        let myuno = this.$store.state.userInfo.uno;
        console.log(myuno);
        http.get('/following/user/followings/' + myuno)
        .then(res => {
            console.log(res);
            let temp_following_list = res.data.data.user_list;
            let no_arr = []
            for (let i=0; i<temp_following_list.length; i++){
                no_arr.push(temp_following_list[i].uno);
            }
            console.log(no_arr);
            // 해당 유저가 나도 팔로중인 유저라면, isFollowing을 true로 만들어준다
            for (let i=0; i<this.users.length; i++){
                console.log(no_arr.indexOf(this.users[i].uno))
                if (no_arr.indexOf(this.users[i].uno) >= 0){
                    console.log("TEST");
                    this.users[i].isFollowing = true;
                }
                else{
                    this.users[i].isFollowing = false;
                }
                console.log(this.users[i]);
            }
            this.loadComplete = true;
        })
        .catch(err => console.error(err))
    
      }
  }

};
</script>
