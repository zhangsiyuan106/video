import Vue from 'vue'
import App from './App.vue'
import router from './router/permission.js'
import store from './store'
import axios from 'axios'
import { Lazyload } from 'vant';
/**
 * 后台管理全局样式
 */
import './assets/css/global.css' //添加全局样式
import './assets/font/iconfont.css' //iconf样式
// 导入全局的样式文件
import './styles/index1.less'
/*样式*/
import './styles/index.css'
import './styles/index.less'

/*工具类*/
import './utils/iconfont.js'
import './utils/rem.js'
// 处理相对时间、初始化
import './utils/dayjs'

/*指令*/
import './directive'

/**
 * ElementUI
 *
 */
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import 'src/theme/element/index.css'
Vue.use(ElementUI)
/*
layui
 */

import { Cookie } from 'src/utils/storage'
Vue.prototype.Cookie = Cookie

/**
 * vant ui
 */
import Vant from 'vant'
import 'vant/lib/index.css'
// 注册
Vue.use(Vant)
Vue.use(Lazyload);

// 注册时可以配置额外的选项
Vue.use(Lazyload, {
    lazyComponent: true,
});
// 返回
Vue.prototype.back = (route) => {
    route.animate = 2
    history.go(-1)
}

// 跳转文章页
Vue.prototype.skip = (route, id) => {
    route.push('/article/'+id)
}
import { VueJsonp } from 'vue-jsonp'
Vue.use(VueJsonp)
// 全局组件
import customComponents from './custom-components.js'
Vue.use(customComponents)

//挂载axios
Vue.prototype.$axios = axios
Vue.prototype.$http=axios
//设置访问根路径
axios.defaults.baseURL = "http://localhost:8081/"
Vue.config.productionTip = false

//编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)


// swiper
import VueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/dist/css/swiper.css'
Vue.use(VueAwesomeSwiper)

//nativeshare分享插件
// import NativeShare from 'nativeshare'
// Vue.use(NativeShare);
//复制插件
// import ClipboardJS from 'clipboard'
// Vue.use(ClipboardJS);

// 引入fastclick
import FastClick from 'fastclick'
FastClick.attach(document.body)

// 引入install全局组件
import install from './install';
// 全局挂载utils
Vue.use(install);
import "../src/assets/styles/icon.css"
// 引入vue-touch
import VueTouch from 'vue-touch'
Vue.use(VueTouch, {
    name: 'v-touch'
})
VueTouch.config.swipe = {
    threshold: 50 //设置左右滑动的距离
}

Vue.config.productionTip = false
new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: { App }
})
