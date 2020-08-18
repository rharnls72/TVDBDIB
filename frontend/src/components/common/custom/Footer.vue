<template>
    <b-nav justified class="myfooter pt-1">
      <!-- 1. 피드 -->
      <b-nav-item @click="changeState(1), moveTab('FeedMain')">
        <b-icon-people-fill v-if="currentTabNo == 1" class="text-dark"></b-icon-people-fill>
        <b-icon-people v-else class="text-secondary"></b-icon-people>
      </b-nav-item>
      <!-- 2. 검색 -> 아이콘을 굵게 만드는 법을 못 찾아서 전체적으로 색깔 차이 줌 -->
      <b-nav-item @click="changeState(2), moveTab('IndexSearch')">
        <b-icon-search v-if="currentTabNo == 2" class="text-dark"></b-icon-search>
        <b-icon-search v-else class="text-secondary"></b-icon-search>
      </b-nav-item>
      <!-- 3. 큐레이션(default) -->
      <b-nav-item @click="changeState(3), moveTab('IndexCuration')">
        <b-icon-house-door-fill v-if="currentTabNo == 3" class="text-dark"></b-icon-house-door-fill>
        <b-icon-house-door v-else class="text-secondary"></b-icon-house-door>
      </b-nav-item>
      <!-- 4. 알림 -->
      <b-nav-item @click="changeState(4), moveTab('Alert')">
        <b-icon-bell-fill v-if="currentTabNo == 4" class="text-dark"></b-icon-bell-fill>
        <b-icon-bell v-else class="text-secondary"></b-icon-bell>
      </b-nav-item>
      <!-- 5. 내 프로필 -->
      <b-nav-item @click="changeState(5), moveTab('MyPage')">
        <b-icon-person-circle v-if="currentTabNo == 5" class="text-dark"></b-icon-person-circle>
        <b-icon-person v-else class="text-secondary"></b-icon-person>
      </b-nav-item>
    </b-nav>
</template>

<script>
export default {
  name: 'Footer',
  methods: {
    // 탭을 클릭하면 해당 탭을 활성화
    // 이동은 해결, but 새로고침하면 3으로 돌아감,, -> 추후에 해결!
    changeState(tabNumber) {
      sessionStorage.setItem('tab_num', tabNumber);
      this.$store.commit('changeState', tabNumber)
    },
    moveTab(name) {
      this.$router.push({name: name})
    },
  },
  computed: {
    currentTabNo() {
      return this.$store.state.tabState;
    }
  },
  created() {
    let tabNumber = sessionStorage.getItem('tab_num');
    if(!tabNumber) tabNumber = 3;
    this.$store.commit('changeState', tabNumber);
  },
}
</script>

<style>
  .myfooter {
    background-color: #D8BEFE;
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 50px;
  }
</style>