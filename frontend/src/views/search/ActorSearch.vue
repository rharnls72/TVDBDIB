<template>
  <div>
    <PeopleSearchProgramHeader/>
   
      <PeopleSearchProgramResult :people="people"/>

    <Footer />
  </div>
</template>

<script>
import Footer from '../../components/common/custom/Footer.vue';
import GetUserApi from "@/api/GetUserApi"
import PeopleSearchProgramResult from '@/components/search/PeopleSearchProgramResult.vue';
import PeopleSearchProgramHeader from '@/components/search/PeopleSearchProgramHeader.vue';

export default {
  name: 'IndexSearch',
  components: {
    Footer,
    PeopleSearchProgramResult,
    PeopleSearchProgramHeader
  },
  data() {
    return {
      people: this.$route.params.people,
    }
  },
  created() {
    GetUserApi.getUser(res => {
      this.$store.commit('addUserInfo', res.user);
    });
    console.log(this.$route.params.people)
    console.log(this.people);
  },
  
};
</script>
<style scoped>

</style>