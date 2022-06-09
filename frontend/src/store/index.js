import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        //存储token
        Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
    },
    getters: {},
    mutations: {
        //修改token，并将token存入localStorage
        Login(state, user) {
            state.Authorization = user.Authorization;
            localStorage.setItem('Authorization', user.Authorization);
            localStorage.setItem('Username', user.Username);
        },
        //退出登录，清除token和localStorage中的数据
        Logout(state) {
            state.Authorization = '';

            if (localStorage.getItem('Authorization'))
                console.log(localStorage.getItem('Authorization'));
            if (localStorage.getItem('Username'))
                console.log(localStorage.getItem('Username'));

            console.log('我是分隔符');

            localStorage.removeItem('Authorization');
            localStorage.removeItem('Username');
            if (localStorage.getItem('Authorization'))
                console.log(localStorage.getItem('Authorization'));
            if (localStorage.getItem('Username'))
                console.log(localStorage.getItem('Username'));
        }
    }

    ,
    actions: {},
    modules: {}
})
export default store;