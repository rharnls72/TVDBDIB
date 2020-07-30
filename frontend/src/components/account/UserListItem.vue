<template>
  <div class="feed-item">
      <ul> <!-- 받아온 데이터로 반복 돌리자 -->
            <!-- done == 'Y' 일때만 done 이라는 클래스를 지정 -->
          <li v-for="(user) in users" v-bind:key="user.uno" 
          class="shadow" type="button">

        <div class="form-group row" style="width: 100%;">
          <!-- 이미지 바인딩 어떻게...? 잘 안된다 -->
            <img class="my-auto col-3 col-sm-3" src="@/assets/images/profile_default.png" @click="movePage(user)">
            <span class="user-content my-auto col-6 col-sm-6">
              {{user.nick_name}}
            </span>
            <span class="float-right my-auto col-3 col-sm-3 removeBtn" type="button">
                <button @click="unfollow(user)" class="float-right btn btn-danger btn-sm" v-if="user.isFollowing">언팔로우</button>
                <button @click="follow(user)" class="float-right btn btn-primary btn-sm" v-if="!user.isFollowing">팔로우</button>
            </span>
        </div>
        </li>

      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
import http from '@/api/http-common.js';
//import commentcss from "@/components/css/feed/comment-list.scss";

export default {
  name: 'userItem',
  data: () => {
    return {
      defaultProfile
    };
  },
  props: {
    users: Array
  },
  methods:{
    movePage(user){
        this.$router.push('/profile/' + user.uno);
    },

    unfollow(user){
        http.post('/following/user/cancel', {
            follower: this.$store.state.userInfo.uno,
            following: user.uno
        })
        .then(res => {
          this.makeToast("언팔로우를 완료했습니다.", "primary");
        })
        .catch(err => this.makeToast(err, "danger"))
    },

    follow(user){
        http.post('/following/user/add', {
            follower: this.$store.state.userInfo.uno,
            following: user.uno
        })
        .then(res => {
          this.makeToast("팔로우에 성공했습니다.", "primary");
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
  list-style-type: none;
  padding-left: 0px;
  margin-top: 0%;
  text-align: left;
}
li {
  display: flex;
  min-height: 30px;
  height: 70px;
  line-height: 20px;
  margin: 0.5rem 0;
  padding: 0 0.5rem;
  background: white;
  border-radius: 3px;
}
.checkBtn {
  line-height: 45px;
  color: #62acde;
  margin-right: 5px;
}
.removeBtn {
  color: blue;
  padding: 0;
}

.list-item {
  display: inline-block;
  margin-right: 5px;
}
.list-move {
  transition: transform 1s;
}
.list-enter-active,
.list-leave-active {
  transition: all 1s;
}
.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
.done{
  background-color: lightgoldenrodyellow;
}

img{
    margin-right: 0%;
    vertical-align: middle;
}
.btn{
  height: 5%;
  width: 90%;
}

.user-content{
  padding: 0;
}

p{
  margin: 5%;
}

</style>
