<script setup>
import {ElNotification} from 'element-plus'
import {User, Lock} from '@element-plus/icons-vue'
import {ref} from 'vue'
import {useTokenStore} from "@/stores/token";
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义数据模型
const registerData = ref({
  username: '',
  password: '',
  rePassword: ''
})

const disableButton = ref(false)
//校验密码函数
const checkPassword = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请再次确认密码"))
    disableButton.value = true
  } else if (value !== registerData.value.password) {
    callback(new Error("两次密码不一致"))
    disableButton.value = true
  } else {
    callback();
    disableButton.value=false;
  }
}
//校验表单
const rules = {
  username: [{
    required: true, message: "请输入用户名", trigger: 'blur'
  },
    {
      min: 5, max: 16, message: "长度在5-16", trigger: 'blur'
    }],
  password: [{
    required: true, message: "请输入密码", trigger: 'blur'
  }, {
    min: 5, max: 16, message: "长度为5-16非空字符", trigger: 'blur'
  }],
  rePassword: [{
    validator: checkPassword, trigger: 'blur'
  }]
}
//注册函数
//导入接口
import {userLoginService, userRegisterService} from "@/api/user";
import {useRouter} from "vue-router";

const router = useRouter();
const tokenStore = useTokenStore()
const register = async () => {
  //registerData是响应式的数据要使用.value
  let result = await userRegisterService(registerData.value);
  ElNotification({
    title: 'Success',
    message: "注册成功",
    type: 'success',
  })
  isRegister.value = false
}

//登录函数
const login = async () => {
  try {
    let result = await userLoginService(registerData.value);
    ElNotification({
      title: 'Success',
      message: "登录成功",
      type: 'success',
    })
    //将得到的token存储到Pinia
    tokenStore.setToken(result.data)
    let payload = parseJwt(result.data)
    if (payload.id === "1") {
      router.push('/admin')
    } else {
      router.push('/common')
    }
    //console.error(result.data)
  } catch (error) {
    console.error(error);
    // 处理错误
  }
}

function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
}


//清空数据模型
const freshData = () => {
  registerData.value = {
    username: '',
    password: '',
    rePassword: ''
  }
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                    v-model="registerData.password" show-password></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                    v-model="registerData.rePassword" show-password></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-link type="success" :underline="false" @click="isRegister = false ;freshData()">
              返回
            </el-link>
            <el-link type="primary" :underline="false" @click="router.push('/')"> Home</el-link>
          </div>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register" :disabled="disableButton">
            注册
          </el-button>
        </el-form-item>

      </el-form>
      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" v-model="registerData.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password" :prefix-icon="Lock" type="password" v-model="registerData.password"
                    placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-link type="success" :underline="false" @click="isRegister = true;freshData()">
              注册
            </el-link>
            <el-link type="primary" :underline="false" @click="router.push('/')"> Home</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>

      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }


  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>