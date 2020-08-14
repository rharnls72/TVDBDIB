<template>
  <b-nav justified class="myheader d-flex justify-content-between align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>메시지</strong></p>
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
  name: 'ChatListHeader',
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

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>