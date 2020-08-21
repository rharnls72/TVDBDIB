<template>
  <div class="row myfeedfooter">
    <div class="col-4 d-flex justify-content-center">
      <div class="link-btn d-flex justify-content-center align-items-center">
        <b-icon-pencil-square @click="turnArticle" font-scale="1.4"></b-icon-pencil-square>
      </div>
    </div>
    <div class="col-4 d-flex justify-content-center">
      <div class="link-btn d-flex justify-content-center align-items-center">
        <b-icon-stopwatch @click="turnCountdown" font-scale="1.4"></b-icon-stopwatch>
      </div>  
    </div>
    <div class="col-4 d-flex justify-content-center">
      <div class="link-btn d-flex justify-content-center align-items-center">
        <b-icon-card-checklist @click="turnVote" font-scale="1.4"></b-icon-card-checklist>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CreateFeedFooter',
  methods: {
    turnArticle() {this.$emit('changeArticle')},
    turnVote() {this.$emit('changeVote')},
    turnCountdown() {this.$emit('changeCountdown')},
  }
}
</script>

<style scoped>
  .link-btn {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    background-color: #D8BEFE;
  }
  .myfeedfooter {
    position: fixed;
    bottom: 20px;
  }
</style>