<template>
  <div>
    <OtherPageHeader :info="info"/>
    <OtherPageInformation :info="info" :followcnt="followcnt"/>
    <Footer />
  </div>
</template>

<script>
import OtherPageHeader from '@/components/account/your/OtherPageHeader.vue'
import OtherPageInformation from '@/components/account/your/OtherPageInformation.vue'
import Footer from '@/components/common/custom/Footer.vue'
import AccountApi from "@/api/AccountApi";
import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'OtherPage',
  data() {
    return {
      info: {},
      followcnt: {},
    }
  },
  components: {
    OtherPageHeader,
    OtherPageInformation,
    Footer,
  },
  methods: {

  },
  mounted() {
    let data = {
        my_nick_name : this.$store.state.userInfo.nick_name,
        other_nick_name: this.$route.params.nick_name
      };
      // if(data.my_nick_name == data.other_nick_name)
        // this.$router.push({name: 'MyPage'});
    AccountApi.requestProfile(
      data,
      res => {
        this.info = res.info;
        this.followcnt = res.followcnt;
      },
      error => {
        this.$router.push({name:'Errors', query: {message: error.msg}})
      }
    );
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
}
</script>

<style>

</style>