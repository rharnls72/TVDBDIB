<template>
  <div>
    <ProgramHeader/>
    <div class="container mycontainer">
    <ProgramPageInformation/>
    </div>
  </div>
</template>

<script>
import ProgramHeader from '@/components/curation/program/ProgramHeader.vue'
import ProgramPageInformation from '@/components/curation/program/ProgramPageInformation.vue'

import CurationApi from '@/api/CurationApi.js'

export default {
  name: 'ProgramPage',
  components: {
    ProgramHeader,
    ProgramPageInformation,
  },
  created() {
  
    CurationApi.programDetail(
      this.$route.params.pno
      , res => {
        console.log(res.data.data);
        res.data.data.programDetail = JSON.parse(res.data.data.programDetail);
        res.data.data.episodeGroup = JSON.parse(res.data.data.episodeGroup);
        console.log(res.data.data);
      }
      , err => console.log(err)
    )

  }
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>