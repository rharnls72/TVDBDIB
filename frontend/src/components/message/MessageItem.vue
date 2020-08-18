<template>
  <div class="feed-item">
      <ul>
        <li v-for="(message) in messages" :key="message.mno">

          <div v-if="message.user.uno!=uno" style="clear:both;">
              <div style="margin-top: 10px; display:flex;">
                <div class="box">
                  <img v-if="message.user.profile_pic!=null" class="profile" :src="message.user.profile_pic">
                  <img v-else class="profile" :src="defaultProfile">
                </div>
                <div style="margin: auto 0;">{{message.user.nick_name}}</div>
              </div>
              <div style="display:flex;">
                  <div class="messageContent">{{message.content}}</div>
                  <div style="position: relative; padding-left:5px;">
                    <div style="position:absolute; bottom:0;">{{message.time.hour}}:{{message.time.minute}}</div>
                  </div>
              </div>
          </div>
          <div v-else style="clear:both;">
              <div style="margin-top: 10px; display:flex; float:right;">
                <div style="margin: auto 0;">{{message.user.nick_name}}</div>
                <div class="box">
                    <img v-if="message.user.profile_pic!=null" class="profile" :src="message.user.profile_pic">
                    <img v-else class="profile" :src="defaultProfile">
                </div>
            </div>
            <div style="clear:both;">
              <div style="display:flex; float:right;">
                <div style="position: relative; padding-left:40px; padding-right:45px;">
                  <div style="position:absolute; bottom:0;"> 
                      <span @click.stop.prevent="deleteMessage(message)" style="color: red;">삭제</span>
                      {{message.time.hour}}:{{message.time.minute.toString().padStart(2,'0')}}</div>
                  </div>
                    <!-- <div style="position:absolute; bottom:0; right : 10px;"> 
                  </div> -->
                  <div class="mineMessageContent">{{message.content}}</div>
              </div>
            </div>
          </div>
        </li>
      </ul>
  </div>
</template>
<script>
import defaultProfile from "@/assets/images/profile_default.png";
import MessageApi from "@/api/MessageApi";
export default {
  name: 'MessageItem',
  data: () => {
    return {
      defaultProfile,
      uno: -1,
    };
  },
  props: {
    messages: Array,
  },
  created() {
    this.uno = this.$store.state.userInfo.uno;
  },
  methods:{
    deleteMessage(message){
      // event.stopPropagation();
      MessageApi.deleteMessage( message.mno,
          res => {
            
          },
          error => {
            this.$router.push({name:'Errors', query: {message: error.msg}})
          }
      );
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
  }

</script>

<style scoped>
.box {
    width: 30px;
    height: 30px; 
    border-radius: 70%;
    overflow: hidden;
    margin-left: 10px;
    margin-right: 10px;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.messageContent{
  max-width: 1000px;
  margin-top: 5px;
  margin-left: 40px;
  padding: 10px;
  border-radius: 10px;
  border-top-left-radius: 0;
  background-color: #f8e8f2;
  word-break:break-all;
}
.mineMessageContent{
  max-width: 1000px;
  margin-top: 5px;
  margin-right: 40px;
  padding: 10px;
  border-radius: 10px;
  border-top-right-radius: 0;
  background-color: #D8BEFE;
  word-break:break-all;
  /* overflow-wrap: break-word; */
}

</style>
