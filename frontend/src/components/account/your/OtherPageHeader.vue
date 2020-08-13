<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>{{ info.nick_name }}</strong></p>
    </b-nav-item>
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'OtherPageHeader',
  props: {
    info: Object,
  },
  methods: {
    // 계정 설정 페이지로 이동
    // editAccount() {

    // },
  },
}
</script>

<style>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>