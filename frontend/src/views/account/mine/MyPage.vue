<template>
  <div>
    <MyPageHeader :info="info"/>
    <MyPageInformation :info="info" :followcnt="followcnt"/>
    <Footer />
  </div>
</template>

<script>
import MyPageHeader from '../../../components/account/mine/MyPageHeader.vue'
import MyPageInformation from '../../../components/account/mine/MyPageInformation.vue'
import Footer from '../../../components/common/custom/Footer.vue'
import http from "@/api/http-common.js";

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
    this.info = this.$store.state.userInfo;
    http.get('/account/followcnt?uno=' + this.info.uno)
      .then(res => {
        this.followcnt = res.data.data;
        console.log(this.followcnt);
      })
      .catch(err => console.error(err))
  }
}
</script>

<style>

</style>