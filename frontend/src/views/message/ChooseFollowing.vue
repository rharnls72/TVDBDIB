<template>
  <div>
    <ChooseFollowingHeader :choosed="choosed"/>
    <div class="mymessage">
      <b-input-group class="align-items-center m-2 mysearchbar">
        <div class="input-group-prepend">
          <div @click="searchIcon" class="input-group-text py-0" style="border: 0px; background-color: #eee;">
            <b-icon-search></b-icon-search>
          </div>
        </div>
        <vue-bootstrap-typeahead
          :data="users"
          v-model="word"
          size="sm"
          textVariant="red"
          :serializer="u => u.nick_name"
          :minMatchingChars='1'
          placeholder="type a username"
          @hit="selectedUser = $event"
          ref="searchInput"
          style="height: auto; width: 80%; border: 0px; background-color: #eee;"
        />
      </b-input-group>
    </div>
    <h1>여백</h1>
    <ChoosedItem :choosed="choosed"/>
    <div class="wrapB">
      <ChooseFollowingItem v-if='loadComplete' :followings="followings" :choosed="choosed"/>
    </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import Footer from '@/components/common/custom/Footer.vue';
import ChooseFollowingHeader from '@/components/message/ChooseFollowingHeader.vue';
import ChooseFollowingItem from '@/components/message/ChooseFollowingItem.vue';
import ChoosedItem from '@/components/message/ChoosedItem.vue';
import GetUserApi from "@/api/GetUserApi";
import MessageApi from "@/api/MessageApi";
import SearchApi from '@/api/SearchApi.js';
import VueBootstrapTypeahead from 'vue-bootstrap-typeahead';
import header from "@/api/header.js"
import http from '@/api/http-common.js';

export default {
  name: 'ChooseFollowing',
   components: {
    ChooseFollowingItem,
    Footer,
    ChooseFollowingHeader,
    VueBootstrapTypeahead,
    ChoosedItem
  },

  data() {
    return {
      loadComplete: false,
      choosed: [],
      followings: [],
      users_result: [],
      users: [],
      word: "",
      selectedUser: null
    }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });

    let uno = this.$store.state.userInfo.uno;

    // 팔로잉 유저 리스트 받아오기
    http.get('/following/user/followings/' + uno, header())
    .then(res => {
        this.followings = res.data.data.user_list;
        this.loadComplete = true;
    })
    .catch(err => console.error(err));

    SearchApi.getAllUser(
      'NoData'
      , res => {
        this.users = res.data.data;
      }
      , err => {
        console.log(err);
      }
    );
  },
  watch: {
    selectedUser(user){
      this.choosed.push(user);
      this.word = "";
    }
  },
  methods: {
    searchIcon(){
      SearchApi.getUserList(
        this.word,
        res => {
          this.users_result = res.data.data;
          setTimeout(()=>{}, 1000)
          this.toNextPage();
        },
        err => {
          console.log(err);
        }
      );
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
    },
  }

};
</script>

<style scoped>
  .mymessage {
    padding-top: 50px;
  }
  .mysearchbar {
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
</style>
