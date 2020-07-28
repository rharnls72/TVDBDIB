<template>
  <b-nav justified class="myheader py-1">
    <b-input-group @click="searchicon">
      <div class="input-group-prepend">
        <div class="input-group-text" style="border:0px; background-color: #eee;">
          <b-icon icon="search"></b-icon>
        </div>
      </div>
      <b-form-input type="search" placeholder="검색" ref="searchInput" v-model="word" style="border:0px; background-color: #eee;"></b-form-input>
    </b-input-group>
  </b-nav>
</template>

<script>
export default {
  name: 'IndexCuarationHeader',
  data() {
    return {
      tabState: 3,
      word: "",
    }
  },
  methods: {
    searchicon(){
      this.$refs.searchInput.focus();
    },
    // 탭을 클릭하면 해당 탭을 활성화
    changeState(tabNumber) {
      this.tabState = tabNumber
    },
    moveTab(name) {
      this.$router.push({name: name})
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