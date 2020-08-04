<template>
  <div>
    <MyPageHeader :info="info"/>
    <div class="container mycontainer">
      <MyPageInformation :info="info" :followcnt="followcnt"/>
      <MyPageTab :info="info"/>
    </div>
    <Footer />
  </div>
</template>

<script>
import MyPageHeader from '@/components/account/mine/MyPageHeader.vue'
import MyPageInformation from '@/components/account/mine/MyPageInformation.vue'
import MyPageTab from '@/components/account/mine/MyPageTab.vue'
import Footer from '@/components/common/custom/Footer.vue'
import AccountApi from "@/api/AccountApi";
import GetUserApi from "@/api/GetUserApi"

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
    MyPageTab,
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

  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>