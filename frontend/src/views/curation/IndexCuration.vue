<template>
  <div class="feed newsfeed">
    <IndexCurationHeader />
    <div class="wrapB">
      <div class="myfeed">
        <EpisodeItem v-for="curation in partCurations" :key="curation.key" :curation="curation"/>
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
import Footer from '../../components/common/custom/Footer.vue';
import IndexCurationHeader from '../../components/curation/IndexCurationHeader.vue'
import header from "@/api/header.js"
import axios from "axios"
import GetUserApi from "@/api/GetUserApi"

export default {
  name: 'IndexCuration',
  data() {
    return {
      curations: [],
      addItem: null,
      startPoint: 0,
      interval: 5,
      partCurations: [],
      loading_complete: false
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
      console.log(this.startPoint);
      let temp = []
      for (let i = this.startPoint; i < this.startPoint + this.interval; i++) {
        this.curations[i].key = this.curations[i].pno * 10000 + this.curations[i].episode;
        temp.push(this.curations[i])
      }
      this.partCurations = this.partCurations.concat(temp);
      this.startPoint += this.interval;
      this.loading_complete = true;
    },
    makeTotalCuations() {
      axios.get('http://localhost:9000/episode/following/1', header())
      .then(res => {
        console.log(res);
        this.curations = res.data.data
        console.log(this.curations)
        this.makeCurations()
      })
      .catch(err => console.error(err))
    },
    // 무한 스크롤 기능 구현
    infiniteHandler($state) {
      setTimeout(() => {
        if (this.loading_complete){
          this.makeCurations()
        }
        $state.loaded();
      }, 300);
    },
  },
  // 1. 데이터 모두 다 받아오기
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    axios.get('http://localhost:9000/episode/following/1', header())
      .then(res => {
        console.log(res);
        this.curations = res.data.data
        console.log(this.curations)
        this.makeCurations()
      })
      .catch(err => console.error(err))
  },
/*
  mounted(){
    this.loading_complete = true;
  }
*/
};
</script>
<style>
  .myfeed {
    padding-top: 70px;
  }
</style>