<template>
  <div id="app" class="columns">
    <FollowerHeader />
      <h2>팔로워</h2>
      <div :class="{maxWidth:  windowWidth>1200}" style="margin: 0 auto;">
        <FollowListItem v-if='loadComplete' :users="users"/>
      </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import http from '@/api/http-common.js';
import Footer from '@/components/common/custom/Footer.vue';
import FollowerHeader from '@/components/account/FollowerHeader.vue';
import FollowListItem from '@/components/account/FollowListItem.vue';
import GetUserApi from "@/api/GetUserApi"
import header from "@/api/header.js"

export default {
  name: 'AlertTest',
   components: {
    FollowListItem,
    Footer,
    FollowerHeader,
  },

  data() {
    return {
    loadComplete: false,
      users: [],
      my_followings: [],
      windowWidth: window.innerWidth,
  }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    let uno = this.$route.params.uno;

    // 이 사람을 팔로우하는 사람의 리스트를 가져온다
    http.get('/following/user/followers/' + uno, header())
        .then(res => {
            this.users = res.data.data.user_list;
            this.requestMyFollowing();
        })
        .catch(err => console.error(err))
  },
  mounted() {
    window.addEventListener('resize', () => {
      this.windowWidth = window.innerWidth
    })
  },
  methods: {
      requestMyFollowing(){
        let myuno = this.$store.state.userInfo.uno;
        http.get('/following/user/followings/' + myuno, header())
        .then(res => {
            let temp_following_list = res.data.data.user_list;
            let no_arr = []
            for (let i=0; i<temp_following_list.length; i++){
                no_arr.push(temp_following_list[i].uno);
            }
            // 해당 유저가 나도 팔로중인 유저라면, isFollowing을 true로 만들어준다
            for (let i=0; i<this.users.length; i++){
                if (no_arr.indexOf(this.users[i].uno) >= 0){
                    this.users[i].isFollowing = true;
                }
                else{
                    this.users[i].isFollowing = false;
                }
            }
            this.loadComplete = true;
        })
        .catch(err => console.error(err))
    
      }
  }

};
</script>
<style scope>
.maxWidth{
  width: 520px;
  padding-top: 30px;
}
</style>
