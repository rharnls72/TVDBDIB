<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center">
      <p class="text-dark mb-0"><strong>설정</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'SettingHeader',
}
</script>

<style>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>