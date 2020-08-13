<template>
  <div>
    <MyPageHeader :info="info"/>
    <div class="container mycontainer">
      <MyPageInformation :info="info" :followcnt="followcnt"/>
      <WrittenFeed v-for="feed in writtenFeeds" :key="feed.fno" :feed="feed" class="col-12 row"/>
    </div>
    <Footer />
  </div>
</template>

<script>
import MyPageHeader from '@/components/account/mine/MyPageHeader.vue'
import MyPageInformation from '@/components/account/mine/MyPageInformation.vue'
import WrittenFeed from '@/components/account/mine/WrittenFeed.vue'
import Footer from '@/components/common/custom/Footer.vue'

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
    }
  },
  components: {
    MyPageHeader,
    MyPageInformation,
    WrittenFeed,
    Footer,
  },
  methods: {
    takeFeed() {
      let data = {
        num: this.requestCount,
        target_uno: this.info.uno
      };

      console.log(this.info)
      console.log(data)

      FeedApi.getFeedList(
        data
        , res => {
          console.log(111, res);

          this.writtenFeeds = res.list
          for (let i=0; i<res.list.length; i++) {
            this.writtenFeeds[i].content = JSON.parse(this.writtenFeeds[i].content)
            this.writtenFeeds[i].tag = JSON.parse(this.writtenFeeds[i].tag)
          }
          this.requestCount++
          console.log(this.writtenFeeds)
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
        console.log(this.info)
        this.followcnt = res.followcnt;

        FeedApi.getFeedList({
            num: 1,
            target_uno: this.$store.state.userInfo.uno
          }
          , res => {
            console.log(111, res);

            this.writtenFeeds = res.list
            for (let i=0; i<res.list.length; i++) {
              this.writtenFeeds[i].content = JSON.parse(this.writtenFeeds[i].content)
              this.writtenFeeds[i].tag = JSON.parse(this.writtenFeeds[i].tag)
            }
            this.requestCount++
            console.log(this.writtenFeeds)
            setTimeout(()=>{}, 1000)
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