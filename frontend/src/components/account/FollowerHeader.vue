<template>
  <b-nav justified class="myheader d-flex align-items-center">
    <b-nav-item>
      <!-- 메시지 -->
      <button>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>Followers</strong></p>
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
  name: 'FollowerHeader',
  methods: {
    // 피드 작성 페이지로 이동
    createFeed() {
      this.$router.push({name: 'CreateFeed'})
    },
  },
}
</script>

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>