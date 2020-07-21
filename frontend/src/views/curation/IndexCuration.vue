<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>큐레이션</h1>
      <CurationItem />
      <CurationItem />
      <CurationItem />
      <CurationItem />
    </div>
    <div id="bottomSensor"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import CurationItem from "../../components/curation/CurationItem.vue";

export default {
  name: 'IndexCuration',
  props: ["keyword"],
  components: { CurationItem },
  methods: {
    addScrollWatcher: function () {
      const bottomSensor = document.querySelector('#bottomSensor')
      const watcher = scrollMonitor.create(bottomSensor)
      watcher.enterViewport(() => {
        setTimeout(() => {
          console.log('얍')
        }, 500)
      })
    },
  },
};
</script>