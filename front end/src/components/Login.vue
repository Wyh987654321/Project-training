<template>
  <div class="background" :style="backgroundDiv">
    <div class="login_box">
      <h2 class="login_title">项目实训管理系统</h2>
      <el-form class="login_form" label-width="0px" :model="form">
        <el-form-item>
          <el-input v-model="form.username" prefix-icon="el-icon-user" placeholder="账号：" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" prefix-icon="el-icon-lock" placeholder="密码：" clearable show-password @keyup.enter.native="login"></el-input>
          <div style="width:100%;text-align:end;" id="login">
            <el-checkbox v-model="check" @change="checked">自动登录</el-checkbox>
          </div>
        </el-form-item>
      </el-form>
      <div class="login_btn">
        <div style="width:100%;text-align:center">
          <el-button type="primary" @click="login" style="width: 8vw">登录</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        check: localStorage.getItem("isRemember"),
        backgroundDiv: {
          backgroundImage:'url(' + require('@/assets/image/background.png') + ')',
          backgroundRepeat:'no-repeat',
          backgroundSize:'100% 100%'
        },
        form: {
          username: '',
          password: '',
        },
      }
    },
    created() {
      this.autoLogin(localStorage.getItem("isRemember"))
    },
    methods: {
      async login() {
        const res =await this.$http.post("http://121.4.96.102:8080/Test1/login",this.form)
       // const res =await this.$http.post("http://121.4.96.102:8080/Test1/login",this.form)
        if(res.data==="学生登录成功"){
          this.$message.success("登录成功")
          //将username加密
          let ciphertext_name = this.toCode(this.form.username)
          //将密码加密
          let ciphertext_pass =this.toCode(this.form.password)
          await this.isLeader(this.form.username)
          localStorage.setItem("user",ciphertext_name)
          localStorage.setItem("password",ciphertext_pass)
          await this.$router.replace('/student_home')
        }else if(res.data==="教师登录成功"){
          this.$message.success("登录成功")

          //将username加密
          let ciphertext_name = this.toCode(this.form.username)
          //将密码加密
          let ciphertext_pass =this.toCode(this.form.password)
          localStorage.setItem("user",ciphertext_name)
          localStorage.setItem("password",ciphertext_pass)
          await this.$router.replace('/teacher_home')
        }else if(res.data==="管理员登录成功"){
          this.$message.success("登录成功")
          //将username加密
          let ciphertext_name = this.toCode(this.form.username)
          //将密码加密
          let ciphertext_pass =this.toCode(this.form.password)
          localStorage.setItem("user",ciphertext_name)
          localStorage.setItem("password",ciphertext_pass)
          await this.$router.replace('/admin_home')

        }else {
          localStorage.clear()
          this.$message.error("登录失败，请检查用户名或密码是否正确")
        }
      },
      checked(){
        localStorage.setItem("isRemember",this.check)
        console.log(localStorage.getItem("isRemember"));
      },
      autoLogin(check){
        if (check==="true"){
          this.form.username = this.toStr(localStorage.getItem("user"))
          this.form.password = this.toStr(localStorage.getItem("password"))
          this.login()
        }
        else {
          localStorage.setItem("isRemember",false)
          return
        }
      },
      async isLeader(id){
        let command ='1'
        const res =await this.$http.post("http://121.4.96.102:8080/Test1/login",{id,command})
        if(res.data==="是组长"){
          localStorage.setItem("isLeader","true")
        }else
          localStorage.setItem("isLeader","false")
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
    margin-top: 8vh;
    margin-bottom: 3vh;
    font-family: "Hiragino Sans GB", monospace;
    color: cadetblue;
  }

  .login_form {
    position: absolute;
    width: 100%;
    padding-left: 3vw;
    padding-right: 3vw;
    box-sizing: border-box;
  }

  .login_btn {
    position: absolute;
    bottom: 8vh;
    width: 100%;
    padding-left: 3vw;
    padding-right: 3vw;
    box-sizing: border-box;
  }

  #login .el-checkbox__label {
    color: cadetblue;
  }

</style>