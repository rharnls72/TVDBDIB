<template>
  <div>
    <EditMyPageHeader :info="info" :valid="valid"/>
    <EditMyPageForm :info="info" :valid="valid"/>
  </div>
</template>

<script>
import EditMyPageHeader from '../../../components/account/mine/EditMyPageHeader.vue'
import EditMyPageForm from '../../../components/account/mine/EditMyPageForm.vue'
import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'EditMyPage',
  components: {
    EditMyPageHeader,
    EditMyPageForm,
  },
  data() {
    return {
      info: {},
      valid: false,
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
  mounted() {
    this.info = JSON.parse(JSON.stringify(this.$store.state.userInfo));
  },
}
</script>

<style>

</style>