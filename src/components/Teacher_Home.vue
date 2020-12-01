<template>
  <div>
    <el-container>
      <el-header>项目实训管理系统</el-header>

      <el-container :style="{minHeight: Height+'px'}">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu :default-openeds="default_index" router>
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-s-order"></i>信息填写</template>
              <el-menu-item index="submit_topic" @click="isClick('1')"><i class="el-icon-arrow-right"></i>发布题目</el-menu-item>
              <el-menu-item index="grade_reply" @click="isClick('1')"><i class="el-icon-arrow-right"></i>答辩成绩填写</el-menu-item>
              <el-menu-item index="grade_guide" @click="isClick('1')"><i class="el-icon-arrow-right"></i>指导成绩填写</el-menu-item>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-search"></i>信息查询</template>
              <el-menu-item index="search_reply" @click="isClick('2')"><i class="el-icon-arrow-right"></i>答辩安排</el-menu-item>
              <el-menu-item index="search_group" @click="isClick('2')"><i class="el-icon-arrow-right"></i>我的小组</el-menu-item>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title"><i class="el-icon-setting"></i>退出登录</template>
              <el-menu-item index="login_out" @click="isClick('3')"><i class="el-icon-arrow-right"></i>登出</el-menu-item>
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