<template>
  <div class="feed newsfeed pt-0">
    <div class="wrapB">
      <!-- <div class="container mt-3"> -->
        <div class="row p-0">
          <img class="col-3 p-0 mb-3">
          <div class="col-3 my-3">
          </div>
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">0</h5>
            <p class="mb-0 text-center">에피소드</p>
          </div>
          <div class="col-3 my-3">
            <h5 class="mb-0 text-center">0</h5>
            <p class="mb-0 text-center">팔로워</p>
          </div>
        </div>
        <p class="row mb-0 pb-3 introduce">여긴뭐지?</p>
        <p class="row p-0">
          <button class="col-12 mybutton p-0 text-dark">팔로우</button>
        </p>
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProgramPageInformation',
}
</script>

<style scoped>
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