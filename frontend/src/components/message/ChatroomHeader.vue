<template>
  <b-nav justified class="myheader py-1 d-flex justify-content-between">
    <b-nav-item class="d-flex justify-content-start">
      <button @click="backpage">
      <b-icon-chevron-left class="text-dark"></b-icon-chevron-left>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>{{room.mainUser.nick_name}}
        <span v-if="room.users.length>2">님 외 {{room.users.length-2}}명</span></strong></p>
    </b-nav-item>
    <b-nav-item class="d-flex justify-content-end">
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
      this.$router.go(-1);
    },
  },
}
</script>

<style>
  .myheader {
    background-color: #eee;
    position: fixed;
    width: 100%;
    z-index: 1;
  }
</style>