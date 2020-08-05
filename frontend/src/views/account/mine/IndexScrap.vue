<template>
  <div>
    <IndexScrapHeader />
    <div class="container mycontainer">
      <Scrapped />
    </div>
    <Footer />
  </div>
</template>

<script>
import IndexScrapHeader from '../../../components/account/mine/IndexScrapHeader.vue'
import Footer from '../../../components/common/custom/Footer.vue'
import Scrapped from '../../../components/account/mine/Scrapped.vue'

export default {
  name: 'IndexScrap',
  components: {
    IndexScrapHeader,
    Footer,
    Scrapped,
  },
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>