<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>提交项目</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form ref="form" :model="form" label-width="100px" label-position="left">
        <el-col :span="12">
          <el-form-item label="组长学号：">
            <el-input v-model="form.leader_num" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="项目名称：">
            <el-input v-model="form.project_name" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="托管库地址：">
            <el-input v-model="form.repository_address" size="small" clearable></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="onSubmit" style="margin-top: 10vh">提交项目</el-button>
          </div>
        </el-col>
      </el-form>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          leader_num: '',
          project_name: '',
          repository_address: '',
        }
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      }
    }
  }
</script>

<style >
  .a {
    margin-top: 5vh;
    padding-left: 20vw;
  }
</style>