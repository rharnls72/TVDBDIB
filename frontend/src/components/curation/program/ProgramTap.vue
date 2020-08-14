<template>
  <div class="p-0">
    <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab title="에피소드" title-link-class="text-secondary" active>
        <ProgramItem v-for="(episode, idx) in episodes" :key="idx" :episode="episode"/>
      </b-tab>
      <b-tab title="댓글" title-link-class="text-secondary">
        <ReplyItem @addReply="addReplyCount" @delReReply="delReReply" @delReply="res=>delReply(res)" :pno="program.programDetail.id"/>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import ProgramItem from "@/components/curation/program/ProgramItem.vue"
import ReplyItem from "@/components/ReplyItem.vue"

export default {
  name: "ProgramEpisodes",
  props: {
    episodes: Array,
    program: Object,
  },
  methods: {
    delReply(res) {
      this.$emit('delReply', res)
    },
    delReReply() {
      this.$emit('delReply', 1)
    },
    addReplyCount() {
      this.$emit('addReply')
    }
  },
  components: {
    ProgramItem,
    ReplyItem,
  },
  mounted() {
    console.log(this.episodes)
    // this.episodes.sort((a, b) => a.-b)
  }
}
</script>

<style>

</style>