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
      <button @click="editprofile" >
        <!-- <button @click="editprofile" v-show="isValid"> -->
      <b-icon-check2-circle class="text-dark"></b-icon-check2-circle>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
import AccountApi from '@/api/AccountApi';

export default {
  name: 'EditMyPageHeader',
  data() {
    return {
      isValid : this.valid,
    }
  },
  props: {
    info: Object,
    valid: Boolean
  },
  mounted() {
    console.log(this.valid);
  },
  methods: {
    backpage(){
      if(confirm("저장되지 않은 변경 내용이 있습니다. \n 취소하시겠어요?"))
        this.$router.go(-1);
    },
    editprofile(){
      AccountApi.requestModifyProfile(
        this.info,
        res => {
          if(res.success){
            this.$store.commit('addUserInfo', res.user);
            localStorage.setItem('tvility', JSON.stringify(res.user));
            alert('변경되었습니다');
          }else{
            alert('변경에 실패했습니다');
          }
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
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>