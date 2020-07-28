<template>
  <div>
    <MyPageHeader :nickname="nickname"/>
    <h1>유저 정보</h1>
    <MyPageInformation />
  </div>
</template>

<script>
import axios from 'axios'
import MyPageHeader from '../../../components/account/mine/MyPageHeader.vue'
import MyPageInformation from '../../../components/account/mine/MyPageInformation.vue'

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