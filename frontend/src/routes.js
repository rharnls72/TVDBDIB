

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinComplete from './views/user/JoinComplete.vue'
import ModifyPw from './views/user/ModifyPw.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import Errors from './views/Errors.vue'
import PageNotFound from './views/PageNotFound.vue'
import ModifyPwComplete from './views/user/ModifyPwComplete.vue'
import IndexCuration from './views/curation/IndexCuration.vue'

export default [

    {
        path : '*',
        name : 'PageNotFound',
        component : PageNotFound
    },
    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/user/joincomplete',
        name : 'JoinComplete',
        component : JoinComplete
    },
    {
        path : '/user/modifyPw',
        name : 'ModifyPw',
        component : ModifyPw
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path: '/errors',
        name: 'Errors',
        component : Errors
    },
    {
        path: '/user/modifypwcomplete',
        name: 'ModifyPwComplete',
        component: ModifyPwComplete
    },
    {
        path: '/curation/main',
        name: 'IndexCuration',
        component: IndexCuration
    },
]
