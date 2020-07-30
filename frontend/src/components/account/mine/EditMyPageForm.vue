<template>
  <div class="feed newsfeed myfeed">
    <div class="wrapB">
      <div class="container mt-3">
        <div class="row p-0 justify-content-center">
          <img class="col-3 p-0 mb-0" :src="defaultProfile" alt="default-image">
        </div>
        <div class="row p-0 justify-content-center">
          <p class="mt-2 mb-0">사진 변경</p>
        </div>

        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="email" class="m-0">이메일:</label>
          </div>
          <div class="col-9 pl-0">
            <input id="email" type="text" :value="info.email" class="p-0 form-control" disabled/>
          </div>
        </div>
        <!-- 닉네임 가능 여부에 따라 조건문 걸어주기 -->
        <!-- 불가능한 닉네임이면 (중복 등) -->
        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="nickName" class="m-0">닉네임:</label>
          </div>
          <div class="col-9 pl-0">
            <input id="nickName" v-model="nick_name"  type="text" class="p-0 form-control" :class="isNick"/>
          </div>
        </div>
        
        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="textarea-no-resize" class="m-0">소개:</label>
          </div>
          <div class="col-9 pl-0">
            <textarea 
            id="textarea-no-resize" 
            v-model="bio"
            wrap="soft"
            rows="3"
            class="p-0 form-control"
            style="resize: none;"
            ></textarea>
            
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultProfile from '../../../assets/images/profile_default.png'
import AccountApi from '@/api/AccountApi'
export default {
  name: 'EditMyPageForm',
  data() {
    return {
      defaultProfile,
      nick_name: "",
      bio: "",
      isNick: "is-valid",
    }
  },
  props: {
    info: Object,
    valid: Boolean
  },
  watch: {
    nick_name: function(v){
      this.checkNick();
      this.syncData();
    },
    bio: function(){
      this.syncData();
    }
  },
  mounted() {
    this.nick_name = this.$store.state.userInfo.nick_name;
    this.bio = this.$store.state.userInfo.bio;
  },
  methods: {
    checkNick(){
      AccountApi.requestFindNick(
        {nick_name: this.$store.state.userInfo.nick_name,
          new_nick_name: this.nick_name},
        res => {
          if(res.isNick){
            this.isNick = "is-valid";
          }else{
            this.isNick = "is-invalid";
          }
        },
        error => {
          this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
    },
    syncData(){
      this.info.nick_name = this.nick_name;
      this.info.bio = this.bio;
      if(this.isNick == "is-valid" && this.bio.length >= 0){
        this.valid = true;
      }else{
        this.valid = false;
      }
      this.$emit('info', this.info);
      this.$emit('valid', this.valid);
    }
  },
}
</script>

<style>
  .myfeed {
    padding-top: 50px;
  }
</style>