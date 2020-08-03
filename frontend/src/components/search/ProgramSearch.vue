<template>
    <b-nav justified class="myheader">
      <b-input-group @click="searchIcon" class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div class="input-group-text py-0" style="border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>

        <!-- 자동완성 기능 지원하는 검색 창 컴포넌트 -->
        <vue-bootstrap-typeahead
          :data="users"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="u => u.nick_name"
          :minMatchingChars='1'
          placeholder="type a program name"
          @hit="selectedUser = $event"
          ref="searchInput"
          style="height: auto; width: 80%; border: 0px; background-color: #eee;"
        />
      </b-input-group>
    </b-nav>
</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import axios from "axios";

export default {
  data() {
    return {
      word: "",
      users: [],
      search_history: [],
      selectedUser: {}
    }
  },
  
  watch: {
    word(newWord) {
        this.getProgramList(newWord);
    },
  },
  
  methods: {
    getProgramList(newWord) {
      


    }
  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead
  }
}
</script>
<style scoped>

</style>