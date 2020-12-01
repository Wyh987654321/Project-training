import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/components/Login";
import Welcome from '@/components/Welcome'

import Teacher_Home from "@/components/Teacher_Home";
// import Student_Home from "@/components/Student_Home";
import submit_topic from '@/components/Teacher/submit_topic'
import search_reply from '@/components/Teacher/search_reply'
import search_group from '@/components/Teacher/search_group'
import grade_reply from '@/components/Teacher/grade_reply'
import grade_guide from '@/components/Teacher/grade_guide'
Vue.use(Router)


const router = new Router({
    routes: [
        { path: '/' , redirect: '/teacher_home' },
        { path: '/login' , component: Login },
        { path: '/teacher_home' ,
            component: Teacher_Home ,
            redirect: '/welcome' ,
            children: [
                { path: '/welcome' , component: Welcome},
                { path: '/submit_topic' , component: submit_topic},
                { path: '/search_reply' , component: search_reply},
                { path: '/search_group' , component: search_group},
                { path: '/grade_reply' , component: grade_reply},
                { path: '/grade_guide' , component: grade_guide},
            ]
        }
    ]
})

// router.beforeEach((to, from, next)) => {
//
// }

export default router