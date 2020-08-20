<template>
  <div class="feed-item">
    <ul class="list-group">
        <li v-for="(following) in followings" v-bind:key="following.uno" 
          class="list-group-item d-flex align-items-center" type="button">

        <div class="box">
          <img v-if="following.profile_pic!=null" :src='following.profile_pic' class="profile" alt="profile">
          <img v-else :src='defaultProfile' class="profile" alt="profile">
        </div>
        {{following.nick_name}}
        <div class="btnGroup">
            <div class="inbtnGroup">
                <button @click.stop.prevent="chooseUser(following)" >
              <b-icon icon="plus-circle" scale="1.3"></b-icon>
              </button>
            </div>
          </div>
        </li>
      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
export default {
  name: 'ChooseFollowingItem',
  data: () => {
    return {
      defaultProfile
    };
  },
  props: {
    followings: Array,
    choosed: Array,
  },
  methods:{
    chooseUser(following){
      if(this.choosed.find(element => element.uno ==following.uno) == undefined){
         this.choosed.push(following);
      }else{
        this.makeToast("이미 추가되었습니다.", "danger");
      }

    },
    makeToast(message, variant){
        this.$bvToast.toast(message, {
          title: '알림',
          toaster: "b-toaster-bottom-center",
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
    padding-top: 0px;
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
.btnGroup{
  display: table; 
  height: 30px; 
  width: 30px;
  position: absolute;
  right: 20px;
}
.inbtnGroup{
  display: table-cell; 
  vertical-align: middle;
}
</style>
