<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h2>青云宗管理系统</h2>
        <p>欢迎回来，请登录</p>
      </div>
      <el-form :model="form" ref="formRef" label-width="80px" class="login-form">
        <el-form-item label="用户名" prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="请输入用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input 
            v-model="form.password" 
            type="password" 
            placeholder="请输入密码"
            prefix-icon="Lock"
          />
        </el-form-item>
        <el-form-item label="验证码" prop="captcha">
          <div class="captcha-row">
            <el-input 
              v-model="form.captcha" 
              placeholder="请输入验证码"
              class="captcha-input"
            />
            <img 
              :src="captchaUrl" 
              alt="验证码" 
              class="captcha-img"
              @click="refreshCaptcha"
            />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" class="login-btn">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login as loginApi, getCaptcha } from '@/api/login'

const router = useRouter()

const form = ref({
  username: '',
  password: '',
  captcha: ''
})

const formRef = ref(null)
const captchaUrl = ref('')

const refreshCaptcha = async () => {
  const res = await getCaptcha()
  captchaUrl.value = res.data
}

const login = async () => {
  if (!form.value.username || !form.value.password || !form.value.captcha) {
    ElMessage.warning('请填写完整信息')
    return
  }
  
  try {
    const res = await loginApi(form.value)
    localStorage.setItem('token', res.data.token)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '登录失败')
    refreshCaptcha()
  }
}

onMounted(() => {
  refreshCaptcha()
})
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  margin: 0 0 10px 0;
  color: #333;
}

.login-header p {
  margin: 0;
  color: #999;
}

.login-form {
  padding: 0 20px;
}

.captcha-row {
  display: flex;
  gap: 10px;
}

.captcha-input {
  flex: 1;
}

.captcha-img {
  width: 120px;
  height: 40px;
  cursor: pointer;
  border-radius: 4px;
}

.login-btn {
  width: 100%;
}
</style>
