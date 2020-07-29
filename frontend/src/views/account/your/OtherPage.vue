<template>
  <div>
    <OtherPageHeader :info="info"/>
    <h1>다른 프로필</h1>
    <OtherPageInformation :info="info" :followcnt="followcnt"/>
    <Footer />
  </div>
</template>

<script>
import OtherPageHeader from '../../../components/account/your/OtherPageHeader.vue'
import OtherPageInformation from '../../../components/account/your/OtherPageInformation.vue'
import Footer from '../../../components/common/custom/Footer.vue'
import http from "@/api/http-common.js";

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
    http.get('/account/followcnt', {
      params: {
        my_nick_name : this.$store.state.userInfo.nick_name,
        other_nick_name: this.$route.params.nick_name
      }
    })
      .then(res => {
        this.info = res.data.data.userInfo;
        this.followcnt = res.data.data.followCnt;
      })
      .catch(err => console.error(err))
  }
}
</script>

<style>

</style>