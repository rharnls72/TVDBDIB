<template>
  <div id="app" class="columns">
    <AlertHeader />
      <div class="wrapB">
          <AlertItem :todolists="todolists"/>
      </div>
    <Footer />
  </div>
</template>

<script>
import "@/components/css/user.scss";
import axios from 'axios';
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
      todolists:[
  {
    no: 1,
    done: 'Y',
    write_date: "2020-05-20",
    end_date: "2020-05-30",
    content: "박하은님이 당신의 게시물에 좋아요를 표시했습니다."
  },
  { 
    no: 2,
    done: 'Y',
    write_date: "2020-05-22",
    end_date: "2020-05-31",
    content: "이유림님이 당신을 언급했습니다."
  },
    { 
    no: 3,
    done: 'Y',
    write_date: "2020-05-22",
    end_date: "2020-05-31",
    content: "서민성님이 머지 리퀘스트를 보냈습니다."
  },
    { 
    no: 4,
    done: 'N',
    write_date: "2020-05-22",
    end_date: "2020-05-31",
    content: "손초능님이 당신을 팔로우했습니다."
  }
]
  }
  },

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
