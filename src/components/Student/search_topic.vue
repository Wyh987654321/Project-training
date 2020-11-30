<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>课题介绍</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>项目名称</span>
        </div>
        <div class="text item"> 指导老师：</div>
        <div class="text item"> 老师邮箱：</div>
        <el-collapse>
          <el-collapse-item title="课题介绍">
            <div>这里是一份详细的课题介绍</div>
          </el-collapse-item>
        </el-collapse>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>项目名称</span>
        </div>
        <div class="text item"> 指导老师：</div>
        <div class="text item"> 老师邮箱：</div>
        <el-collapse>
          <el-collapse-item title="课题介绍">
            <div>这里是一份详细的课题介绍</div>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {

      };
    }
  }
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both;
  }

  .box-card {
    width: 50vw;
    margin-top: 5vh;
  }

  .a {
    margin-top: 5vh;
    padding-left: 10vw;
  }

</style>