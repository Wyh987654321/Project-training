import Vue from 'vue'
import Router from 'vue-router'
// import Login from "@/components/Login";
import Student_Home from '@/components/Student_Home'
import Welcome from '@/components/Welcome'
import submit_group from '@/components/Student/submit_group'
import submit_question from '@/components/Student/submit_question'
import submit_project from '@/components/Student/submit_project'
import search_question from '@/components/Student/search_question'
import search_project from '@/components/Student/search_project'
import search_document from '@/components/Student/search_document'
import search_reply from '@/components/Student/search_reply'
import search_grade from '@/components/Student/search_grade'

Vue.use(Router)

const router = new Router({
    routes: [
        { path: '/' , redirect: '/student_home' },
        // { path: '/login' , component: Login },
        { path: '/student_home' ,
          component: Student_Home ,
          redirect: '/welcome' ,
          children: [
              { path: '/welcome' , component: Welcome},
              { path: '/submit_group' , component: submit_group},
              { path: '/submit_question' , component: submit_question},
              { path: '/submit_project' , component: submit_project},
              { path: '/search_question' , component: search_question},
              { path: '/search_project' , component: search_project },
              { path: '/search_document' , component: search_document},
              { path: '/search_reply' , component: search_reply},
              { path: '/search_grade' , component: search_grade}
          ]
        }
    ]
})

// router.beforeEach((to, from, next)) => {
//
// }

export default router