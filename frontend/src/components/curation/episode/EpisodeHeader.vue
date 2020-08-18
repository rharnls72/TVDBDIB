<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button>
        <b-icon-chevron-left @click="goToBack" class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-center">
      <p class="text-dark mb-0"><strong>{{episode.pname}}</strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'EpisodeHeader',
  props: {
    episode: Object,
  },
  methods: {
    goToBack() {
      this.$router.go(-1)
    }
  }
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