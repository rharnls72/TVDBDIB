<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <div class="container mt-3">
        <div class="row p-0">
          <img class="col-3 p-0" :src="defaultProfile" alt="default-image">
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">340</h5>
            <p class="mb-0 text-center">좋아요</p>
          </div>
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">340</h5>
            <p class="mb-0 text-center">팔로워</p>
          </div>
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">340</h5>
            <p class="mb-0 text-center">팔로잉</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultProfile from '../../../assets/images/profile_default.png'

export default {
  name: 'MyPageInformation',
  data() {
    return {
      defaultProfile
    }
  },
}
</script>

<style>

</style>