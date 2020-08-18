import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)

const state = {
    isUser: false,
    userInfo: {},
    searchTab: 1,
    searchWord: '',

    loginEmail: "",
    updateFeed: null,

    tabState: 3,
}

export default new Vuex.Store({
    state,
    mutations,
    getters,
    actions
})