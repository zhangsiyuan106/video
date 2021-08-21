import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import search from './modules/search'
import user from './modules/user'
import home from './modules/home'
import headline from './modules/headline'
import video from './modules/video'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        id: localStorage.getItem("id"),
        nickname: localStorage.getItem("nickname"),
        username: localStorage.getItem("username"),
        introduction:localStorage.getItem("introduction"),
        phone:localStorage.getItem("phone"),
        email:localStorage.getItem("email"),
        sex: localStorage.getItem("sex"),
        avatar: localStorage.getItem("avatar"),
        backgroundImage: localStorage.getItem("backgroundImage"),
        hotList: localStorage.getItem("hotList"),
    },
    mutations: {},
    modules: {
        app,
        search,
        user,
        home,
        video,
        headline,
    },
    getters,
});

export default store
