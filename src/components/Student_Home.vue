<template>
  <div>
    <el-container>
      <el-header>项目实训管理系统</el-header>

      <el-container :style="{minHeight: Height+'px'}">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu :default-openeds="default_index" router>
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-s-order"></i>填写信息</template>

              <el-menu-item index="submit_group" @click="isClick('1')"><i class="el-icon-arrow-right"></i>填写分组</el-menu-item>
              <el-menu-item index="submit_topic" @click="isClick('1')"><i class="el-icon-arrow-right"></i>提交选题</el-menu-item>
              <el-menu-item index="submit_project" @click="isClick('1')"><i class="el-icon-arrow-right"></i>提交项目</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-search"></i>信息查询</template>
              <el-menu-item index="search_topic" @click="isClick('2')"><i class="el-icon-arrow-right"></i>课题介绍</el-menu-item>
              <el-menu-item index="search_project" @click="isClick('2')"><i class="el-icon-arrow-right"></i>我的项目</el-menu-item>
              <el-menu-item index="search_document" @click="isClick('2')"><i class="el-icon-arrow-right"></i>课程文档</el-menu-item>
              <el-menu-item index="search_reply" @click="isClick('2')"><i class="el-icon-arrow-right"></i>答辩安排</el-menu-item>
              <el-menu-item index="search_grade" @click="isClick('2')"><i class="el-icon-arrow-right"></i>我的成绩</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title"><i class="el-icon-setting"></i>退出登录</template>
              <el-menu-item index="login_out" @click="login_out"><i class="el-icon-arrow-right"></i>登出</el-menu-item>
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
        default_index: ['2'],
        Height: 0
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
          this.$router.replace('/login')
        }).catch(() => {
        });
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

  *{
    margin: 0;
  }

</style>