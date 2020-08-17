<template>
  <div>
    <IndexScrapHeader />
    <div class="mycontainer">
      <div class="p-0">
        <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
          <b-tab title="피드 찜" title-link-class="text-secondary" active>
            <div v-for="(feed, idx) in feedDib" :key="idx">
              <WrittenFeed :feed="feed"/>
              <hr class="row col-12">
            </div>
          </b-tab>
          <b-tab title="에피소드 찜" title-link-class="text-secondary">
            <div v-for="(episode, idx) in episodeDib" :key="idx">
              <DibEpisode :episode="episode"/>
              <hr class="row col-12">
            </div>
          </b-tab>
        </b-tabs>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import IndexScrapHeader from '@/components/account/mine/IndexScrapHeader.vue'
import WrittenFeed from '@/components/account/mine/WrittenFeed.vue'
import Footer from '@/components/common/custom/Footer.vue'
import DibEpisode from '@/components/account/mine/DibEpisode.vue'

import FeedApi from '@/api/FeedApi.js'
import CurationApi from '@/api/CurationApi.js'
import GetUserApi from '@/api/GetUserApi.js'

export default {
  name: 'IndexScrap',
  data() {
    return {
      feedDib: null,
      episodeDib: null,
    }
  },
  components: {
    IndexScrapHeader,
    WrittenFeed,
    Footer,
    DibEpisode,
  },
  created() {

    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

    FeedApi.getFeedList(
      {
        num: 1,
        is_dibs: 1,
      }
      , res => {
        for (let i=0; i<res.list.length; i++) {
          res.list[i].content = JSON.parse(res.list[i].content)
          res.list[i].tag = JSON.parse(res.list[i].tag)
        }
        this.feedDib = res.list
        console.log(this.feedDib)
      }
      , err => console.log(err)
    )

    CurationApi.readScrapedEpisode(
      { 
        num: 1,
        is_dibs: 1
      }
      , res=>{
        this.episodeDib = res.data.data
        console.log(this.episodeDib)
      }
      , err=>console.log(err)
    )
  }
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
    margin-bottom: 100px;
  }
</style>