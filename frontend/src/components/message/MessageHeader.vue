<template>
  <b-nav justified class="myheader py-1 d-flex justify-content-between">
    <b-nav-item>
      <p class="text-dark mb-0"><strong>Message</strong></p>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'MessageHeader',
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>