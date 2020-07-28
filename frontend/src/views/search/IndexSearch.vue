<template>
  <div class="feed newsfeed">
    <IndexSearchHeader />
    <div class="wrapB">
      
    </div>
    <Footer />
  </div>
</template>

<script>
import { mapState } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import Footer from '../../components/common/custom/Footer.vue';
import IndexSearchHeader from '../../components/curation/IndexSearchHeader.vue'

export default {
  name: 'IndexSearch',
  components: {
    IndexSearchHeader,
    Footer,
  },
};
</script>