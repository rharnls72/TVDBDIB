<template>
  <div class="feed newsfeed">
    <IndexCurationHeader />
    <div class="wrapB">
      <h1>큐레이션</h1>
      <div>
        <EpisodeItem v-for="curation in partCurations" :key="curation.pno" :curation="curation"/>
        <infinite-loading @infinite="infiniteHandler"></infinite-loading>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import EpisodeItem from "../../components/curation/episode/EpisodeItem.vue";
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
import Footer from '../../components/common/custom/Footer.vue';
import IndexCurationHeader from '../../components/curation/IndexCurationHeader.vue'

export default {
  name: 'IndexCuration',
  data() {
    return {
      curations: [],
      addItem: null,
      startPoint: 0,
      interval: 5,
      partCurations: [],
    }
  },
  props: ["keyword"],
  components: {
    EpisodeItem,
    InfiniteLoading,
    Footer,
    IndexCurationHeader,
  },
  methods: {
    // 2. 5개씩 끊어서 보여주기
    makeCurations() {
      let temp = []
      for (let i = this.startPoint; i < this.startPoint + this.interval; i++) {
        temp.push(this.curations[i])
      }
      this.partCurations = this.partCurations.concat(temp)
      console.log(this.partCurations)
    },
    // 무한 스크롤 기능 구현
    infiniteHandler($state) {
      setTimeout(() => {
        this.makeCurations()
        $state.loaded();
      }, 500);
      this.startPoint += this.interval
    },
  },
  // 1. 데이터 모두 다 받아오기
  created() {
    axios.get('http://localhost:9000/episode/following/1')
      .then(res => {
        this.curations = res.data.data
        console.log(this.curations)
      })
      .catch(err => console.error(err))
  },
};
</script>