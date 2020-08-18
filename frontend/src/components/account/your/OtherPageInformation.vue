<template>
  <div class="feed newsfeed pt-0">
    <div class="wrapB">
      <div class="row p-0">
        <div class="box" style="background: #BDBDBD;">
              <img v-if="info.profile_pic != null" class="profile" :src="info.profile_pic" :alt="profile_img">
              <img v-else class="profile" :src="profile_img" :alt="profile_img">
          </div>
        <div class="col-3 my-3">
          <h5 class="mb-0 text-center">{{followcnt.feed_cnt}}</h5>
          <p class="mb-0 text-center">게시물</p>
        </div>
        <div class="col-3 my-3" @click="moveFollowerPage()">
          <h5 class="mb-0 text-center" v-html="followcnt.follower_cnt"></h5>
          <p class="mb-0 text-center">팔로워</p>
        </div>
        <div class="col-3 my-3" @click="moveFollowingPage()">
          <h5 class="mb-0 text-center" v-html="followcnt.following_cnt"></h5>
          <p class="mb-0 text-center">팔로잉</p>
        </div>
      </div>
      <p class="row mb-0 pb-3 introduce">{{info.bio}}</p>
      <p class="row p-0">
        <button v-if="followcnt.is_follow==0" @click="follow()" class="col-12 mybutton p-0 text-dark">팔로우</button>
        <button v-else @click="defollow()" class="col-12 mybutton p-0 text-dark">팔로우 취소</button>
      </p>
    </div>
  </div>
</template>

<script>
import defaultProfile from '@/assets/images/profile_default.png'
import AccountApi from "@/api/AccountApi";

export default {
  name: 'MyPageInformation',
  props: {
    info: Object,
    followcnt: Object,
  },
  data() {
    return {
      profile_img: defaultProfile,
    }
  },
  mounted() {
    if(this.info.profile_pic != null) {
      this.profile_img = this.info.profile_pic;
    }
  },
  methods: {
    follow(){
      let data = {
        follower : this.$store.state.userInfo.uno,
        following: this.info.uno
      };
      AccountApi.requestFollow(
        data,
        res => {
          this.followcnt.is_follow = 1;
          this.followcnt.follower_cnt++;
        },
        error => {
          this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
    },
    defollow(){
      let data = {
        follower : this.$store.state.userInfo.uno,
        following: this.info.uno
      };
      AccountApi.requestDeFollow(
        data,
        res => {
          this.followcnt.is_follow = 0;
           this.followcnt.follower_cnt--;
        },
        error => {
          this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
    },
    moveFollowerPage(){
      this.$router.push('/profile/followers/' + this.info.uno);
    },
    moveFollowingPage(){
      this.$router.push('/profile/followings/' + this.info.uno);
    }
  },
}
</script>

<style>
  .introduce {
    line-height: 1.5em;
  }
  .mybutton {
    height: auto;
    box-shadow: none;
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
  .myfeed {
    padding-top: 50px;
  }
  .box {
    width: 80px;
    height: 80px; 
    border-radius: 70%;
    overflow: hidden;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>