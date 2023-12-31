<script setup>
import {ref} from 'vue'
import useUserInfoStore from "@/stores/userInfo";

const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})
const rules = {
  nickname: [
    {required: true, message: '请输入用户昵称', trigger: 'blur'},
    {
      pattern: /^\S{2,10}$/,
      message: '昵称必须是2-10位的非空字符串',
      trigger: 'blur'
    }
  ],
  email: [
    {required: true, message: '请输入用户邮箱', trigger: 'blur'},
    {type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
  ]
}

//修改用户信息
import {userInfoUpdateService} from '@/api/user.js'
import {ElMessage} from 'element-plus';

const updateUserInfo = async () => {
  let result = await userInfoUpdateService(userInfo.value)
  ElMessage.success(result.message ? result.message : '修改成功')
  //更新pinia中的数据
  userInfoStore.info.nickname = userInfo.value.nickname
  userInfoStore.info.email = userInfo.value.email
}

</script>
<template>
  <div class="form-background">
    <el-card class="form-container">
      <template #header>
        <div class="header">
          <span>基本资料</span>
        </div>
      </template>
      <el-row>
        <el-col :span="40">
          <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
            <el-form-item label="登录名称">
              <el-input v-model="userInfo.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="用户昵称" prop="nickname">
              <el-input v-model="userInfo.nickname" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="用户邮箱" prop="email">
              <el-input v-model="userInfo.email"></el-input>
            </el-form-item>
            <el-form-item >
              <el-button type="primary" @click="updateUserInfo" >提交修改</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<style scoped>
.form-background {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url('https://webgradients.com/public/webgradients_png/043%20New%20York.png');
  background-size: cover;
  background-position: center;
}

.form-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(1px);
  padding: 20px;
  border-radius: 10px;
  width: 80%; /* Adjust this value according to your needs */
  max-width: 500px; /* Adjust this value according to your needs */
}
</style>

