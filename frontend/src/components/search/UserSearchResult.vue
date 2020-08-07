<template>
    <div class="feed-item">
      <ul class="list-group"> 
          <li v-for="(user) in users_result" v-bind:key="user.uno" 
           class="list-group-item d-flex justify-content-between align-items-center" type="button" @click="goDetail(user.nick_name)">
            {{user.nick_name}}
        <div class="image-parent">
              <img :src='user.profile_pic' width="75rem" height="75rem" class="img-fluid" alt="profile">
          </div>
          </li>      
    </ul>
</div>

</template>

<script>

export default {
    name: 'UserSearchResult',
  data: () => {
    return {
    };
  },
  props: {
    users_result: Array
  },
  methods:{
      goDetail(name){
          console.log(name);
          this.$router.push("/profile/" + name);
      }
  }
}
</script>

<style scoped>
ul {
    padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}
</style>