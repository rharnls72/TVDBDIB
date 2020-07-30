<template>
  <b-nav class="myheader d-flex justify-content-center align-items-center">
    <b-nav-item>
      <p class="text-dark mb-0"><strong>TVility</strong></p>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'LoginHeader',
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