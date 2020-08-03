<template>
  <div>
    <!-- b-tabs에 v-model: tabState를 주면 0부터로 동작 -->
    <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab @click="changeState(1)" title="인기" title-link-class="text-secondary" active></b-tab>
      <b-tab @click="changeState(2)" title="계정" title-link-class="text-secondary"></b-tab>
      <b-tab @click="changeState(3)" title="큐레이션" title-link-class="text-secondary"></b-tab>
      <b-tab @click="changeState(4)" title="피드" title-link-class="text-secondary"></b-tab>
    </b-tabs>

    <UserSearch v-if="tabState == 2" />
    <ProgramSearch v-if="tabState == 3" />

  </div>
</template>

<script>
import UserSearch from '@/components/search/UserSearch.vue';
import ProgramSearch from '@/components/search/ProgramSearch.vue';

export default {
  name: 'IndexSearchHeader',
  data() {
    return {
      tabState: 1
    }
  },
  methods: {
    changeState(tabNumber) {
      this.tabState = tabNumber
      console.log(this.tabState);
    },
  },
  components: {
    UserSearch,
    ProgramSearch
  }
}
</script>

<style>
  .mytabs {
    padding-top: 10px;
  }
</style>