<script setup>
import {
  Management,
  Promotion,
  SwitchButton,
  CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default_.png'

//导入接口函数
import {userInfoGetService} from '@/api/user'
//导入pinia
import {useUserInfoStore} from '@/stores/userInfo'

const userInfoStore = useUserInfoStore();

//获取个人信息
const getUserInfo = async () => {
  let result = await userInfoGetService();
  //存储pinia
  userInfoStore.setInfo(result.data);
}
getUserInfo()

import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {useTokenStore} from "@/stores/token";

const tokenStore = useTokenStore();
const router = useRouter();
const handleCommand = async (command) => {
  if (command === 'logout') {
    //退出登录
    ElMessageBox.confirm(
        '你确认退出吗？',
        '温馨提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
        .then(async () => {
          //用户点击了确认
          //清空pinia中存储的token以及个人信息
          tokenStore.removeToken();
          userInfoStore.removeInfo();
          //跳转到登录页面
          router.push('/login')
          ElMessage.success('退出成功')
        })
        .catch(() => {
          //用户点击了取消
          ElMessage({
            type: 'info',
            message: '取消退出',
          })
        })
  } else {
    await router.push('/user/' + command)
  }
}
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#fff"
               router>
        <el-menu-item index="/admin/article/category">
          <el-icon>
            <Management/>
          </el-icon>
          <span>文章分类</span>
        </el-menu-item>
        <el-menu-item index="/admin/article/manage">
          <el-icon>
            <Promotion/>
          </el-icon>
          <span>文章管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>Administrator: <strong>{{ userInfoStore.info.username }}</strong></div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="avatar"/>
                        <el-icon>
                            <CaretBottom/>
                        </el-icon>
                    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>
      <!-- 底部区域 -->
      <!--      <el-footer>大事件 ©2023 Copied by Kirito.Lan</el-footer>-->
    </el-container>

  </el-container>

</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url('@/assets/logo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>