import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
import InfiniteLoading from 'vue-infinite-loading'


Vue.config.productionTip = false

Vue.use(VueRouter)
Vue.use(InfiniteLoading)

const router = new VueRouter({
    routes,
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');


