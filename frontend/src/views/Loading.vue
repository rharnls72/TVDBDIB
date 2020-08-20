<template>
  <div>
    <LoginHeader />
    <LoadingItem />
  </div>
</template>

<script>
import LoginHeader from '@/components/user/custom/LoginHeader.vue'
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

export default {
  name: 'Loading',
  components: {
    LoginHeader,
    LoadingItem,
  }
}
</script>