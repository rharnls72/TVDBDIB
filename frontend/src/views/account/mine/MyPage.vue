<template>
  <div>
    <MyPageHeader :nickname="nickname"/>
    <h1>내 프로필</h1>
    <MyPageInformation />
    <Footer />
  </div>
</template>

<script>
import axios from 'axios'
import MyPageHeader from '../../../components/account/mine/MyPageHeader.vue'
import MyPageInformation from '../../../components/account/mine/MyPageInformation.vue'
import Footer from '../../../components/common/custom/Footer.vue'

export default {
  name: 'MyPage',
  data() {
    return {
      nickname: 'hani',
    }
  },
  components: {
    MyPageHeader,
    MyPageInformation,
    Footer,
  },
  methods: {
    // getNickname() {
    //   axios.get('http://localhost:9000/')
    //     .then(res => {

    //     })
    //     .catch(err => console.error(err))
    // },
  },
}
</script>

<style>

</style>