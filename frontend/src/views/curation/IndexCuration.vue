<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>큐레이션</h1>
      <div>
        <CurationItem v-for="curation in curations" :key="curation.pno" :curation="curation"/>
        <infinite-loading @infinite="infiniteHandler"></infinite-loading>
      </div>
    </div>
    <div id="bottomSensor"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import CurationItem from "../../components/curation/CurationItem.vue";
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios'

export default {
  name: 'IndexCuration',
  data() {
    return {
      curations: [],
      addItem: null,
    }
  },
  props: ["keyword"],
  components: {
    CurationItem,
    InfiniteLoading,
    },
  methods: {
    infiniteHandler($state) {
      setTimeout(() => {
        const temp = [];
        for (let i = this.curations.length + 1; i <= this.curations.length + 1; i++) {
          // axios.get('http://localhost:9000/program/list')
          //   .then(res => {
          //     this.curations = this.curations.concat(res.data.data);
          //   })
          //   .catch(err => console.error(err))
          temp.push({
            pno: i
          })
        }
        this.curations = this.curations.concat(temp);
        $state.loaded();
      }, 1000);
      // setTimeout(() => {
      //   const temp = [];
      //   for (let i = this.curations.length + 1; i <= this.curations.length + 1; i++) {
      //     temp.push(i);
      //   }
      //   this.curations = this.curations.concat(temp);
      //   $state.loaded();
      // }, 1000);
    },
  },
  created() {
    axios.get('http://localhost:9000/program/list')
      .then(res => {
        this.curations = res.data.data
        console.log(this.curations)
      })
      .catch(err => console.error(err))
  }
};
</script>