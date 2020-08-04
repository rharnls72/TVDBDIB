<template>
    <div class="wrapB">
      <div class="myfeed" v-for="feed in feeds_result" :key="feed.fno">
        <feedArticleItem v-if="feed.ctype===1" :article="feed" :fno="feed.fno" @deleteItem="removeFeed"/>
        <feedCountdownItem v-if="feed.ctype===2" :article="feed" :fno="feed.fno" @deleteItem="removeFeed"/>
        <feedVoteItem v-if="feed.ctype===3" :article="feed" :fno="feed.fno" @deleteItem="removeFeed"/>
      </div>
    </div>
</template>

<script>
import feedArticleItem from "@/components/feed/feedArticleItem.vue";
import feedCountdownItem from "@/components/feed/feedCountdownItem.vue";
import feedVoteItem from "@/components/feed/feedVoteItem.vue";

export default {
  data() {
    return {

    }
  },

  components: { 
    feedArticleItem, 
    feedCountdownItem, 
    feedVoteItem
  },

  props: {
      feeds_result: Array
  },

  methods: {
    removeFeed(fno) {
        this.feeds_result = this.feeds_result.filter(res => res.fno!==fno);
    }
  }
}
</script>

<style scoped>

</style>