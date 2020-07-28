<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <div class="container mt-3">
        <div class="row p-0">
          <img class="col-3 p-0 mb-3" :src="defaultProfile" alt="default-image">
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">340</h5>
            <p class="mb-0 text-center">게시물</p>
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
        <p class="row mb-0 pb-3 introduce">나 이런 사람이야~ 나 이런 사람이야~ 나 이런 사람이야~ 나 이런 사람이야~ 나 이런 사람이야~</p>
        <p class="row p-0">
          <button class="col-12 mybutton p-0 text-dark">프로필 수정</button>
        </p>
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
  .introduce {
    line-height: 1.5em;
  }
  .mybutton {
    height: auto;
    box-shadow: none;
    border: 1px solid lightgray;
    border-radius: 0.25rem;
  }
</style>