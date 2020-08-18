<template>
  <div>
    <ProgramRecommendHeader v-if="tabState == 1" />
      <UserSearchHeader v-if="tabState == 2" :word="word" @update:word="searchWord => word = searchWord"/>
      <UserSearchHeader v-if="tabState == 3" />
      <FeedSearchHeader v-if="tabState == 4" :word="word" @update:word="searchWord => word = searchWord"/>

      <!-- b-tabs에 v-model: tabState를 주면 0부터로 동작 -->
      <b-tabs :class="{mytabs: tabState == 1}" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
        <b-tab @click="changeState(1)" title="인기" title-link-class="text-secondary" active ref='searchTab1'></b-tab>
        <b-tab @click="changeState(2)" title="계정" title-link-class="text-secondary" ref='searchTab2'></b-tab>
        <b-tab @click="changeState(3)" title="큐레이션" title-link-class="text-secondary" ref='searchTab3'></b-tab>
        <b-tab @click="changeState(4)" title="피드" title-link-class="text-secondary" ref='searchTab4'></b-tab>
      </b-tabs>

      <!-- 선택한 탭번호에 따라 하위 컴포넌트 출력 -->
      <ProgramRecommend v-if="tabState == 1" />
      <UserSearch v-if="tabState == 2" :word="word"/>
      <ProgramSearch v-if="tabState == 3" />
      <FeedSearch v-if="tabState == 4" :word="word"/>
    <Footer />
  </div>
</template>

<script>
import Footer from '../../components/common/custom/Footer.vue';
import GetUserApi from "@/api/GetUserApi"
import ProgramRecommend from '@/components/search/ProgramRecommend.vue';
import UserSearch from '@/components/search/UserSearch.vue';
import ProgramSearch from '@/components/search/ProgramSearch.vue';
import FeedSearch from '@/components/search/FeedSearch.vue';
import ProgramRecommendHeader from '@/components/search/ProgramRecommendHeader.vue'
import UserSearchHeader from '@/components/search/UserSearchHeader.vue'
import FeedSearchHeader from '@/components/search/FeedSearchHeader.vue'
export default {
  name: 'IndexSearch',
  data() {
    return {
      tabState: 1,
      word: '',
    }
  },
  components: {
    Footer,
    ProgramRecommend,
    UserSearch,
    ProgramSearch,
    FeedSearch,
    ProgramRecommendHeader,
    UserSearchHeader,
    FeedSearchHeader
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
  },
  mounted() {
    this.word = this.$store.state.searchWord;
    switch(this.$store.state.searchTab){
      case 1:
        this.$refs.searchTab1.active = true
        break;
      case 2:
        this.$refs.searchTab2.active = true
        break;
      case 3:
        this.$refs.searchTab3.active = true
        break;
      case 4:
        this.$refs.searchTab4.active = true
    }
    this.changeState(this.$store.state.searchTab);
  },
  methods: {
    changeState(tabNumber) {
      this.word = '';
      this.tabState = tabNumber;
      this.$store.state.searchTab = tabNumber;
    },
  },
};
</script>
<style scoped>
.mytabs{
  padding-top: 60px;
}
 .searchArea{
   padding: 0;
    width: 100%;
    background-color: #D8BEFE;
    display: table;
  }
  .searchIcon{
    width: 60px;
    padding-left: 20px;
    vertical-align: middle;
    display: table-cell;
    border: 0px; 
    background-color: #D8BEFE;
  }
  .searchInput{
    padding: 0;
    border: 0px;
    color: #52565c;
    font-size: 17px;
    background-color: #D8BEFE;
    font-weight: medium;
    vertical-align: middle;
    display: table-cell;
  }
</style>