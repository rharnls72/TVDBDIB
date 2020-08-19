<template>
  <div>
    <div v-if="shareTag" class="feed-create">
      <!-- 헤더 -->
      <CreateHeader @submit="submitShare"/>

      <!-- 공유글 작성 -->
      <div class="create-feed-form row justify-content-center">
        <ShareForm v-if="editArticle!==null" :shareTag="shareTag" :article="editArticle" @CreateArticle="getFormData"/>
        <ShareForm v-else :shareTag="shareTag" @CreateArticle="getFormData"/>
      </div>

      <!-- 공유될 글 표기 -->
      <div class="share-item mx-5">
        <WrittenFeed v-if="!!article.ctype" :feed="article"/>
        <FeedShareEpisodeThumbnail class="program-thumbnail" v-else-if="!!article.eno" :curation="article"/>
        <FeedProgramThumbnail class="program-thumbnail" v-else :program="article"/>
      </div>
    </div>
    <LoadingItem v-else />
  </div>
</template>

<script>
import FeedApi from '@/api/FeedApi.js'
import Curation from '@/api/CurationApi.js'

import CreateHeader from '@/components/feed/feedComponents/CreateHeader.vue'
import ShareForm from '@/components/feed/feedComponents/ShareForm.vue'
import FeedProgramThumbnail from '@/components/feed/feedThumbnail/FeedProgramThumbnail.vue'
import WrittenFeed from '@/components/account/mine/WrittenFeed.vue'
import FeedShareEpisodeThumbnail from '@/components/feed/feedThumbnail/FeedShareEpisodeThumbnail.vue'
import LoadingItem from '@/components/common/custom/LoadingItem.vue'

import GetUserApi from "@/api/GetUserApi.js"

import $ from "jquery"

export default {
  name: 'CreateShare',
  data() {
    return {
      article: null,
      editArticle: null,
      submitData: null,
      formData: null,

      shareTag: false
    }
  },
  components: {
    CreateHeader,
    ShareForm,
    FeedProgramThumbnail,
    WrittenFeed,
    FeedShareEpisodeThumbnail,
    LoadingItem
  },
  methods: {
    moveMain() {
      console.log(1)
      this.$router.push('/feed/main')
    },
    getFormData(res) {
      this.formData = res;
    },
    makeSubmitData(res) {
      res.content.article = this.article
      res.content = JSON.stringify(res.content)
      res.tag = JSON.stringify(res.tags)
      this.submitData = res
    },

    submitShare() {
      if(this.formData == null) {
        this.makeToast("제목, 내용, 태그를 입력해주세요!", "danger");
        return;
      }

      let formD = this.formData;
      let submitState = true;
      if(!(formD.content.title && formD.content.title.length > 0)) {
        this.makeToast("제목을 입력해주세요.", "danger");
        submitState = false;
      }
      if(!(formD.content.content && formD.content.content.length > 0)) {
        this.makeToast("내용을 입력해주세요.", "danger");
        submitState = false;
      }
      if(!(formD.tags && formD.tags.length > 0)) {
        this.makeToast("적어도 하나 이상의 태그가 필요합니다.", "danger");
        submitState = false;
      }

      if(!submitState) {
        return;
      }

      this.makeSubmitData(this.formData);

      if (!this.$route.params.fno) {
        // Axios 요청

        console.log(this.submitData)

        FeedApi.createFeed(
          // 요청에 쓸 데이터 전달
          this.submitData
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log('createFeed Success: ' + res);
            this.$router.push({path: '/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log('createFeed Error: ' + err);
          } 
        )
      } else {

        this.submitData.fno = this.$route.params.fno

        FeedApi.updateFeed(
          // 요청에 쓸 데이터 전달
          this.submitData
          // 성공시 수행할 콜백 메서드
          , res => {
            console.log('createFeed Success: ' + res);
            this.$router.push({path: '/feed/main'})
          }
          // 실패시 수행할 콜백 메서드
          , err => {
            console.log('createFeed Error: ' + err);
          } 
        )
      }
    },
    makeToast(message, variant){
        this.$bvToast.toast(message, {
          title: '알림',
          toaster: "b-toaster-bottom-right",
          variant: variant,
          autoHideDelay: 3000,
          appendToast: false
        })
     }
  },
  created() {
    
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

    if (this.$route.params.type === "0") {
      FeedApi.feedDetail(
        this.$route.params.no
        , res => {
          this.article = res.feed
          this.article.content = JSON.parse(this.article.content)
          this.article.tag = JSON.parse(this.article.tag)

          let shareTag = "";
          if(this.article.content.title && this.article.content.title.length > 0) {
            shareTag = shareTag + this.article.content.title;
          }

          let type = this.article.ctype;
          if(type == 1 || type == 4) {
            if(this.article.content.content && this.article.content.content.length > 0) {
              shareTag = shareTag + " " + this.article.content.content;
            }
          } else if(type == 3) {
            let contents = this.article.content.content;
            let str = "";
            for(let item of contents) {
              if(item.text && item.text.length > 0) {
                str = str + " " + item.text;
              }
            }
            shareTag = shareTag + str;
          }

          this.shareTag = shareTag;

          console.log('Maked share tag string: ', shareTag);
        }
        , err => console.log(err)
      )
    } else if (this.$route.params.type === "1") {
      Curation.requestEpisodeDetail({
        pno: this.$route.params.pno,
        season: this.$route.params.season,
        episode: this.$route.params.no,
      }
      , res => {
        this.article = res.list

        let epi = this.article;
        let shareTag = "";
        if(epi.pname && epi.pname.length > 0) {
          shareTag = shareTag + " " + epi.pname;
        }
        if(epi.season_name && epi.season_name.length > 0) {
          shareTag = shareTag + " " + epi.season_name;
        }
        if(epi.episode_name && epi.episode_name.length > 0) {
          shareTag = shareTag + " " + epi.episode_name;
        }
        if(epi.summary && epi.summary.length > 0) {
          shareTag = shareTag + " " + epi.summary;
        }

        this.shareTag = shareTag;

        console.log('Maked share tag string: ', shareTag);
      }
      , err => console.log(err)
      )
    } else if (this.$route.params.type === "2") {
      Curation.programDetail(
        this.$route.params.no
        , res => {
          console.log(res)
          this.article = res.data.data
          this.article.programDetail = JSON.parse(this.article.programDetail)

          let shareTag = "";
          if(this.article.programDetail.name
            && this.article.programDetail.name.length > 0) {
            shareTag = shareTag + " " + this.article.programDetail.name;
          }
          if(this.article.programDetail.overview
            && this.article.programDetail.overview.length > 0) {
            shareTag = shareTag + " " + this.article.programDetail.overview;
          }

          if(shareTag.length == 0) {
            shareTag = "tvility";
          }
          this.shareTag = shareTag;
        }
        , err => console.log(err)
      )
    } else if (this.$route.params.fno !== null) {
      FeedApi.feedDetail(
        this.$route.params.fno
        , res => {
          console.log(res)
          res.feed.content = JSON.parse(res.feed.content)
          res.feed.tag = JSON.parse(res.feed.tag)
          this.article = res.feed.content.article
          this.editArticle = res.feed
        }
        , err => console.log(err)
      )
    }
    
    document.documentElement.scrollTop = 0
    
  }
}
</script>

<style scoped>
.share-item {
  border: none;
  border-radius: 2px;
  background-color: white;
}
.create-feed-form {
  width: 100%;
  padding-top: 15vh;
  padding-bottom: 5vh;
  bottom: 0;
  margin-left: 0;
  margin-right: 0;
}
.feed-create {
  background-color: #f8e8f2;
}
.program-thumbnail {
  padding: 5px;
}
</style>
