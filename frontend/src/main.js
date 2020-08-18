import 'babel-polyfill';
import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'

import InfiniteLoading from 'vue-infinite-loading'
import { BootstrapVue, BootstrapVueIcons, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import VCalendar from 'v-calendar'
import VueHtml2Canvas from 'vue-html2canvas'

// Google Social Login
import GAuth from 'vue-google-oauth2'
const gauthOption = {
  clientId: '420822944998-spsnvgec0jsolelhv29ui190blc2j92t.apps.googleusercontent.com',
  scope: 'profile email',
  prompt: 'select_account'
}
Vue.use(GAuth, gauthOption)

Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(InfiniteLoading)
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.use(VCalendar, {
    componentPrefix: 'vc',  // Use <vc-calendar /> instead of <v-calendar />
                // ...other defaults
  });

// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.use(VueHtml2Canvas)

const router = new VueRouter({
    mode: 'history',
    routes,
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');


