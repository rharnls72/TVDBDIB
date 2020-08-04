<template>
  <div class="p-0">
    <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab title="내가 쓴" title-link-class="text-secondary" active>
        <WrittenFeed />
      </b-tab>
      <b-tab title="태그된" title-link-class="text-secondary">
        <TaggedFeed />
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import WrittenFeed from '../mine/WrittenFeed.vue'
import TaggedFeed from '../mine/TaggedFeed.vue'

export default {
  name: 'MyPageTab',
  components: {
    WrittenFeed,
    TaggedFeed,
  },
}
</script>

<style>

</style>