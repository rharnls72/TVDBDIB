<template>
  <div class="myloading d-flex justify-content-center align-items-center">
    <div>
      <h2 class="text-center">      
        <b-iconstack class="mr-1" animation="cylon">
          <b-icon-chevron-down stacked class="myicon1" shift-v="8.5"></b-icon-chevron-down>
          <b-icon-tv stacked class="myicon1"></b-icon-tv>
        </b-iconstack>
        <b-iconstack class="mr-1" animation="cylon">
          <b-icon-chevron-down stacked class="myicon2" shift-v="8.5"></b-icon-chevron-down>
          <b-icon-tv stacked class="myicon2"></b-icon-tv>
        </b-iconstack>
        <b-iconstack class="mr-1" animation="cylon">
          <b-icon-chevron-down stacked class="myicon3" shift-v="8.5"></b-icon-chevron-down>
          <b-icon-tv stacked class="myicon3"></b-icon-tv>
        </b-iconstack>
        <b-iconstack class="mr-1" animation="cylon">
          <b-icon-chevron-down stacked class="myicon4" shift-v="8.5"></b-icon-chevron-down>
          <b-icon-tv stacked class="myicon4"></b-icon-tv>
        </b-iconstack>
        <b-iconstack class="mr-1" animation="cylon">
          <b-icon-chevron-down stacked class="myicon5" shift-v="8.5"></b-icon-chevron-down>
          <b-icon-tv stacked class="myicon5"></b-icon-tv>
        </b-iconstack>
      </h2>
      <h5 class="text-center">잠시만 기다려 주세요!</h5>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoadingItem',
}
</script>

<style scoped>
  .myloading {
    padding-top: 70px;
    padding-bottom: 50px;
    height: 100%;
  }
  .myicon1 {
    color: #ff00e1;
  }
  .myicon2 {
    color: #da00ff;
  }
  .myicon3 {
    color: #8000ff;
  }
  .myicon4 {
    color: #2500ff;
  }
  .myicon5 {
    color: #008fff;
  }
</style>