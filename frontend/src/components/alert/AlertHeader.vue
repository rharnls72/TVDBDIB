<template>
  <div>
    <b-nav justified class="myheader d-flex justify-content-between align-items-center">
      <b-nav-item>
      </b-nav-item>
      <b-nav-item>
        <p class="text-dark mb-0"><strong>알림</strong></p>
      </b-nav-item>
      <b-nav-item>
      </b-nav-item>
    </b-nav>
  </div>
</template>

<script>
export default {
  name: 'AlertHeader',
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    top: 0;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>