<template>
  <b-nav justified class="myheader py-1">
    <b-nav-item>
      <!-- 새 글 작성 -->
      <button>
        <b-icon-plus-circle class="text-dark"></b-icon-plus-circle>
      </button>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>TVility</strong></p>
    </b-nav-item>
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <!-- 메시지 -->
      <button>
        <b-icon-chat-dots class="text-dark"></b-icon-chat-dots>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'IndexCuarationHeader',

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