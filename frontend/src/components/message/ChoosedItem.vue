<template>
  <div class="feed-item">
    <span class="label" v-for="(choose) in choosed" v-bind:key="choose.uno">{{choose.nick_name}}
      <button @click="deleteChoose(choose)">
      <b-icon icon="x" class="text-dark"></b-icon>
      </button>
    </span>
  </div>
</template>
<script>
export default {
  name: 'ChooseFollowingItem',
  props: {
    choosed: Array,
  },
  methods:{
    deleteChoose(choose){
      var index = this.choosed.indexOf(choose);
      this.choosed.splice(index, 1);
    }
  }
  }

</script>
<style>
.label{
  background-color: blueviolet;
  padding: 10px;
  margin: 8px 5px;
  border-radius: 25%;
}
</style>

