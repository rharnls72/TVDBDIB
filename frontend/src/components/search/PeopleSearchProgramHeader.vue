<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center">
      <p class="text-dark mb-0"><strong>출연작</strong></p>
    </b-nav-item>
  <b-nav-item class="d-flex justify-content-start">
    
    </b-nav-item>
  </b-nav>
</template>

<script>

export default {
  methods: {
    backpage(){
      this.$router.go(-1);
    },
  },
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 0;
  }
</style>