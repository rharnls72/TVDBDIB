<template>
  <div class="feed newsfeed myfeed">
    <div class="wrapB">
      <div class="container mt-3">
        <div class="row p-0 justify-content-center">
          <img class="col-3 p-0 mb-0" :src="defaultProfile" alt="default-image">
        </div>
        <div class="row p-0 justify-content-center">
          <p class="mt-2 mb-0">사진 변경</p>
        </div>
        <!-- 닉네임 가능 여부에 따라 조건문 걸어주기 -->
        <!-- 불가능한 닉네임이면 (중복 등) -->
        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="input-invalid" class="m-0">닉네임:</label>
          </div>
          <div class="col-9 pl-0">
            <b-form-input id="input-invalid" :state="false" placeholder=" InValid input" class="p-0"></b-form-input>
          </div>
        </div>
        <!-- 가능한 닉네임이면 -->
        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="input-valid" class="m-0">닉네임:</label>
          </div>
          <div class="col-9 pl-0">
            <b-form-input id="input-valid" :state="true" placeholder=" Valid input" class="p-0"></b-form-input>
          </div>
        </div>
        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="textarea-no-resize" class="m-0">소개:</label>
          </div>
          <div class="col-9 pl-0">
            <b-form-textarea
              id="textarea-no-resize"
              placeholder="Fixed height textarea"
              rows="3"
              no-resize
              class="p-0"
            ></b-form-textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import defaultProfile from '../../../assets/images/profile_default.png'

export default {
  name: 'EditMyPageForm',
  data() {
    return {
      defaultProfile,
    }
  },
}
</script>

<style>
  .myfeed {
    padding-top: 50px;
  }
</style>