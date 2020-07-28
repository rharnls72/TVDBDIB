<template>
    <b-nav justified class="myfooter py-1">
      <!-- 피드 -->
      <b-nav-item @click="changeState(1), moveTab('FeedMain')">
        <b-icon-people-fill v-if="tabState == 1" class="text-dark"></b-icon-people-fill>
        <b-icon-people v-else class="text-secondary"></b-icon-people>
      </b-nav-item>
      <!-- 검색 -> 아이콘을 굵게 만드는 법을 못 찾아서 전체적으로 색깔 차이 줌 -->
      <!-- 추후에 라우팅 -->
      <b-nav-item @click="changeState(2), moveTab('IndexSearch')">
        <b-icon-search v-if="tabState == 2" class="text-dark"></b-icon-search>
        <b-icon-search v-else class="text-secondary"></b-icon-search>
      </b-nav-item>
      <!-- 큐레이션(default) -->
      <b-nav-item @click="changeState(3), moveTab('IndexCuration')">
        <b-icon-house-door-fill v-if="tabState == 3" class="text-dark"></b-icon-house-door-fill>
        <b-icon-house-door v-else class="text-secondary"></b-icon-house-door>
      </b-nav-item>
      <!-- 알림 -->
      <!-- 1. 편지봉투 -> 편지봉투 높이가 달라지는 단점 -->
      <!-- <b-nav-item>
        <b-icon-envelope class="text-secondary"></b-icon-envelope>
        <b-icon-envelope-open-fill class="text-dark"></b-icon-envelope-open-fill>
      </b-nav-item> -->
      <!-- 2. 종 -->
      <b-nav-item @click="changeState(4), moveTab('Alert')">
        <b-icon-bell-fill v-if="tabState == 4" class="text-dark"></b-icon-bell-fill>
        <b-icon-bell v-else class="text-secondary"></b-icon-bell>
      </b-nav-item>
      <!-- 내 피드 -->
      <!-- 추후에 라우팅 -->
      <b-nav-item @click="changeState(5)">
        <b-icon-person-circle v-if="tabState == 5" class="text-dark"></b-icon-person-circle>
        <b-icon-person v-else class="text-secondary"></b-icon-person>
      </b-nav-item>
    </b-nav>
</template>

<script>
export default {
  name: 'Footer',
  data() {
    return {
      tabState: 3,
    }
  },
  methods: {
    // 탭을 클릭하면 해당 탭을 활성화
    changeState(tabNumber) {
      this.tabState = tabNumber
    },
    moveTab(name) {
      this.$router.push({name: name})
    },
  },
}
</script>

<style>
  .myfooter {
    background-color: #eee;
    position: fixed;
    bottom: -5px;
    width: 100%;
  }
</style>