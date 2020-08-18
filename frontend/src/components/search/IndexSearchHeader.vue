<template>
  <div>
    <!-- b-tabs에 v-model: tabState를 주면 0부터로 동작 -->
    <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab @click="changeState(1)" title="인기" title-link-class="text-secondary" active ref='searchTab1'></b-tab>
      <b-tab @click="changeState(2)" title="계정" title-link-class="text-secondary" ref='searchTab2'></b-tab>
      <b-tab @click="changeState(3)" title="큐레이션" title-link-class="text-secondary" ref='searchTab3'></b-tab>
      <b-tab @click="changeState(4)" title="피드" title-link-class="text-secondary" ref='searchTab4'></b-tab>
    </b-tabs>

    <!-- 선택한 탭번호에 따라 하위 컴포넌트 출력 -->
    <ProgramRecommend v-if="tabState == 1" />
    <UserSearch v-if="tabState == 2" />
    <ProgramSearch v-if="tabState == 3" />
    <FeedSearch v-if="tabState == 4" />

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
    padding-top: 10px;
    width: 100%;
  }
</style>