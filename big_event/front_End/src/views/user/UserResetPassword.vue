<script lang="ts" setup>
import {reactive, ref, computed} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (ruleForm.new_pwd !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('new_pwd', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value !== ruleForm.new_pwd) {
    callback(new Error("两次密码不匹配"))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  old_pwd: "",
  new_pwd: "",
  re_pwd: ""
})

const rules = reactive<FormRules<typeof ruleForm>>({
  old_pwd: [{validator: validatePass, trigger: 'blur'}],
  new_pwd: [{validator: validatePass2, trigger: 'blur'}],
  re_pwd: [{validator: validatePass2, trigger: 'blur'}],
})

const isFormValid = computed(() => {
  let isValid = true;
  for (let key in ruleForm) {
    if (!ruleForm[key]) {
      isValid = false;
      break;
    }
  }
  return isValid;
});

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
      modifyPassword()
    } else {
      console.log('error submit!')
      return false
    }
  })
}


const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

import {updateUserPasswordService} from "@/api/user"
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {useTokenStore} from "@/stores/token";
import {useUserInfoStore} from '@/stores/userInfo'

const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();
const router = useRouter();
const modifyPassword = async () => {
  let result = await updateUserPasswordService(ruleForm)
  if (result["code"] === "1") {
    //清空pinia中存储的token以及个人信息
    tokenStore.removeToken();
    userInfoStore.removeInfo();
    //跳转到登录页面
    await router.push('/login')
    ElMessage.success("修改成功,请重新登录")
  } else {
    ElMessage.error("修改失败")
  }
}

</script>

<template>
  <div class="form-background">
    <div class="form-container">
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          status-icon
          :rules="rules"
          label-width="120px"
          class="demo-ruleForm"
      >
        <!-- Your form items here -->

        <el-form-item label="旧 密 码:" prop="old_pwd">
          <el-input v-model.number="ruleForm.old_pwd" type="password" show-password style="width: 200px"/>
        </el-form-item>

        <el-form-item label="新 密 码:" prop="new_pwd">
          <el-input v-model="ruleForm.new_pwd" type="password" show-password autocomplete="off" style="width: 200px"/>
        </el-form-item>

        <el-form-item label="确认密码:" prop="re_pwd">
          <el-input
              v-model="ruleForm.re_pwd"
              type="password"
              show-password
              style="width: 200px"
              autocomplete="off"/>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)" :disabled="!isFormValid">提交</el-button>
          <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.form-background {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url('https://webgradients.com/public/webgradients_png/008%20Rainy%20Ashville.png'); /* Replace with your image path */
  background-size: cover; /* This will make the image cover the entire div */
  background-position: center; /* This will center the image in the div */

  .form-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  //background-color: white; padding: 20px; border-radius: 10px; width: 50%; /* Adjust this value according to your needs */
    max-width: 500px; /* Adjust this value according to your needs */
    background: rgba(255, 255, 255, 0.5); /* 半透明白色背景 */
    backdrop-filter: blur(10px); /* 磨砂效果 */
  }
}
</style>


