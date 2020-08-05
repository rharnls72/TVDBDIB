<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center">
      <p class="text-dark mb-0"><strong>프로필 편집</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
        <button @click="editprofile" v-show="is_valid.is_valid=='is-valid'">
      <b-icon-check2-circle class="text-dark"></b-icon-check2-circle>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
import AccountApi from '@/api/AccountApi';

export default {
  name: 'EditMyPageHeader',
  props: {
    info: Object,
    is_valid: Object
  },
  methods: {
    backpage(){
      if(JSON.stringify(this.info) != JSON.stringify(this.$store.state.userInfo)){
        if(confirm("저장되지 않은 변경 내용이 있습니다. \n 취소하시겠어요?"))
          this.$router.go(-1);
      }else{
        this.$router.go(-1);
      }
    },
    editprofile(){
      if(JSON.stringify(this.info) == JSON.stringify(this.$store.state.userInfo)){
        this.makeToast("변경사항이 없습니다.", "warning");
      }else if(this.is_valid.is_valid=='is-valid'){
        console.log(this.info.profile_pic);
        AccountApi.requestModifyProfile(
          this.info,
          res => {
            if(res.success){
              this.$store.commit('addUserInfo', res.user);
              localStorage.setItem('tvility', JSON.stringify(res.user));
              this.makeToast("변경되었습니다.", "primary");
              console.log(this.info.profile_pic);
            }else{
              this.makeToast("변경에 실패했습니다.", "danger")
            }
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
        );
      }
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
  },
}
</script>

<style>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>