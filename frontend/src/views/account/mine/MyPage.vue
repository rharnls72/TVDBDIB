<template>
  <div>
    <MyPageHeader :info="info"/>
    <h1>내 프로필</h1>
    <MyPageInformation :info="info" :followcnt="followcnt"/>
    <Footer />
  </div>
</template>

<script>
import MyPageHeader from '@/components/account/mine/MyPageHeader.vue'
import MyPageInformation from '@/components/account/mine/MyPageInformation.vue'
import Footer from '@/components/common/custom/Footer.vue'
import AccountApi from "@/api/AccountApi";

export default {
  name: 'MyPage',
  data() {
    return {
      info: {},
      followcnt: {},
    }
  },
  components: {
    MyPageHeader,
    MyPageInformation,
    Footer,
  },
  methods: {

  },
  mounted() {
    let data = {
        my_nick_name : this.$store.state.userInfo.nick_name,
        other_nick_name: this.$store.state.userInfo.nick_name
      };
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

  }
}
</script>

<style>

</style>