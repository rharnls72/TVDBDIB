<template>
  <div class="feed newsfeed myfeed">
    <div class="wrapB">
      <div class="container mt-3">
        <b-list-group class="row p-0">
          <button @click="modifyPw()">
          <b-list-group-item class="px-3 py-2 d-flex align-items-center">
            <b-icon-hash class="text-dark d-inline mb-0 mr-3"></b-icon-hash>
            <p class="text-dark d-inline m-0">비밀번호 변경</p>
          </b-list-group-item>
          </button>
          <button @click="logout()">
          <b-list-group-item class="px-3 py-2 d-flex align-items-center">
            <b-icon-unlock class="text-dark d-inline mb-0 mr-3"></b-icon-unlock>
            <p class="text-dark d-inline m-0">로그아웃</p>
          </b-list-group-item>
          </button>
        </b-list-group>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SettingProperties',
  methods: {
    modifyPw(){
      this.$router.push({name:'ModifyPw'});
    },
    logout(){
      this.$store.commit('setAutoLogin', false);
      localStorage.removeItem('tvility');
      this.$router.push({name:'Login'})
    }
  },
}
</script>

<style>
  .myfeed {
    padding-top: 50px;
  }
</style>