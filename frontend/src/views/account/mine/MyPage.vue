<template>
  <div>
    <MyPageHeader :info="info"/>
    <h1>내 프로필</h1>
    <h1>{{ email }}</h1>
    <MyPageInformation />
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
      email: "",
      info: {},
    }
  },
  components: {
    MyPageHeader,
    MyPageInformation,
    Footer,
  },
  methods: {

  },
  // created() {
  //   this.email = this.$store.state.userInfo.email;
  //   console.log(this.email);
  //   http.get('/account/profileinfo?email=' + this.email)
  //     .then(res => {
  //       console.log(res)
  //     })
  //     .catch(err => console.error(err))
  // },
  mounted() {
    this.email = this.$store.state.userInfo.email;
    console.log(this.email);
    http.get('/account/profileinfo?email=' + this.email)
      .then(res => {
        console.log(res)
      })
      .catch(err => console.error(err))
  }
}
</script>

<style>

</style>