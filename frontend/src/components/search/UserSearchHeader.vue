<template>
<div>
    <div class="searchArea">
      <div @click="searchIcon" class="searchIcon">
            <b-icon-search style="width: 20px; height: 20px;"></b-icon-search>
      </div>
      <input class="searchInput" type="text" ref="searchInput" placeholder="친구 검색" v-on:input='searchWord = $event.target.value' v-on:keyup='searchIcon()' autocomplete="off">
    </div>
</div>
</template>

<script>
import SearchApi from '@/api/SearchApi.js';

export default {
  name: 'UserSearch',
  data() {
    return {
      searchWord: this.word
    }
  },
  props:{
    word: String,
  },
  mounted() {
    this.$refs.searchInput.value = this.searchWord;
  },
  methods: {
    searchIcon(){
      this.$store.state.searchWord = this.searchWord;
      this.$emit('update:word', this.searchWord)
    },
  },
}
</script>
<style scoped>
  .searchArea{
    padding: 0;
    width: 100%;
    background-color: #D8BEFE;
    display: table;
    margin-bottom: 10px;
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