<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item>
      <p class="m-0 text-dark">취소</p>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>프로필 편집</strong></p>
    </b-nav-item>
    <b-nav-item>
      <p class="m-0 text-dark">완료</p>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'EditMyPageHeader',

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