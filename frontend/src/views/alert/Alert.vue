<template>
  <div class="user">
    <div class="wrapC">
      <button class="btn-bottom" @click="calculate">calculate</button>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
export default {
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
  }
};
</script>