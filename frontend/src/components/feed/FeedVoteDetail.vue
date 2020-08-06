<template>
  <div>
    <feedVoteItem/>
    <ReplyItem/>
  </div>
</template>

<script>
import feedVoteItem from "@/components/feed/feedVoteItem.vue"
import ReplyItem from "@/components/ReplyItem.vue"

export default {
  name: 'feedVoteDetail',
  components: {
    feedVoteItem,
    ReplyItem,
  }
}
</script>

<style>

</style>