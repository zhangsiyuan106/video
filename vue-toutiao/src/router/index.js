import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
Router.prototype.animate = 0
const _import_ = file => () => import('views/' + file + '.vue')


/*
    slide: 页面切换动画
    login: 是否需要登陆

 */
export const constantRouterMap = [

    {
        path: '/',
        name: '首页',
        meta: {page: true},
        component: _import_('Layout/index'),
        redirect: '/home',
        children: [
            {path: 'home', component: _import_('Home/index'), name: '首页'}
        ]
    },
    {
        path: '/video',
        meta: {page: true},
        component: _import_('Layout/index'),
        redirect: '/',
        children: [
            {path: '/', component: _import_('Video/index'), name: '视频'}
        ]
    },

    {
        path: '/headline',
        meta: {page: true},
        component: _import_('Layout/index'),
        redirect: '/',
        children: [
            {path: '/', component: _import_('Headline/index'), name: '微头条'}
        ]
    },
    {
        path: '/account',
        meta: {page: true},
        component: _import_('Account/index'),
        name: '个人中心'
    },
    {
        path: '/search',
        name: '搜索',
        meta: {slide: 1},
        component: _import_('Search/index')
    },
    {
        path: '/article/:id',
        name: '文章',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('Article/index')
    },

    {
        path: '/video/:id',
        name: '视频',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('videowithcommon/index')
    },
    {
        path: '/homepage',
        name: '个人主页',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('Homepage/index')
    },
    {
        path: '/system',
        name: '系统设置',
        meta: {
            slide: 1,
        },
        component: _import_('System/index')
    },
    {
        path: '/userSettings',
        name: '用户设置',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('UserSettings/index')
    },
    {
        path: '/editInformation',
        name: 'editInformation',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('EditInformation/index')
    },
    {
        path: '/editname',
        name: 'editname',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('EditInformation/Editname/index')
    },
    {
        path: '/editintroduction',
        name: '修改简介',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('EditInformation/Editintroduction/index')
    },
    {
        path: '/accountAndSecurity',
        name: '账号与安全',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('EditInformation/AccountAndSecurity/index')
    },
    {
        path: '/editphone',
        name: '修改手机号',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('EditInformation/Editphone/index')
    },
    {
        path: '/changePassword',
        name: '修改密码',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('EditInformation/ChangePassword/index')
    },
    {
        path: '/hotList',
        name: '热榜',
        meta: {
            slide: 1,
        },
        component: _import_('HotList/index')
    },
    {
        path: '/hotInf',
        name: 'hotInf',
        meta: {
            slide: 1,
        },
        component: _import_('HotList/Inf/index')
    },
    {
        path: '/weather',
        name: '天气',
        meta: {
            slide: 1,
        },
        component: _import_('Weather/index')
    },
    {
        path: '/city',
        name: '城市',
        meta: {
            slide: 1,
        },
        component: _import_('Weather/city/index')
    },

    {
        path: '/master',
        name: '后台管理',
        meta: {
            slide: 1,
        },
        component: _import_('Master/index')
    },
    {
        path: '/unauthorized',
        name: '未授权',
        component: _import_('Master/Unauthorized')
    },
    {
        path: '/registerPage',
        name: '注册',
        meta: {
            slide: 1,
        },
        component: _import_('Register/index')
    },
    {
        path: '/masterHome',
        name: '后台首页',
        component: _import_('Master/Home')
    },
    {
        path: '/swagger',
        name: '接口文档',
        meta: {
            slide: 1,
        },
        component: _import_('Swagger/Swagger')
    },
    {
        path: '/druid',
        name: 'sql监控',
        meta: {
            slide: 1,
        },
        component: _import_('Druid/Druid')
    },
    {
        path: '/masterWelHome',
        name: '后台欢迎页面',
        meta: {
            slide: 1,
        },
        component: _import_('Master/Welcome')
    },
    {
        path: '/userMaster',
        name: '用户列表',
        meta: {
            slide: 1,
        },
        component: _import_('Master/UserList')
    },
    {
        path: '/comment',
        name: '评论列表',
        meta: {
            slide: 1,
        },
        component: _import_('Master/CommentList')
    },
    {
        path: '/videoList',
        name: '视频列表',
        meta: {
            slide: 1,
        },
        component: _import_('Master/VideoList')
    },
    {
        path: '/articleMaster',
        name: '文章列表',
        meta: {
            slide: 1,
        },
        component: _import_('Master/ArticleList')
    },
    {
        path: '/logs',
        name: '操作日志',
        meta: {
            slide: 1,
        },
        component: _import_('Master/Logs')
    },

    {
        path: '/follows',
        name: '我的关注',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('FollowsPage/follows')
    },
    {
        path: '/followers',
        name: '我的粉丝',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('FollowsPage/followers')
    },
    {
        path: '/msg',
        name: '消息通知',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('Msg/index')
    },
    {
        path: '/tomemsg',
        name: '文章评论',
        meta: {
            slide: 1 ,
            login: true
        },
        component: _import_('Msg/tomemsg')
    },
    {
        path: '/videomsg',
        name: '视频评论',
        meta: {
            slide: 1 ,
            login: true
        },
        component: _import_('Msg/videomsg')
    },
    {
        path: '/replymsg',
        name: '回复我的',
        meta: {
            slide: 1 ,
            login: true
        },
        component: _import_('Msg/replymsg')
    },

    {
        path: '/invite',
        name: '邀请好友功能',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('invite/index')
    },
    {
        path: '/invitepage',
        name: '邀请好友',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('invite/invitepage')
    },
    {
        path: '/help-*',
        name: '助力好友',
        meta: {
            slide: 1,
            login: true
        },

        component: _import_('invite/zhuli')
    },
    {
        path: '/creative',
        name: '创作中心',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('creativeCenter/index')
    },
    {
        path: '/creativeArticleCover',
        name: '文章封面',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('creativeCenter/creativeArticleCover')
    },
    {
        path: '/creative/:articleId/list',
        name: '创作列表',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('creativeCenter/createList')
    },
    {
        path: '/creative/:articleId/edit',
        name: '发布',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('creativeCenter/creativeEdit')
    },

    {
        path: '/creative/analysis',
        name: '数据分析',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('creativeCenter/dataAnalysis')
    },

    {
        path: '/videoupload',
        name: '视频上传',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('videoupload/index')
    },
    {
        path: '/videoListDetail',
        name: '视频列表',
        component: _import_('Video/index')
    },
    {
        path: '/videowithcommon',
        name: '视频详情页',
        meta: {
            slide: 1,
            login: true
        },
        component: _import_('videowithcommon/index')
    },
    {
        path: '/pictureupload',
        name: '图片上传',
        component: _import_('Picture/pictureupload')
    },
    {
        path: '/book',
        name: '电子书',
        component: _import_('book/index')
    }
]
export const asyncRouterMap = []

export const router = new Router({
    mode: 'history',
    routes: constantRouterMap
})

