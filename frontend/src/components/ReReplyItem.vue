<template>
  <div class="pl-4">
    <div class="d-flex justify-align-between align-items-center">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
      <b-icon icon="plus-circle" class="text-right ml-2" font-scale="1.4"></b-icon>
    </div>
    <div class="pl-2" v-for="r in replies" :key="r.id">
      {{r.user}} {{r.contents}}
    </div>
  </div>
</template>

<script>
export default {
  name: "ReReplyItem",
  props: {
    replies: Array,
  },
  data() {
    return {
      content: null,
    }
  }
}
</script>

<style>

</style>