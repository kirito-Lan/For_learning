//导入vue-router
import {createRouter, createWebHistory} from "vue-router";
//导入组件
import loginVue from '@/views/Login.vue'
import layoutVue from '@/views/layout.vue'
import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
import ArticleManageVue from '@/views/article/ArticleManage.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserAvatarVUe from '@/views/user/UserAvatar.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import WatchNews from '@/views/WatchNews.vue'
import LayoutForAdmin from "@/views/layoutForAdmin.vue";
import ArticleCategoryAdmin from "@/views/article/ArticleCategoryAdmin.vue";
import ArticleManageAdmin from "@/views/article/ArticleManageAdmin.vue";
//定义路由关系

const routes = [
    {path: '/login', component: loginVue},
    {path: '/', component: WatchNews},
    {
        path: '/admin',
        component: LayoutForAdmin,
        redirect: '/admin/article/manage',
        //设置子路由
        children: [
            {path: '/admin/article/manage',component: ArticleManageAdmin},
            {path: '/admin/article/category',component: ArticleCategoryAdmin}
        ]
    },
    {
        path: '/common',
        component: layoutVue,
        //重定向
        redirect: '/common/article/manage',
        //子路由
        children: [
            {path: '/common/article/category', component: ArticleCategoryVue},
            {path: '/common/article/manage', component: ArticleManageVue},
            {path: '/common/user/info', component: UserInfoVue},
            {path: '/common/user/avatar', component: UserAvatarVUe},
            {path: '/common/user/password', component: UserResetPasswordVue},
        ]
    }
]
//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

export default router

