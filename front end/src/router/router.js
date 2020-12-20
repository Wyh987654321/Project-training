import Vue from 'vue'
import Router from 'vue-router'

import Login from "@/components/Login";
import Teacher_Home from "@/components/Teacher_Home";
import Student_Home from '@/components/Student_Home'
import Admin_Home from "@/components/Admin_Home";

import stu_welcome from '@/components/Student/welcome'
import stu_submit_group from '@/components/Student/submit_group'
import stu_submit_project from '@/components/Student/submit_project'
import stu_search_topic from '@/components/Student/search_topic'
import stu_search_project from '@/components/Student/search_project'
import stu_search_document from '@/components/Student/search_document'
import stu_search_reply from '@/components/Student/search_reply'
import stu_search_grade from '@/components/Student/search_grade'
import stu_login_out from '@/components/Student/login_out'

import tea_welcome from '@/components/Teacher/welcome'
import tea_submit_topic from '@/components/Teacher/submit_topic'
import tea_search_reply from '@/components/Teacher/search_reply'
import tea_search_group from '@/components/Teacher/search_group'
import tea_submit_grade from '@/components/Teacher/submit_grade'
import tea_stu_project from '@/components/Teacher/stu_project'
import tea_login_out from '@/components/Teacher/login_out'

import admin_welcome from '@/components/Admin/welcome'
import stu_form from '@/components/Admin/stu_form'
import group_form from '@/components/Admin/group_form'
import tea_form from '@/components/Admin/tea_form'
import project_list from '@/components/Admin/project_list'
import release_document from '@/components/Admin/release_document'
import reply_plan from '@/components/Admin/reply_plan'
import grade_form from '@/components/Admin/grade_form'
import group_grade from '@/components/Admin/group_grade'
import admin_login_out from '@/components/Admin/login_out'

Vue.use(Router)

const router = new Router({
    model:'hash',
    routes: [
        { path: '/' , redirect: '/login' },
        { path: '/login' , component: Login },
        { path: '/student_home' ,
            component: Student_Home ,
            redirect: '/stu_welcome' ,
            children: [
                { path: '/stu_welcome' , component: stu_welcome},
                { path: '/stu_submit_group' , component: stu_submit_group},
                { path: '/stu_submit_project' , component: stu_submit_project},
                { path: '/stu_search_topic' , component: stu_search_topic},
                { path: '/stu_search_project' , component: stu_search_project },
                { path: '/stu_search_document' , component: stu_search_document},
                { path: '/stu_search_reply' , component: stu_search_reply},
                { path: '/stu_search_grade' , component: stu_search_grade},
                { path: '/stu_login_out' , component: stu_login_out},
            ]
        },
        { path: '/teacher_home' ,
            component: Teacher_Home ,
            redirect: '/tea_welcome' ,
            children: [
                { path: '/tea_welcome' , component: tea_welcome},
                { path: '/tea_submit_topic' , component: tea_submit_topic},
                { path: '/tea_search_reply' , component: tea_search_reply},
                { path: '/tea_search_group' , component: tea_search_group},
                { path: '/tea_submit_grade' , component: tea_submit_grade},
                { path: '/tea_stu_project' , component: tea_stu_project},
                { path: '/tea_login_out' , component: tea_login_out }
            ]
        },
        { path: '/Admin_Home' ,
            component: Admin_Home ,
            redirect: '/admin_welcome' ,
            children: [
                { path: '/admin_welcome' , component: admin_welcome},
                { path: '/stu_form' , component: stu_form},
                { path: '/group_form' , component: group_form},
                { path: '/tea_form' , component: tea_form},
                { path: '/project_list' , component: project_list},
                { path: '/release_document' , component: release_document},
                { path: '/grade_form' , component: grade_form},
                { path: '/group_grade' , component: group_grade},
                { path: '/reply_plan' , component: reply_plan},
                { path: '/admin_login_out' , component: admin_login_out},
            ]
        }
    ]
})

// router.beforeEach((to, from, next) => {
//     // if(to.path==='/login') return next()
//     // let user = localStorage.getItem('user')
//     //
//     // if(to.path==='/stu_submit_group' && user.substring(0,1)!=='4') return next ('/login')
// })

export default router