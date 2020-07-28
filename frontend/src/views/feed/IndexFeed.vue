<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <router-link to="/user/modifyPw" class="btn--text">비밀번호변경</router-link>
      <feedArticleItem />
      <feedCountdownItem />
      <feedVoteItem />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import feedArticleItem from "@/components/feed/feedArticleItem.vue";
import feedCountdownItem from "@/components/feed/feedCountdownItem.vue";
import feedVoteItem from "@/components/feed/feedVoteItem.vue";

export default {
  props: ["keyword"],

  components: { feedArticleItem, feedCountdownItem, feedVoteItem }
};
</script>

