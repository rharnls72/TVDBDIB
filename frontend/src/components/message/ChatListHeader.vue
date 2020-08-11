<template>
  <b-nav justified class="myheader py-1 d-flex justify-content-between">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>Message</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
      <button @click="chooseFollowing">
      <b-icon icon="plus-circle" class="text-dark"></b-icon>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'MessageHeader',
  methods: {
    backpage(){
      this.$router.go(-1);
    },
    chooseFollowing(){
      this.$router.push({name: 'ChooseFollowing'});
    }
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