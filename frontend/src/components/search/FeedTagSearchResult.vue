<template>
    <div class="feed-item">
      <ul class="list-group"> 
          <li v-for="(tag) in tags" v-bind:key="tag.tno" 
           class="list-group-item d-flex align-items-center" type="button" @click="goDetail(tag)">
          {{tag.name}}
          </li>      
    </ul>
</div>

</template>

<script>
export default {
  name: 'UserSearchResult',
  props: {
    tags: Array,
  },
  methods:{
      goDetail(tag){
        this.$emit('update:selectTag', tag.name)
      }
  }
}
</script>

<style scoped>
ul {
    padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}

</style>