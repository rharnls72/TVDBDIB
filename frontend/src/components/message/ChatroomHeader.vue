<template>
  <b-nav fill class="myheader d-flex justify-content-between align-items-center">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>{{room.mainUser.nick_name}}
        <span v-if="room.users.length>2">님 외 {{room.users.length-2}}명</span></strong>
      </p>
    </b-nav-item>
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'ChatroomHeader',
  props: {
    room: Object,
  },
  methods: {
    backpage(){
      // this.$router.push({name: 'ChatList'});
      this.$router.go(-1);
    },
  },
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    top: 0;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>