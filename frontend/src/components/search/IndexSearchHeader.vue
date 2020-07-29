<template>
  <div>
    <b-nav justified class="myheader">
      <b-input-group @click="searchIcon" class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div class="input-group-text py-0" style="border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>
        <b-form-input
          type="search"
          placeholder="검색"
          ref="searchInput"
          v-model="word"
          class="p-0"
          style="height: auto; border: 0px; background-color: #eee;">
        </b-form-input>
      </b-input-group>
    </b-nav>
    <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab title="인기" title-link-class="text-secondary" active></b-tab>
      <b-tab title="계정" title-link-class="text-secondary"></b-tab>
      <b-tab title="큐레이션" title-link-class="text-secondary"></b-tab>
      <b-tab title="피드" title-link-class="text-secondary"></b-tab>
    </b-tabs>
  </div>
</template>

<script>
import SearchApi from '@/api/SearchApi.js';

export default {
  name: 'IndexSearchHeader',
  data() {
    return {
      tabState: 3,
      word: "",
      users: []
    }
  },
  watch: {
    word(newWord) {
      this.getUserList(newWord);
    }
  },
  methods: {
    searchIcon(){
      this.$refs.searchInput.focus();
    },
    // 탭을 클릭하면 해당 탭을 활성화
    changeState(tabNumber) {
      this.tabState = tabNumber
    },
    moveTab(name) {
      this.$router.push({name: name})
    },
    getUserList(newWord) {
      SearchApi.getUserList(
        newWord,
        res => {
          console.log(res.data.data);
          this.users = res.data.data;
        },
        err => {
          console.log(err);
        }
      );
    }
  },
}
</script>

<style>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
  .mysearchbar {
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
  .mytabs {
    padding-top: 50px;
  }
</style>