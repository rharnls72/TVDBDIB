<template>
  <div>
    <MyPageHeader :nickname="nickname"/>
  </div>
</template>

<script>
import axios from 'axios'
import MyPageHeader from '../../../components/account/mine/MyPageHeader.vue'

export default {
  name: 'MyPage',
  data() {
    return {
      nickname: 'hani',
    }
  },
  components: {
    MyPageHeader,
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