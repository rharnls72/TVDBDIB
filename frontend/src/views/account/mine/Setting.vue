<template>
  <div>
    <SettingHeader />
    <SettingProperties />
  </div>
</template>

<script>
import SettingHeader from '../../../components/account/mine/SettingHeader.vue'
import SettingProperties from '../../../components/account/mine/SettingProperties.vue'

export default {
  name: 'Setting',
  components: {
    SettingHeader,
    SettingProperties,
  },
}
</script>

<style scoped>

</style>