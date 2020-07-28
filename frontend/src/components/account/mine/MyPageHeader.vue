<template>
  <b-nav justified class="myheader py-1">
    <b-nav-item>
      <b-icon-blank></b-icon-blank>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>{{ nickname }}</strong></p>
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
                <b-nav-item href="#link-1" @click="hide">
                  <b-icon-gear-wide class="text-dark d-inline"></b-icon-gear-wide>
                  <b-icon-blank class="h5"></b-icon-blank>
                  <p class="text-dark d-inline m-0">설정</p>
                </b-nav-item>
                <b-nav-item href="#link-2" @click="hide">
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
    nickname: String,
  },
  methods: {
    // 피드 작성 페이지로 이동
    createFeed() {
      this.$router.push({name: 'CreateFeed'})
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