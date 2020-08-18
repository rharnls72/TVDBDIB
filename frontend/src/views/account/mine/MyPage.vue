<template>
  <div>
    <div v-if="show">
      <MyPageHeader :info="info"/>
      <div class="container mycontainer">
        <MyPageInformation :info="info" :followcnt="followcnt"/>
        <div v-for="feed in writtenFeeds" :key="feed.fno" class="col-12 m-0 p-0 row">
          <WrittenFeed :feed="feed" class="col-12 row"/>
          <hr class="row col-12">
        </div>
      </div>
      <Footer />
    </div>
    <LoadingItem v-else />
  </div>
</template>

<script>
import MyPageHeader from '@/components/account/mine/MyPageHeader.vue'
import MyPageInformation from '@/components/account/mine/MyPageInformation.vue'
import WrittenFeed from '@/components/account/mine/WrittenFeed.vue'
import Footer from '@/components/common/custom/Footer.vue'
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import AccountApi from "@/api/AccountApi"
import GetUserApi from "@/api/GetUserApi"
import FeedApi from "@/api/FeedApi.js"

export default {
  name: 'MyPage',
  data() {
    return {
      info: {},
      followcnt: {},
      writtenFeeds: [],
      requestCount: 1,

      show: false
    }
  },
  components: {
    MyPageHeader,
    MyPageInformation,
    WrittenFeed,
    Footer,
    LoadingItem
  },
  methods: {
    takeFeed() {
      let data = {
        num: this.requestCount,
        target_uno: this.info.uno
      };

      FeedApi.getFeedList(
        data
        , res => {
          this.writtenFeeds = res.list
          for (let i=0; i<res.list.length; i++) {
            this.writtenFeeds[i].content = JSON.parse(this.writtenFeeds[i].content)
            this.writtenFeeds[i].tag = JSON.parse(this.writtenFeeds[i].tag)
          }
          this.requestCount++
          setTimeout(()=>{}, 1000)
        }
        , err => {
          console.log(err)
        }
      )
    },
  },
  watch: {
    info() {
      this.takeFeed()
    }
  },
  mounted() {
    let data = {
        my_nick_name : this.$store.state.userInfo.nick_name,
        other_nick_name: this.$store.state.userInfo.nick_name
      };
    AccountApi.requestProfile(
      data,
      res => {
        this.info = res.info;
        this.followcnt = res.followcnt;

        FeedApi.getFeedList({
            num: 1,
            target_uno: this.$store.state.userInfo.uno
          }
          , res => {

            this.writtenFeeds = res.list
            for (let i=0; i<res.list.length; i++) {
              this.writtenFeeds[i].content = JSON.parse(this.writtenFeeds[i].content)
              this.writtenFeeds[i].tag = JSON.parse(this.writtenFeeds[i].tag)
            }
            this.requestCount++
            setTimeout(()=>{}, 1000)

            this.show = true;
          }
          , err => {
            console.log(err)
          }
        )

      },
      error => {
        this.$router.push({name:'Errors', query: {message: error.msg}})
      }
    );

  },
  created() {
    
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

  },
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>