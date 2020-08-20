<template>
  <div>
    <EditMyPageHeader :info="info" :is_valid="is_valid"/>
    <EditMyPageForm :info="info" :is_valid="is_valid"/>
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
      is_valid: {},
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
  mounted() {
    this.info = JSON.parse(JSON.stringify(this.$store.state.userInfo));
    this.is_valid = {is_valid:"is-invalid"};
  },
}
</script>