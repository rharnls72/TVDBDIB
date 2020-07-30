<template>
  <b-nav justified class="myheader py-1 d-flex justify-content-between">
    <b-nav-item>
      <!-- 메시지 -->
      <button>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>Followings</strong></p>
    </b-nav-item>
    <b-nav-item>
      <!-- 메시지 -->
      <button>
      </button>
    </b-nav-item>
  </b-nav>
</template>

<script>
export default {
  name: 'IndexCuarationHeader',
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