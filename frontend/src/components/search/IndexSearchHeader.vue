<template>
  <div>
    

    

  </div>
</template>

<script>
import ProgramRecommend from '@/components/search/ProgramRecommend.vue';
import UserSearch from '@/components/search/UserSearch.vue';
import ProgramSearch from '@/components/search/ProgramSearch.vue';
import FeedSearch from '@/components/search/FeedSearch.vue';

export default {
  name: 'IndexSearchHeader',
  data() {
    return {
      tabState: 1
    }
  },
  mounted() {
    console.log(this.$store.state.searchTab)
    switch(this.$store.state.searchTab){
      case 1:
        this.$refs.searchTab1.active = true
        break;
      case 2:
        this.$refs.searchTab2.active = true
        break;
      case 3:
        this.$refs.searchTab3.active = true
        break;
      case 4:
        this.$refs.searchTab4.active = true
    }
    this.changeState(this.$store.state.searchTab);
  },
  methods: {
    changeState(tabNumber) {
      this.tabState = tabNumber;
      this.$store.state.searchTab = tabNumber;
    },
  },
  components: {
    ProgramRecommend,
    UserSearch,
    ProgramSearch,
    FeedSearch
  }
}
</script>

<style scoped>
  .mytabs {
    padding-top: 50px;
    width: 100%;
  }
  
</style>