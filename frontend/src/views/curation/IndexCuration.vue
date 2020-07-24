<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>큐레이션</h1>
      <div>
        <EpisodeItem v-for="curation in curations" :key="curation.pno" :curation="curation"/>
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
    EpisodeItem,
    InfiniteLoading,
    Footer,
  },
  methods: {
    // 무한 스크롤 기능 구현 -> 더미 데이터 들어오면 주석 처리해 둔 axios 로직으로 바꿀 예정
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
            pno: i,
            name: `${i}. 프로그램 제목`,
            broadcaster: `${i}. 프로그램의 방송 주체`,
            genre: `${i}. 프로그램의 장르`,
            description: `${i}. 프로그램에 대한 정보입니다. 프로그램에 대한 정보입니다. 프로그램에 대한 정보입니다. 프로그램에 대한 정보입니다. 프로그램에 대한 정보입니다. 프로그램에 대한 정보입니다. 프로그램에 대한 정보입니다.`,
            broadcast_time: `${i}. 프로그램의 방송 시간`,
            thumbnail: `${i}. 프로그램의 썸네일 url`,
          })
        }
        this.curations = this.curations.concat(temp);
        $state.loaded();
      }, 1000);
    },
  },
  // 아직 더미 데이터가 없어서 만들어 놓은 데이터 모두 다 받아오는 방식
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