<template>
  <b-nav justified class="myheader py-1">
    <b-nav-item>

      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>Notification</strong></p>
    </b-nav-item>
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <!-- 메시지 -->
      <button>
        <b-icon-info-circle class="text-dark"></b-icon-info-circle> 3
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'IndexCuarationHeader',
  methods: {
    // 피드 작성 페이지로 이동
    createFeed() {
      this.$router.push({name: 'CreateFeed'})
    },
  },
}
</script>

<style>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    z-index: 1;
  }
</style>