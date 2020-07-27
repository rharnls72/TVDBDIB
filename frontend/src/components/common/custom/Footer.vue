<template>
    <b-nav justified class="myfooter py-1">
      <!-- 피드 -->
      <b-nav-item>
        <b-icon-people class="text-secondary"></b-icon-people>
        <b-icon-people-fill class="text-dark"></b-icon-people-fill>
      </b-nav-item>
      <!-- 검색 -> 아이콘을 굵게 만드는 법을 못 찾아서 전체적으로 색깔 차이 줌 -->
      <b-nav-item>
        <b-icon-search class="text-secondary"></b-icon-search>
        <b-icon-search class="text-dark"></b-icon-search>
      </b-nav-item>
      <!-- 큐레이션(default) -->
      <b-nav-item>
        <b-icon-house-door class="text-secondary"></b-icon-house-door>
        <b-icon-house-door-fill class="text-dark"></b-icon-house-door-fill>
      </b-nav-item>
      <!-- 알림 -->
      <!-- 1. 편지봉투 -> 편지봉투 높이가 달라지는 단점 -->
      <!-- <b-nav-item>
        <b-icon-envelope class="text-secondary"></b-icon-envelope>
        <b-icon-envelope-open-fill class="text-dark"></b-icon-envelope-open-fill>
      </b-nav-item> -->
      <!-- 2. 종 -->
      <b-nav-item>
        <b-icon-bell class="text-secondary"></b-icon-bell>
        <b-icon-bell-fill class="text-dark"></b-icon-bell-fill>
      </b-nav-item>
      <!-- 내 피드 -->
      <b-nav-item>
        <b-icon-person class="text-secondary"></b-icon-person>
        <b-icon-person-circle class="text-dark"></b-icon-person-circle>
      </b-nav-item>
    </b-nav>
</template>

<script>
export default {
  name: 'Footer',

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