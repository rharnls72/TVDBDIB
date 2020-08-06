<template>
  <div id="app" class="columns">
    
  </div>
</template>

<script>
import "@/components/css/user.scss";
import db from '@/api/firebaseInit';
export default {
  name: 'AlertTest',

  data() {
    return {
      tabIndex: 0,
      alerts: [],
      generals: [],
      follow_requests: []
  }
  },
  created(){
        // db.collection("alert").doc("2scBDBt3W8GBCcrREkVY")
        //   .onSnapshot(function(doc) {
        //       console.log("Current data: ", doc.data());
        //   });
    db.collection("alert").where("ano", "==", 1)
      .onSnapshot(function(querySnapshot) {
        var alerts = [];
        querySnapshot.forEach(function(doc) {
          alerts.push(doc.data());
        });
        console.log(alerts);
      });
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
