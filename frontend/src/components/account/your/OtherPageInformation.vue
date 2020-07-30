<template>
  <div class="feed newsfeed myfeed">
    <div class="wrapB">
      <div class="container mt-3">
        <div class="row p-0">
          <img class="col-3 p-0 mb-3" :src="defaultProfile" alt="default-image">
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">{{followcnt.feed_cnt}}</h5>
            <p class="mb-0 text-center">게시물</p>
          </div>
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">{{followcnt.follower_cnt}}</h5>
            <p class="mb-0 text-center">팔로워</p>
          </div>
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">{{followcnt.following_cnt}}</h5>
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
      defaultProfile,
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
          this.$router.go(this.$router.currentRoute);
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
          this.$router.go(this.$router.currentRoute);
        },
        error => {
          this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
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
</style>