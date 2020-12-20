<template>
  <div>
    <el-container>
      <el-header>项目实训管理系统</el-header>

      <el-container :style="{minHeight: Height+'px'}">
        <el-aside :width="isCollapse ? '65px' : '200px'" style="background-color: rgb(238, 241, 246)">
          <div class="toggle-button" @click="toggleCollapse">|||</div>
          <el-menu router
                   :default-openeds="default_index"
                   :collapse="isCollapse"
                   :collapse-transition="false">

            <el-menu-item index="stu_submit_group" @click="isClick('1')"><i class="el-icon-s-order"></i><span slot="title">填写分组</span></el-menu-item>

            <el-menu-item index="stu_submit_project" @click="isClick('2')" v-show="isLeader==='true'"><i class="el-icon-s-claim"></i><span slot="title">提交项目</span></el-menu-item>

            <el-submenu index="3">
              <template slot="title"><i class="el-icon-search"></i><span slot="title">信息查询</span></template>
              <el-menu-item index="stu_search_topic" @click="isClick('3')"><i class="el-icon-arrow-right"></i>课题介绍</el-menu-item>
              <el-menu-item index="stu_search_project" @click="isClick('3')"><i class="el-icon-arrow-right"></i>我的项目</el-menu-item>
              <el-menu-item index="stu_search_document" @click="isClick('3')"><i class="el-icon-arrow-right"></i>课程文档</el-menu-item>
              <el-menu-item index="stu_search_reply" @click="isClick('3')"><i class="el-icon-arrow-right"></i>答辩安排</el-menu-item>
              <el-menu-item index="stu_search_grade" @click="isClick('3')"><i class="el-icon-arrow-right"></i>我的成绩</el-menu-item>
            </el-submenu>

            <el-submenu index="4">
              <template slot="title"><i class="el-icon-setting"></i><span slot="title">退出登录</span></template>
              <el-menu-item index="stu_login_out" @click="login_out"><i class="el-icon-arrow-right"></i>登出</el-menu-item>
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
        default_index: ['3'],
        Height: 0,
        isLeader: localStorage.getItem('isLeader'),
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