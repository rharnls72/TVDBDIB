<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <!-- 새 글 작성 -->
      <button>
        <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center">
      <p class="text-dark mb-0"><strong>{{programTitle}}</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'ProgramHeader',
  props: {
    programTitle: String,
  }
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