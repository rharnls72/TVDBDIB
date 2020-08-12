<template>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <b-icon @click="moveMain" icon="chevron-left" font-scale="1.5"></b-icon>
    <b-icon @click="submitArticle" icon="check-square" font-scale="1.4"></b-icon>
  </div>
</template>

<script>
export default {
  name: 'CreateFeedHeader',
  methods: {
    moveMain() {
      this.$router.go(-1)
    },
    submitArticle() {
      this.$emit('submitArticle')
    }
  }
}
</script>

<style>

</style>