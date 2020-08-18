<template>
  <div>
    <div v-if="show">
      <div class="feed newsfeed">
        <IndexCurationHeader />
        <div class="wrapB">
          <div class="myfeed">
            <EpisodeItem v-for="curation in partCurations" :key="curation.key" :curation="curation"/>
            <infinite-loading v-if="!noCuration" @infinite="infiniteHandler"></infinite-loading>
            <div v-else style="text-align: center; margin-top: 50px;"> 팔로우 중인 프로그램이 없습니다<br/>
              좋아하는 프로그램을 찾으러 가볼까요?<br/>
            <button type="button" class="shadow moveSearch" @click="moveSearch">찾으러 가자!</button>
            </div>
          </div>
        </div>
        <Footer />
      </div>
    </div>
    <LoadingItem v-else />
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
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import GetUserApi from "@/api/GetUserApi"
import CurationApi from "@/api/CurationApi"

export default {
  name: 'IndexCuration',
  data() {
    return {
      curations: [],
      addItem: null,
      startPoint: 0,
      interval: 5,
      partCurations: [],
      loading_complete: false,
      noCuration: false,
      show: false,
    }
  },
  props: ["keyword"],
  components: {
    EpisodeItem,
    InfiniteLoading,
    Footer,
    IndexCurationHeader,
    LoadingItem,
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
      CurationApi.requestEpisode(
        res => {
            this.curations = res.list;
            this.makeCurations();
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
      );
    },
    // 무한 스크롤 기능 구현
    infiniteHandler($state) {
      setTimeout(() => {
        if (this.loading_complete && !this.noCuration){
          this.makeCurations()
        }
        $state.loaded();
      }, 300);
    },
    moveSearch(){
      this.$router.push({name:'IndexSearch'})
    }
  },
  // 1. 데이터 모두 다 받아오기
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    CurationApi.requestEpisode(
      res => {
        this.curations = res.list;
        if(this.curations.length>0){
          this.makeCurations();
        }else{
          this.noCuration = true;
          this.loading_complete = true;
        }
        this.show = !this.show
      },
      error => {
        this.$router.push({name:'Errors', query: {message: error.msg}})
      }
    );
  },
/*
  mounted(){
    this.loading_complete = true;
  }
*/
};
</script>
<style scoped>
  .myfeed {
    padding-top: 70px;
  }
  .moveSearch{
    margin-top: 30px;
    width: 150px;
    height: 40px;
    border-radius: 10px;
    background-color: #f8e8f2;
    color: rgb(84, 78, 88);
  }
</style>