<template>
  <div>
    <el-container>
      <el-header>项目实训管理系统</el-header>

      <el-container :style="{minHeight: Height+'px'}">
        <el-aside :width="isCollapse ? '65px' : '200px'" style="background-color: rgb(238, 241, 246)">
          <div class="toggle-button" @click="toggleCollapse">|||</div>
          <el-menu
              router
              :default-openeds="default_index"
              :collapse="isCollapse"
              :collapse-transition="false">

            <el-submenu index="1">
              <template slot="title"><i class="el-icon-user-solid"></i><span slot="title">名单列表</span></template>
              <el-menu-item index="stu_form" @click="isClick('1')"><i class="el-icon-arrow-right"></i>学生名单</el-menu-item>
              <el-menu-item index="group_form" @click="isClick('1')"><i class="el-icon-arrow-right"></i>小组名单</el-menu-item>
              <el-menu-item index="tea_form" @click="isClick('1')"><i class="el-icon-arrow-right"></i>教师名单</el-menu-item>
              <el-menu-item index="project_list" @click="isClick('1')"><i class="el-icon-arrow-right"></i>课题列表</el-menu-item>
            </el-submenu>

            <el-menu-item index="release_document" @click="isClick('2')"><i class="el-icon-s-claim"></i><span slot="title">发布文档</span></el-menu-item>

            <el-submenu index="3">
              <template slot="title"><i class="el-icon-search"></i><span slot="title">成绩查询</span></template>
              <el-menu-item index="grade_form" @click="isClick('3')"><i class="el-icon-arrow-right"></i>成绩名单</el-menu-item>
              <el-menu-item index="group_grade" @click="isClick('3')"><i class="el-icon-arrow-right"></i>小组成绩</el-menu-item>
            </el-submenu>

            <el-menu-item index="reply_plan" @click="isClick('4')"><i class="el-icon-s-order"></i><span slot="title">答辩安排</span></el-menu-item>

            <el-submenu index="5">
              <template slot="title"><i class="el-icon-setting"></i><span slot="title">退出系统</span></template>
              <el-menu-item index="admin_login_out" @click="login_out"><i class="el-icon-arrow-right"></i>登出</el-menu-item>
            </el-submenu>

          </el-menu>
        </el-aside>

        <el-container>
          <el-main>
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container >

      <el-footer height="30px">TJ-02开发</el-footer>
    </el-container>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        default_index: ['1'],
        Height: 0,
        isCollapse: false
      }
    },
    methods: {
      isClick(index) {
        this.default_index = [index]
      },
      login_out() {
        this.$confirm('是否退出当前账号？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '已退出登录!'
          });
          localStorage.clear()
          this.$router.replace('/login')
        }).catch(() => {
        });
      },
      toggleCollapse() {
        this.isCollapse = !this.isCollapse
      }
    },
    mounted() {
      this.Height = document.documentElement.clientHeight - 100;
      window.onresize = ()=> {this.Height = document.documentElement.clientHeight - 100}
    }
  }
</script>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-footer {
    color: #333;
    text-align: center;
  }

  .el-aside {
    color: #333;
  }

  .toggle-button {
    background-color: #DDDDDD;
    font-size: 10px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
  }

  *{
    margin: 0;
  }

</style>