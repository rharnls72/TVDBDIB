<template>
  <div id="app" class="columns">
    <FollowingHeader />
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
    // 분명 isFollowing 속성을 추가해줬고 콘솔에서도 출력되는데 팔로우-언팔로우 버튼이 제대로 안 뜨는 현상
    // -> 두번째 요청 보내는 사이에 UserListItem이 렌더링돼서 그런 것이었음...
    // 두번째 요청까지 다 끝나기 전에는 props 넘겨주지 않게 해야 했다 
    loadComplete: false, // 그래서 넣은 속성
      users: [],
      my_followings: []
  }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    let uno = this.$route.params.uno;

    // 팔로잉 유저 리스트 받아오기
    http.get('/following/user/followings/' + uno)
    .then(res => {
        console.log(res);
        this.users = res.data.data.user_list;
        this.requestMyFollowing(uno);
    })
    .catch(err => console.error(err));
  },

  methods: {
      requestMyFollowing(uno){
        let myuno = this.$store.state.userInfo.uno;

        console.log(uno);
        console.log(myuno);

        // 다른 사람의 페이지인 경우: 내 팔로잉 리스트도 가져온다. (팔로/언팔 버튼 보여주기 위함)
        if (myuno != uno){
            http.get('/following/user/followings/' + myuno)
            .then(res => {
                console.log(res);
                let temp_following_list = res.data.data.user_list;
                let no_arr = []
                for (let i=0; i<temp_following_list.length; i++){
                    no_arr.push(temp_following_list[i].uno);
                }
                console.log(no_arr);
                this.my_followings = no_arr;
            })
            .catch(err => console.error(err))
            .finally(() => {
                for (let i=0; i<this.users.length; i++){
                    console.log(this.my_followings.indexOf(this.users[i].uno))
                    if (this.my_followings.indexOf(this.users[i].uno) >= 0){
                        this.users[i].isFollowing = true;
                    }
                    else{
                        this.users[i].isFollowing = false;
                    }
                    console.log(this.users[i].isFollowing);
                }
                this.loadComplete = true;
            })
        }
        // 내 페이지인 경우 = 페이지 로딩시 모든 유저가 팔로잉 상태임이 보장된다
        else {
            for (let i=0; i<this.users.length; i++){
                console.log("test");
                this.users[i].isFollowing = true;
            }
            this.loadComplete = true;
        }
      }
  }

};
</script>
