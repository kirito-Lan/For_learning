//导入vue-router
import {createRouter, createWebHistory} from "vue-router";
//导入组件
import loginVue from '@/views/Login.vue'
import layoutVue from '@/views/layout.vue'
import ArticleCategoryVue from'@/views/article/ArticleCategory.vue'
import ArticleManageVue from'@/views/article/ArticleManage.vue'
import UserInfoVue from'@/views/user/UserInfo.vue'
import UserAvatarVUe from'@/views/user/UserAvatar.vue'
import UserResetPasswordVue from'@/views/user/UserResetPassword.vue'
//定义路由关系

const routes = [
    { path: '/login', component: loginVue },
    {
        path: '/',
        component: layoutVue,
        //重定向
        redirect: '/article/manage',
        //子路由
        children: [
            { path: '/article/category', component: ArticleCategoryVue },
            { path: '/article/manage', component: ArticleManageVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVUe },
            { path: '/user/password', component: UserResetPasswordVue },
        ]
    }
]
//创建路由器
const router=createRouter({
    history:createWebHistory(),
    routes:routes
});

export default router