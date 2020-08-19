<template>
<div>
    <div class="searchArea">
      <div @click="searchIcon" class="searchIcon">
            <b-icon-search style="width: 20px; height: 20px;"></b-icon-search>
      </div>
      <select class="searchInput" name="" id="" style="width: 90px;" v-model="selected">
        <option value="program">프로그램</option>
        <option value="people">출연자</option>
      </select>
      <input class="searchInput" style="padding-left: 10px;" type="text" ref="searchInput" placeholder="검색" v-on:input='searchWord = $event.target.value' v-on:keyup='searchIcon()' autocomplete="off">
    </div>
</div>
</template>

<script>
import SearchApi from '@/api/SearchApi.js';

export default {
  name: 'UserSearch',
  data() {
    return {
      searchWord: this.word,
      selected: 'program',
    }
  },
  props:{
    word: String,
  },
  watch: {
    selected: function(){
      this.searchWord = '';
      this.$refs.searchInput.value = this.searchWord;
      this.searchIcon();
    }
  },
  mounted() {
    this.$refs.searchInput.value = this.searchWord;
  },
  methods: {
    searchIcon(){
      this.$store.state.searchWord = this.searchWord;
      this.$emit('update:word', this.searchWord)
      this.$emit('update:selected', this.selected)
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
    width: 70%;
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