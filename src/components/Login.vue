<template>
  <div class="background" :style="backgroundDiv">
    <div class="login_box">
      <h2 class="login_title">项目实训管理系统</h2>
      <el-form class="login_form" label-width="0px" :model="form" :rules="login_rules" ref="loginFormRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" prefix-icon="el-icon-user" placeholder="账号：" clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="el-icon-lock" placeholder="密码：" clearable show-password></el-input>
        </el-form-item>
        <div style="width:100%;text-align:center">
          <el-button type="primary" @click="login">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        backgroundDiv: {
          backgroundImage:'url(' + require('@/assets/image/background.png') + ')',
          backgroundRepeat:'no-repeat',
          backgroundSize:'100% 100%'
        },
        form: {
          username: '',
          password: '',
        },
        login_rules: {
          username: [
            { required: true, message: '请输入账号', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ],
        }
      }
    },
    methods: {
      login() {
        this.$router.replace('/student_home')
        // this.$http.post('login', this.form)
      }
    }
  }
</script>

<style>
  .background {
    width: 100%;
    height: 100%;
    position: fixed;
  }

  .login_box {
    width: 30vw;
    height: 50vh;
    border-radius: 15px;
    background-color:  rgba(238, 241, 246, 0.2);
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .login_title {
    text-align: center;
    margin-top: 10vh;
    font-family: "Hiragino Sans GB", monospace;
    color: cadetblue;
  }

  .login_form {
    position: absolute;
    bottom: 4vh;
    width: 100%;
    padding-left: 3vw;
    padding-right: 3vw;
    box-sizing: border-box;
  }

</style>