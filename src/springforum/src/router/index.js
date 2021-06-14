import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Verification from '../views/Verification.vue'
import discuss from '../components/Main/discuss.vue'
import trend from '../components/Main/trend.vue'
import edit from '../components/Main/edit.vue'
import New from '../components/Main/new.vue'
import paper from '../components/Main/paper.vue'
import savepage from '../components/Main/savepage.vue'
import home from '../components/Main/home.vue'
import chat from '../components/Main/chat.vue'
import profile from '../components/Main/profile.vue'
import mypaper from '../components/Main/mypaper.vue'
import result from '../components/Main/result.vue'

import checkmail from '../components/Verification/checkemail.vue'
import forgetpass from '../components/Verification/forgetpass.vue'
import resetpass from '../components/Verification/resetpassword.vue'
import signin from '../components/Verification/signin.vue'
import signup from '../components/Verification/signup.vue'
import success from '../components/Verification/success.vue'
import test from '../views/test.vue'
import EmCode from '../components/Verification/VerifyCode.vue'
import phone from '../components/Verification/PhoneNum.vue'
import veriydigit from '../components/Verification/verifyDigit.vue'
import oauth2 from '../components/Verification/Oauth2.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/main',
    name: 'main',
    component: Main,
    children: [
      {
        path: 'savepage',
        name: 'savepage',
        component: savepage
      },
      {
        path: '',
        name: 'home',
        component: home
      },
      {
        path: 'trend',
        name: 'Trend',
        component: trend
      },
      {
        path: 'discuss',
        name: 'discuss',
        component: discuss
      },
      {
        path: 'new',
        name: 'new',
        component: New
      },
      {
        path: 'paper/:aid/:uid',
        name: 'paper',
        component: paper
      },
      {
        path: 'edit',
        name: 'edit',
        component: edit
      },
      {
        path: 'chat/:roomNum',
        name: 'chat',
        component: chat
      },
      {
        path: 'profile',
        name: 'profile',
        component: profile
      },
      {
        path: 'mypaper/:aid',
        name: 'mypaper',
        component: mypaper
      },
      {
        path: 'result/:item',
        name: 'result',
        component: result
      }
    ]
  },
  {
    path: '/verification',
    name: 'verification',
    component: Verification,
    children: [
      {
        path: 'forget',
        name: 'forget',
        component: forgetpass
      },
      {
        path: 'checkemail',
        name: 'checkemail',
        component: checkmail
      },
      {
        path: 'password',
        name: 'password',
        component: resetpass
      },
      {
        path: 'success',
        name: 'success',
        component: success
      },
      {
        path: '',
        name: 'signin',
        component: signin
      },
      {
        path: 'signup',
        name: 'signup',
        component: signup
      },
      {
        path: 'verifycode',
        name: 'verifycode',
        component: EmCode
      },
      {
        path: 'phone',
        name: 'phone',
        component: phone
      },
      {
        path: 'verifydigit',
        name: 'verifydigit',
        component: veriydigit
      },
      {
        path: 'redirect',
        name: 'redirect',
        component: oauth2
      }
    ]
  },
  {
    path: '/test',
    name: 'test',
    component: test
  }

  // {
  //   path: '/about',
  //   name: 'About',
  // route level code-splitting
  // this generates a separate chunk (about.[hash].js) for this route
  // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
