<template>
  <div class="row">
    <div @click="turnArticle" class="col-4 d-flex justify-content-center">
      <div class="link-btn d-flex justify-content-center align-items-center">
        <b-icon icon="pencil-square" font-scale="1.4"></b-icon>
      </div>
    </div>
    <div @click="turnCountdown" class="col-4 d-flex justify-content-center">
      <div class="link-btn d-flex justify-content-center align-items-center">
        <b-icon icon="stopwatch" font-scale="1.4"></b-icon>
      </div>  
    </div>
    <div @click="turnVote" class="col-4 d-flex justify-content-center">
      <div class="link-btn d-flex justify-content-center align-items-center">
        <b-icon icon="card-checklist" font-scale="1.4"></b-icon>
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
  background-color: gray;
}
</style>