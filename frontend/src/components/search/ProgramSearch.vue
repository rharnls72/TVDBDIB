<template>
<div>
    <b-nav justified class="myheader" >
      <b-input-group class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div @click="searchIcon()" class="input-group-text py-0" style="margin-left: -10%; border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>

        <!-- 프로그램명 / 인명 검색 select -->
        <b-form-select v-model="selected" :options="options"
         style="height: 85%; width: 20%; border: 0px;"></b-form-select>

        <!-- 자동완성 기능 지원하는 검색 창 컴포넌트 -->
        <vue-bootstrap-typeahead
          :data="searchedList"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="p => p.name"
          :minMatchingChars='1'
          :maxMatches='20'
          placeholder="search..."
          @hit="selectedUser = $event"
          ref="searchInput"
          style="height: auto; width: 55%; border: 0px; background-color: #eee;"
        />
      </b-input-group>
    </b-nav>

    <ResultProgram v-if="current_option == 1" :programs="result"/>
    <ResultPeople v-if="current_option == 2" :peoples="result"/>

    <!-- v-if를 넣어서 처음 페이지 띄웠을 때 (검색버튼 안 눌렀을 때) 는 동작 안하게 함 -->
    <infinite-loading v-if="loading_complete && !isEndPoint" @infinite="infiniteHandler"></infinite-loading>

</div>

</template>

<script>
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import SearchApi from '@/api/SearchApi.js';
import tmdbApi from '@/api/tmdbApi.js';
import axios from "axios";
import ResultProgram from "@/components/search/ProgramSearchResult.vue";
import ResultPeople from "@/components/search/PeopleSearchResult.vue";
import InfiniteLoading from 'vue-infinite-loading';

export default {

  data() {
    return {
      // selectbox 관련 속성
      selected: 'program',
      options: [
        { value: 'program', text: "프로그램명" },
        { value: 'people', text: "출연자"}
      ],
      current_option: 0,
      
      word: "",
      searchedList: [],
      result: [],
      total_pages: 0,
      current_page: 0,
      loading_complete: false,
      isEndPoint: false
      //search_history: [],
      //selectedUser: {},
    }
  },
  
  watch: {
    word(newWord) {
        if (this.selected == 'program')
          this.getProgramList(newWord);
        else if (this.selected == 'people')
          this.getPeopleList(newWord);
    },
  },
  
  methods: {
    infiniteHandler($state) {
      setTimeout(() => {

        if (this.current_page == this.total_pages){
          this.isEndPoint = true;
        }

        if (this.loading_complete == true && !this.isEndPoint){
          this.toNextPage();
        }

        $state.loaded();
      }, 1000);
    },

    toNextPage(){
      this.loading_complete = false;
      this.current_page++;
        axios.get(tmdbApi.BASE_URL + "search/tv?query=" + this.word + "&api_key=" + tmdbApi.API_KEY + "&page=" + this.current_page + "&language=ko")
            .then(res => {
                console.log(res);
                this.result = this.result.concat(res.data.results);
                this.loading_complete = true;
            })
            .catch(error => {
                console.log(error);
            });
    },

    getProgramList(newWord) {
        axios.get(tmdbApi.BASE_URL + "search/tv?query=" + newWord + "&api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
                console.log(res);
                this.searchedList = res.data.results;
            })
            .catch(error => {
                console.log(error);
            });
    },

    // 한글로 입력하면 분명 검색결과는 받아오는데 원문이 영어라서 그런지 자동완성이 안된다.
    getPeopleList(newWord){
        axios.get(tmdbApi.BASE_URL + "search/person?query=" + newWord + "&api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
                console.log(res);
                this.searchedList = res.data.results;
            })
            .catch(error => {
                console.log(error);
            });
    },

    searchIcon(){
        if (this.selected == 'program')
          this.getProgramResult(this.word);
        else if (this.selected == 'people')
          this.getProgramResultForPeople(this.word);
    },

    getProgramResult(word){
        axios.get(tmdbApi.BASE_URL + "search/tv?query=" + word + "&api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
                console.log(res);
                this.result = res.data.results;
                this.total_pages = res.data.total_pages;
                this.current_page = 1;
                this.current_option = 1;
                this.loading_complete = true;
            })
            .catch(error => {
                console.log(error);
            });
    },

    getProgramResultForPeople(word){
        axios.get(tmdbApi.BASE_URL + "search/person?query=" + word + "&api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
              // 1. 동명이인 어떻게 처리? - 다 띄워줄 수 있게 인터페이스를...?
              // 2. known_for가 왜 3개까지밖에 안뜨지?
                console.log(res);
                let promises = [];
                this.result = [];

                let peoples = res.data.results;
                // 검색결과로 나온 인물들에 대하여 detail 검색하기.
                for (let i=0; i<peoples.length; i++){
                  promises.push(this.detailSearchForPeople(peoples[i].name, peoples[i].profile_path, peoples[i].id));
                }
                
                // Promise.all: async랑 같이 써서 위의 작업이 다 끝나고 나서 실행되는 것을 보장하기 위함
                Promise.all(promises).then( () => {
                  console.log(this.result);
                  this.total_pages = res.data.total_pages;
                  this.current_page = 1;
                  this.current_option = 2;
                  this.loading_complete = true;
                })

            })
            .catch(error => {
                console.log(error);
            });
    },

    async detailSearchForPeople(name, pic, id){

        let item = {};
        axios.get(tmdbApi.BASE_URL + "person/" + id + "?api_key=" + tmdbApi.API_KEY + "&language=ko")
            .then(res => {
                  item.birthday = res.data.birthday;
                  if (res.data.also_known_as != null)
                    item.also_known_as = res.data.also_known_as[0];

                  axios.get(tmdbApi.BASE_URL + "person/" + id + "/tv_credits?api_key=" + tmdbApi.API_KEY + "&language=ko")
                  .then(res => {
                      console.log(res);
                      item.id = id;
                      item.people_name = name;
                      item.profile_path = pic;
                      item.programs = res.data.cast;
                      this.result.push(item);
                  })
                  .catch(error => {
                      console.log(error);
                  });
            })
            .catch(error => {
                console.log(error);
            });


    }



  },
  components: {
    //SearchApi,
    VueBootstrapTypeahead,
    ResultProgram,
    ResultPeople,
    InfiniteLoading,
  }
}
</script>
<style scoped>
  .myheader {
    background-color: #eee;
    width: 100%;
    height: 50px;
    z-index: 1;
    position: sticky;
  }
</style>