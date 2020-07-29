<template>
  <div id="app" class="columns">
    <AlertHeader />
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

export default {
  name: 'AlertTest',
   components: {
    AlertItem,
    Footer,
    AlertHeader,
  },

  data() {
    return {
      alerts:[]
  }
  },

  created(){
      http.get('/alert/list/2')
        .then(res => {
          console.log(res);
          this.alerts = res.data.data;
        })
        .catch(err => console.error(err))
  },

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
