import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import InfiniteLoading from 'vue-infinite-loading'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import VCalendar from 'v-calendar'

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(InfiniteLoading)

Vue.use(VCalendar, {
    componentPrefix: 'vc',  // Use <vc-calendar /> instead of <v-calendar />
                // ...other defaults
  });

// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

const router = new VueRouter({
    routes,
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');


