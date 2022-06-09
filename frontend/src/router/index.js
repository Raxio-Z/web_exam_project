import Vue from 'vue'
import VueRouter from 'vue-router'

import UserLayout from "@/Layouts/UserLayout";
import BasicLayout from "@/Layouts/BasicLayout";
// import HomeView from "@/views/HomeView";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        redirect: '/welcome',
        component: BasicLayout,
        children: [
            {
                path: 'welcome',
                name: 'welcome',
                component: () => import(/* webpackChunkName: "about" */ '../components/exam/Welcome')
            },
            {
                path: 'examList',
                name: 'examList',
                component: () => import(/* webpackChunkName: "about" */ '../components/exam/ExamList')
            },
            {
                path: 'myExam',
                name: 'myExam',
                component: () => import(/* webpackChunkName: "about" */ '../components/exam/MyExam')
            },
            {
                path:'settings',
                name:'settings',
                component: () => import(/* webpackChunkName: "about" */ '../components/user/Settings')
            }
        ]
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/user',
        component: UserLayout,
        redirect: '/user/login',
        name: 'user',
        hidden: true,
        children: [
            {
                path: 'login',
                name: 'login',
                component: () => import(/* webpackChunkName: "user" */ '../components/user/Login')
            },
            // {
            //     path: 'register',
            //     name: 'register',
            //     component: () => import(/* webpackChunkName: "user" */ '../components/user/Register')
            // },
            // {
            //     path: 'register-result',
            //     name: 'registerResult',
            //     component: () => import(/* webpackChunkName: "user" */ '../components/user/RegisterResult')
            // }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})


//设置路由导航守卫，注册全局前置守卫，判断用户是否登录
router.beforeEach((to, from, next) => {
    if (to.path === '/user/login') {
        next();
    } else {
        var token = localStorage.getItem('Authorization');
        //没有token或token过期，跳到登录界面
        if (token === null || token === '') {
            next('/user/login');
        } else {
            next();
        }
    }
})


export default router
