

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinComplete from './views/user/JoinComplete.vue'
import ModifyPw from './views/user/ModifyPw.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import Errors from './views/Errors.vue'
import PageNotFound from './views/PageNotFound.vue'
import ModifyPwComplete from './views/user/ModifyPwComplete.vue'

import CreateFeed from '@/views/feed/CreateFeed.vue'
import feedDetail from '@/components/feed/feedDetail.vue'

import EmailConfirm from './views/user/EmailConfirm.vue'
import ModifyPwEmail from './views/user/ModifyPwEmail.vue'
import FindPw from './views/user/FindPw.vue'
import FindPwComplete from './views/user/FindPwComplete.vue'
import FindPwRe from './views/user/FindPwRe.vue'
import IndexCuration from './views/curation/IndexCuration.vue'
import IndexSearch from './views/search/IndexSearch.vue'
import Alert from './views/alert/Alert.vue'
import EpisodeDetail from './components/curation/episode/EpisodeDetail.vue'
import MyPage from './views/account/mine/MyPage.vue'
import EditMyPage from './views/account/mine/EditMyPage.vue'

import Setting from './views/account/mine/Setting.vue'

import OtherPage from './views/account/your/OtherPage.vue'

import ViewFollowings from './views/account/ViewFollowings.vue'
import ViewFollowers from './views/account/ViewFollowers.vue'

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
        path : '/user/editpw/:email',
        name : 'ModifyPwEmail',
        component : ModifyPwEmail
    },
    {
        path : '/user/emailconfirm/:email',
        name : 'EmailConfirm',
        component : EmailConfirm
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
    {
        path: '/search/main',
        name: 'IndexSearch',
        component: IndexSearch
    },
    {
        path : '/user/findPw',
        name : 'FindPw',
        component : FindPw
    },
    {
        path : '/user/findPwcomplete',
        name : 'FindPwComplete',
        component : FindPwComplete
    },
    {
        path : '/user/findPwRe',
        name : 'FindPwRe',
        component : FindPwRe
    },
    {
        path: '/feed/create/:ftype?/:feedId?',
        name: 'CreateFeed',
        component: CreateFeed
    },
    {
        path: '/alert/test',
        name: 'Alert',
        component: Alert
    },
    {
        path: '/feed/detail/:fno',
        name: 'FeedDetail',
        component: feedDetail
    },
    {
        path: '/episode/detail/:pno/:season/:episode',
        name: 'EpisodeDetail',
        component: EpisodeDetail
    },
    {
        path: '/mypage/main',
        name: 'MyPage',
        component: MyPage        
    },
    {
        path: '/mypage/edit',
        name: 'EditMyPage',
        component: EditMyPage
    },
    {
        path: '/profile/:nick_name',
        name: 'OtherPage',
        component: OtherPage
    },
    {
        path: '/profile/followings/:uno',
        name: 'ViewFollowings',
        component: ViewFollowings
    },
    {
        path: '/profile/followers/:uno',
        name: 'ViewFollowers',
        component: ViewFollowers
    },
    {
        path: '/setting',
        name: 'Setting',
        component: Setting
    },
]
