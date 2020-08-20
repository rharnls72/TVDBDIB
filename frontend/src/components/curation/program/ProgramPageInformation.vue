<template>
  <div class="feed newsfeed pt-0">
    <div class="wrapB">
      <!-- <div class="container mt-3"> -->

        <div class="row p-0 justify-content-center">
          <img class="col-5 p-0 mb-3" :src="'https://image.tmdb.org/t/p/w500/'+program.programDetail.poster_path">
          <div class="col-5 pl-3 mb-3 d-flex flex-column justify-content-between">
            <div class="row px-3">
              <select v-if="program.programDetail.number_of_seasons!=1" v-model="season" class="col-12 mb-3">
                <template v-for="(season) in program.programDetail.seasons">
                  <option v-if="season.season_number!=0" :key="season.season_number" :value="season.season_number">시즌 {{season.season_number}}</option>
                </template>
              </select>
              <div>방송시작일 : <p class="mb-0">{{program.programDetail.first_air_date}}</p></div>
              <div>방영사 : <span v-for="(network, idx) in program.programDetail.networks" :key="idx">{{network.name}} </span></div>
              <div>장르 : <span v-for="(genre, idx) in program.programDetail.genres" :key="idx">{{genre.name}} </span></div>
            </div>
            <div class="d-flex justify-content-center">
              <h5 class="mb-0">
                <b-icon-heart v-if="!program.press_like" @click="touchLikeIcon"></b-icon-heart>
                <b-icon-heart-fill v-else @click="touchLikeIcon" class="text-danger"></b-icon-heart-fill>
                <b-icon-pencil @click="createShare" class="mx-4"></b-icon-pencil>
                <b-icon-reply @click="copyUrl"></b-icon-reply>
              </h5>
            </div>
          </div>
        </div>

        <div class="pb-3 mt-2 row">
          <div class="col-4">
            <h5 class="mb-0 text-center">{{program.like_num}}</h5>
            <p class="mb-0 text-center">좋아요</p>
          </div>
          <div class="col-4">
            <h5 class="mb-0 text-center">{{followers.length}}</h5>
            <p class="mb-0 text-center">팔로워</p>
          </div>
          <div class="col-4">
            <h5 class="mb-0 text-center">{{program.reply_num}}</h5>
            <p class="mb-0 text-center">댓글</p>
          </div>
        </div>

        <div class="row justify-content-center">
          <p class="col-10 mb-0 pb-3 px-0 introduce">{{program.programDetail.overview}}</p>
        </div>
        <p class="row p-0 justify-content-center">
          <button v-if="!infollowers" @click="touchFollowIcon" class="col-10 mybutton p-0 text-dark my-3">팔로우</button>
          <button v-else @click="touchFollowIcon" class="col-10 followstate p-0 text-dark my-3">팔로우 취소</button>
        </p>
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import CurationApi from '@/api/CurationApi.js'

export default {
  name: 'ProgramPageInformation',
  data() {
    return {
      season: null
    }
  },
  props: {
    program: Object,
    followers: Array,
    seasonNum: Number,
  },
  methods: {
    createShare() {
      this.$router.push({path:`/createShare/2/${this.program.programDetail.id}`})
    },
    makeToast(message, variant){
      this.$bvToast.toast(message, {
        title: '알림',
        toaster: "b-toaster-bottom-center",
        variant: variant,
        autoHideDelay: 3000,
        appendToast: false
      })
    },
    copyToClipboard(val) {
      var t = document.createElement('textarea')
      document.body.appendChild(t)
      t.value = val
      t.select()
      document.execCommand('copy')
      document.body.removeChild(t)
    },
    copyUrl() {
      this.copyToClipboard(`http://i3a106.p.ssafy.io/program/${this.program.programDetail.id}`)
      this.makeToast("경로 복사에 성공했습니다.", "danger");
    },
    touchLikeIcon() {
      this.program.press_like = !this.program.press_like
      if (this.program.press_like) {
        this.program.like_num ++
        CurationApi.programCreateLike(
          {
            tno: this.program.programDetail.id,
          }
          , res => console.log(res)
          , err => console.log(err)
        )
      }
      else {
        this.program.like_num --
        CurationApi.programDeleteLike(
          {
            tno: this.program.programDetail.id,
          }
          , res => console.log(res)
          , err => console.log(err)
        )
      }
      // console.log(this.likeIcon)
    },
    touchFollowIcon() {
      if (this.infollowers) {
        CurationApi.programDeleteFollow(
          {
            uno: this.$store.state.userInfo.uno,
            pno: this.program.programDetail.id,
          }
          , res => {
            this.$emit('checkFollowers')
          }
          , err => console.log(err)
        )
      }
      else {
        CurationApi.programAddFollow(
          {
            uno: this.$store.state.userInfo.uno,
            pno: this.program.programDetail.id,
          }
          , res => {
            this.$emit('checkFollowers')
          }
          , err => console.log(err)
        )
      }
      // console.log(this.likeIcon)
    },
  },
  watch: {
    season: function(e, n) {
      this.$emit('changeSeason', this.season)
    }
  },
  computed: {
    infollowers() {
      var define = this.followers.filter(res => {
        return res.uno===this.$store.state.userInfo.uno
      })
      var result = null
      if (define.length === 0) {result = false}
      else {result = true}
      return result
    }
  },
  mounted() {
    this.season = this.seasonNum
  }
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
  .followstate {
    height: auto;
    box-shadow: none;
    border: 1px solid lightgray;
    border-radius: 0.25rem;
    background-color: #f8e8f2;
  }
</style>