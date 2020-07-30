<template>
  <div class="pl-4 mt-2">
    <div class="d-flex justify-align-between align-items-center">
      <b-form-input type="text" class="m-0 rounded-pill" v-model="content" placeholder="내용 입력!!!"></b-form-input>
            <!-- 댓글 내용이 없으면 -->
      <b-icon v-if="!content" icon="plus-circle" class="text-right ml-2 text-light" font-scale="1.4"></b-icon>
      <!-- 댓글 내용이 있으면 -->
      <b-icon v-else icon="plus-circle" class="text-right ml-2 text-secondary" font-scale="1.4"></b-icon>
    </div>
    <div class="pl-2 my-1" v-for="r in replies" :key="r.id">
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