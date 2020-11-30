import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/components/Login";
import Student_Home from '@/components/Student_Home'
import Welcome from '@/components/Welcome'
import submit_group from '@/components/Student/submit_group'
import submit_topic from '@/components/Student/submit_topic'
import submit_project from '@/components/Student/submit_project'
import search_topic from '@/components/Student/search_topic'
import search_project from '@/components/Student/search_project'
import search_document from '@/components/Student/search_document'
import search_reply from '@/components/Student/search_reply'
import search_grade from '@/components/Student/search_grade'
import login_out from '@/components/Student/login_out'

Vue.use(Router)

const router = new Router({
    routes: [
        { path: '/' , redirect: '/login' },
        { path: '/login' , component: Login },
        { path: '/student_home' ,
          component: Student_Home ,
          redirect: '/welcome' ,
          children: [
              { path: '/welcome' , component: Welcome},
              { path: '/submit_group' , component: submit_group},
              { path: '/submit_topic' , component: submit_topic},
              { path: '/submit_project' , component: submit_project},
              { path: '/search_topic' , component: search_topic},
              { path: '/search_project' , component: search_project },
              { path: '/search_document' , component: search_document},
              { path: '/search_reply' , component: search_reply},
              { path: '/search_grade' , component: search_grade},
              { path: '/login_out' , component: login_out},
          ]
        }
    ]
})

// router.beforeEach((to, from, next)) => {
//
// }

export default router