<template>
  <div>
    <AlertHeader />
    <div class="myalert">
      <!-- tabIndex라는 값을 v-model 이용해서 제어해서 탭 이동을 구현. 하위 b-tab에서 class만 바꾸는 걸로는 안 됐다 -->
    <b-tabs v-model="tabIndex" class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab @click="tabClick(1)" :title="'알림 (' + generals_size + ')'" title-link-class="text-secondary" active></b-tab>
      <b-tab @click="tabClick(2)" :title="'팔로우 요청 (' + requests_size + ')'" title-link-class="text-secondary"></b-tab>
    </b-tabs>
    </div>
      <div class="wrapB">
        <AlertItem :alerts="alerts"/>
      </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import http from '@/api/http-common.js';
import Footer from '@/components/common/custom/Footer.vue';
import AlertHeader from '@/components/alert/AlertHeader.vue';
import AlertItem from '@/components/alert/AlertItem.vue';
import GetUserApi from "@/api/GetUserApi"
import header from "@/api/header.js"
import db from '@/api/firebaseInit'
export default {
  name: 'AlertTest',
   components: {
    AlertItem,
    Footer,
    AlertHeader,
  },

  data() {
    return {
      tabIndex: 0,
      alerts: [],
      generals: [],
      follow_requests: []
  }
  },

  computed: {
    generals_size: function (){
      let new_num = 0;
      for (let i=0; i<this.$data.generals.length; i++){
        if (this.$data.generals[i].read == 0){
          new_num++; //
        }
      }
      return new_num;
    },
    requests_size: function(){
      return this.$data.follow_requests.length;
    }
  },

  created(){
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    
    let uno = this.$store.state.userInfo.uno;

    // 알림 가져오기.
    db.collection("alert").where("uno", "==", uno).orderBy("time", "desc")
      .onSnapshot(this.getAlerts);

      // 팔로우 요청 가져오기.
      db.collection("follow_request").where("uno", "==", uno).orderBy("time", "desc")
      .onSnapshot(this.getFollowings);

  },

  methods: {
    tabClick(option){
      if (option == 1){
        console.log("click " + option);
        this.tabIndex = 0;
        this.alerts = this.generals;
      }
      else{
        console.log("click " + option);
        this.tabIndex = 1; // AlertItem에서 이 함수 호출됐을 때 이 코드로 인해 탭 전환이 됨.
        this.alerts = this.follow_requests;
      }
    },
    getAlerts(querySnapshot) {
        // this.makeToast("알림이 왔습니다.", "primary");
        let temp = [];
        querySnapshot.forEach(function(doc) {
          temp.push(doc.data());
        });
        this.generals = temp;
        if(this.tabIndex == 0)
          this.alerts = this.generals;
    },
    getFollowings(querySnapshot){
      // this.makeToast("알림이 왔습니다.", "primary");
        let temp = [];
        querySnapshot.forEach(function(doc) {
          temp.push(doc.data());
        });
        this.follow_requests = temp;
        if(this.tabIndex == 1)
          this.alerts = this.follow_requests;
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
  }

/*
  mounted() {
    Notification.requestPermission().then(function(result) {
      Notification.permission = result;
      
    });
  },
  methods: {
    calculate() {
      this.notify();
    },
    notify() {
      if (Notification.permission != 'granted') {
        alert('notification is disabled');
      }
      else {
        var notification = new Notification('Notification title', {
          icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
          body: 'Notification text',
        });
 
        notification.onclick = function () {
          window.open('http://google.com');
        };
      }
    },
  }*/
};
</script>

<style scoped>
  .myalert {
    padding-top: 70px;
  }
</style>