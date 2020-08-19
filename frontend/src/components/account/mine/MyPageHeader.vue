<template>
  <b-nav justified class="myheader align-items-center">
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>{{ info.nick_name }}</strong></p>
    </b-nav-item>
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <b-icon-blank></b-icon-blank>
      <!-- 설정, 스크랩한 게시물 보기 -->
      <b-icon-list v-b-toggle.sidebar-no-header class="text-dark"></b-icon-list>
      <b-sidebar id="sidebar-no-header" aria-labelledby="sidebar-no-header-title" no-header right shadow>
        <template v-slot:default="{ hide }">
          <div class="p-1">
            <nav class="mb-3">
              <b-nav vertical>
                <b-nav-item href="#link-1" @click="moveTab('Setting')">
                  <b-icon-gear-wide class="text-dark d-inline"></b-icon-gear-wide>
                  <b-icon-blank class="h5"></b-icon-blank>
                  <p class="text-dark d-inline m-0">설정</p>
                </b-nav-item>
                <b-nav-item href="#link-2" @click="moveTab('IndexScrap')">
                  <b-icon-bookmark class="text-dark d-inline"></b-icon-bookmark>
                  <b-icon-blank class="h5"></b-icon-blank>
                  <p class="text-dark d-inline m-0">스크랩</p>
                </b-nav-item>
              </b-nav>
            </nav>
          </div>
        </template>
      </b-sidebar>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'MyPageHeader',
  props: {
    info: Object,
  },
  methods: {
    moveTab(name) {
      this.$router.push({name: name})
    }
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