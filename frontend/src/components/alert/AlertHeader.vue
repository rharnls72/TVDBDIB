<template>
  <div>
   <b-nav justified class="myheader py-1 d-flex justify-content-between">
    <b-nav-item>
      <!-- 메시지 -->
      <button>
      </button>
    </b-nav-item>
    <b-nav-item>
      <p class="text-dark mb-0"><strong>알림</strong></p>
    </b-nav-item>
    <b-nav-item>
      <!-- 메시지 -->
      <button>
        
      </button>
    </b-nav-item>
  </b-nav>
    <!-- <b-tabs class="mytabs" active-nav-item-class="font-weight-bold text-dark" content-class="mt-3" justified>
      <b-tab title="인기" title-link-class="text-secondary" active></b-tab>
      <b-tab title="계정" title-link-class="text-secondary"></b-tab>
    </b-tabs> -->

  </div>

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

<style scoped>
  .myheader {
    background-color: #D8BEFE;
    position: fixed;
    width: 100%;
    height: 50px;
    z-index: 1;
  }
</style>