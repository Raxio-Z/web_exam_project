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
        path:'welcome',
        name:'welcome',
        component: () => import(/* webpackChunkName: "about" */ '../components/exam/Welcome')
      },
      {
        path:'examList',
        name:'examList',
        component: () => import(/* webpackChunkName: "about" */ '../components/exam/ExamList')
      },
      {
        path:'myExam',
        name:'myExam',
        component: () => import(/* webpackChunkName: "about" */ '../components/exam/MyExam')
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

export default router
