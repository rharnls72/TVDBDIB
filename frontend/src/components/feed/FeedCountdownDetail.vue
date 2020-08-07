<template>
  <div>
    <FeedCountdownItem/>
    <ReplyItem/>
  </div>
</template>

<script>
import feedCountdownItem from "@/components/feed/feedCountdownItem.vue"
import ReplyItem from "@/components/ReplyItem.vue"

export default {
  name: 'FeedCountdownDetail',
  component: {
    feedCountdownItem,
    ReplyItem,
  }

}
</script>

<style>

</style>