<template>
    <div class="feed-item">

        <div v-for="(people) in peoples" v-bind:key="people.id" class="card" style="width: 100%;">
        <div class="card-header d-flex justify-content-between align-items-center">
            <ul style="padding-top: 5%;"> <!-- 가운데 맞추려는 고육지책... -->
            <li class="d-flex align-items-center">
                {{people.people_name}} 
                <!-- also_known_as랑 birthday는 있으면 띄우고 없으면 안 띄운다 -->
                <div v-if="people.also_known_as != null">({{people.also_known_as}}) </div>
            </li>
            <li> 
                <div class="text-muted" v-if="people.birthday != null">{{people.birthday}} 출생</div>
            </li>
            </ul>
            <img :src='imgBaseUrl + people.profile_path' width="35rem" height="35rem" class="img-fluid float-right" alt="profile">           
        </div>
        <ul class="list-group list-group-flush">
            <li v-for="(program) in people.programs" v-bind:key="program.id" 
            class="list-group-item d-flex justify-content-between align-items-center" @click="goDetail(program.id)">
                {{program.name}}
                <div class="image-parent">
                    <img :src='imgBaseUrl + program.poster_path' width="75rem" height="120rem" class="img-fluid" alt="poster">
                </div>
            </li>
        </ul>
        </div>

    </div>

</template>

<script>
import tmdbApi from '@/api/tmdbApi.js';

export default {
    name: 'ProgramSearchResult',
  data: () => {
    return {
        imgBaseUrl: tmdbApi.IMAGE_BASE_URL
    };
  },
  props: {
    peoples: Array
  },
  methods:{
      goDetail(id){
          this.$router.push("/program/" + id);
      }
  }
}
</script>

<style scoped>

ul {
  padding-top: 0px; /* 검색창을 sticky로 고정시키니까 이걸 다시 0으로 해도 됐다. 50이었는데... */
}

</style>