<template>
  <div>
    <div v-if="show">
      <OtherPageHeader :info="info"/>
      <div class="container mycontainer">
        <OtherPageInformation :info="info" :followcnt="followcnt"/>
        <WrittenFeed v-for="feed in writtenFeeds" :key="feed.fno" :feed="feed" class="col-12 row" />
      </div>
      <Footer />
    </div>
    <LoadingItem v-else />
  </div>
</template>

<script>
import OtherPageHeader from '@/components/account/your/OtherPageHeader.vue'
import OtherPageInformation from '@/components/account/your/OtherPageInformation.vue'
import Footer from '@/components/common/custom/Footer.vue'
import WrittenFeed from "@/components/account/mine/WrittenFeed.vue"
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import AccountApi from "@/api/AccountApi";
import GetUserApi from "@/api/GetUserApi"
import FeedApi from "@/api/FeedApi.js"

export default {
  name: 'OtherPage',
  data() {
    return {
      info: {},
      followcnt: {},
      requestCount: 1,
      writtenFeeds: null,

      show: false
    }
  },
  components: {
    OtherPageHeader,
    OtherPageInformation,
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

      console.log('Other page, written feed data', data);

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

          this.show = true;
        }
        , err => {
          console.log(err)
        }
      )
    },

    takeOtherUser() {
      let data = {
        my_nick_name : this.$store.state.userInfo.nick_name,
        other_nick_name: this.$route.params.nick_name
      };
      if(data.my_nick_name == data.other_nick_name)
        this.$router.push({name: 'MyPage'});
      AccountApi.requestProfile(
        data,
        res => {
          this.info = res.info;
          this.followcnt = res.followcnt;

          // 그 사람이 쓴 피드 목록은 info 가 설정 된 후에 가져오기
          this.takeFeed()
        },
        error => {
          this.$router.push({name:'Errors', query: {message: error.msg}})
        }
      );
    }
  },
  mounted() {
    // this.takeFeed()
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    this.takeOtherUser()
  },

  
}
</script>

<style scoped>
  .mycontainer {
    padding-top: 70px;
    padding-bottom: 50px;
  }
</style>