<template>
    <div class="feed-item">
        <!-- <div v-for="(people) in peoples" v-bind:key="people.id" style="width: 100%;" > -->
            <div v-if="people.people_name !=null" class="actor" style="width: 100%;">
            <div class="box">
              <img v-if="people.profile_path!=null" :src='imgBaseUrl + people.profile_path' class="profile" alt="profile">
              <img v-else :src='defaultProfile' class="profile" alt="profile">
            </div>
            <div>
                <div>
                    {{people.people_name}} 
                    <!-- also_known_as랑 birthday는 있으면 띄우고 없으면 안 띄운다 -->
                    <span v-if="people.also_known_as != null">({{people.also_known_as}}) </span>
                </div>
                <div class="text-muted" v-if="people.birthday != null">{{people.birthday}} 출생</div>
            </div>
       </div>

        <div v-if="people.programs.length>0" class="programs"> 
          <div v-for="(program) in people.programs" v-bind:key="program.id"
            @click="goDetail(program.id)" class="programItem">
        <div class="imageBox">
          <div>
              <img v-if="program.poster_path != null" :src='imgBaseUrl + program.poster_path'
               class="img-fluid" alt="poster">
              <img v-else :src='defaultPoster'
               style="padding-top:35px; padding-bottom: 35px;" class="img-fluid" alt="poster">
          </div>
            <span v-if="program.name !=null">{{program.name}}</span>
            <span v-else>{{program.title}}</span>
          </div>
          </div>
        </div>

        <!-- </div> -->

    </div>

</template>

<script>
import tmdbApi from '@/api/tmdbApi.js';
import defaultProfile from '@/assets/images/profile_default.png'
import defaultPoster from '@/assets/images/custom/logo.png'
import ResultProgram from "@/components/search/ProgramSearchResult.vue";
export default {
    name: 'ProgramSearchResult',
  data: () => {
    return {
        imgBaseUrl: tmdbApi.IMAGE_BASE_URL,
        defaultProfile,
        defaultPoster,
        ResultProgram
    };
  },
  props: {
    people: Object
  },
  mounted() {
      // console.log(this.peoples)
  },
  methods:{
      goDetail(id){
          this.$router.push("/program/" + id);
      }
  }
}
</script>

<style scoped>
.actor{
  padding: 10px;
  position: fixed;
  top: 50px;
  background-color: white;
  display: flex;
}
.peopleList{
    display: flex;
    background-color: lightgray;
}
ul {
  padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}
  .box {
    width: 40px;
    height: 40px; 
    border-radius: 70%;
    overflow: hidden;
    margin-right: 10px;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.programs{
    padding-top: 120px;
    padding-bottom: 10px;
}
.programItem {
  list-style-type: none;
  float: left;
  width: 100px;
  height: 200px;
  margin: 10px;
}

/* .profile {
    object-fit: cover;
} */

</style>