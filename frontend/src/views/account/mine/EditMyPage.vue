<template>
  <div>
    <EditMyPageHeader />
    <EditMyPageForm />
  </div>
</template>

<script>
import EditMyPageHeader from '../../../components/account/mine/EditMyPageHeader.vue'
import EditMyPageForm from '../../../components/account/mine/EditMyPageForm.vue'

export default {
  name: 'EditMyPage',
  components: {
    EditMyPageHeader,
    EditMyPageForm,
  },
}
</script>

<style>

</style>