<template>
  <div class="feed newsfeed myfeed">
    <div class="wrapB content">
        <div class="box mg_bottom">
          <img class="profile" :src="imageData" alt="default-image" />
        </div>
        <div class="mg_bottom">
          <!-- <p class="mt-2 mb-0">사진 변경</p> -->
          <b-form-file v-model="pic" class="" accept="image/*" @change="changeProfilePic">사진 선택</b-form-file>
        </div>

        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="email" class="m-0">이메일:</label>
          </div>
          <div class="col-9 pl-0">
            <input id="email" type="text" :value="info.email" class="p-0 form-control" disabled />
          </div>
        </div>
        <!-- 닉네임 가능 여부에 따라 조건문 걸어주기 -->
        <!-- 불가능한 닉네임이면 (중복 등) -->
        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="nickName" class="m-0">닉네임:</label>
          </div>
          <div class="col-9 pl-0">
            <input
              id="nickName"
              v-model="nick_name"
              type="text"
              class="p-0 form-control"
              :class="isNick"
            />
          </div>
        </div>

        <div class="row py-2">
          <div class="col-3 pr-0">
            <label for="textarea-no-resize" class="m-0">소개:</label>
          </div>
          <div class="col-9 pl-0">
            <textarea
              id="textarea-no-resize"
              v-model="bio"
              wrap="soft"
              rows="3"
              class="p-0 form-control"
              style="resize: none;"
            ></textarea>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import defaultProfile from "@/assets/images/profile_default.png";
import AccountApi from "@/api/AccountApi";

export default {
  name: "EditMyPageForm",
  data() {
    return {
      imageData: defaultProfile,
      profile_pic: "",
      nick_name: "",
      bio: "",
      isNick: "is-valid",

      pic: null,
      pic_modified: false,
    };
  },
  props: {
    info: Object,
    is_valid: Object,
  },
  watch: {
    nick_name: function (v) {
      this.checkNick();
    },
    bio: function () {
      this.syncData();
    }
  },
  mounted() {
    this.nick_name = this.$store.state.userInfo.nick_name;
    this.bio = this.$store.state.userInfo.bio;
    this.profile_pic = this.$store.state.userInfo.profile_pic;
    if(this.profile_pic != null) {
      // console.log(this.profile_pic);
      this.imageData = this.profile_pic;
    }
  },
  methods: {
    checkNick() {
      AccountApi.requestFindNick(
        {
          nick_name: this.$store.state.userInfo.nick_name,
          new_nick_name: this.nick_name,
        },
        (res) => {
          if (res.isNick) {
            this.isNick = "is-valid";
          } else {
            this.isNick = "is-invalid";
          }
          this.syncData();
        },
        (error) => {
          this.$router.push({ name: "Errors", query: { message: error.msg } });
        }
      );
    },
    syncData() {
      this.info.nick_name = this.nick_name;
      this.info.bio = this.bio;
      if (this.isNick == "is-valid" && this.bio.length > 0) {
        this.is_valid.is_valid = "is-valid";
      } else {
        this.is_valid.is_valid = "is-invalid";
      }
      this.$emit("info", this.info);
      this.$emit("is_valid", this.is_valid);
    },

    changeProfilePic(event) {
      // Reference to the DOM input element
      var input = event.target;
      // Ensure that you have a file before attempting to read it
      if (input.files && input.files[0]) {
          // create a new FileReader to read this image and convert to base64 format
          var reader = new FileReader();
          // Define a callback function to run, when FileReader finishes its job
          reader.onload = (e) => {
              // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
              // Read image as base64 and set to imageData
              this.imageData = e.target.result;
              this.info.profile_pic = this.imageData;
              this.syncData();
          }
          // Start the reader job - read file as a data url (base64 format)
          reader.readAsDataURL(input.files[0]);
      }
    }
  }
};
</script>

<style>
.myfeed {
  padding-top: 50px;
}
.mg_bottom{
  margin: auto;
  margin-bottom: 15px;
}
.content{
  padding: 30px;
}
.box {
    width: 100px;
    height: 100px; 
    border-radius: 70%;
    overflow: hidden;
    background: #BDBDBD;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>