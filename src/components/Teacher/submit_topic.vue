<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>发布信息</el-breadcrumb-item>
      <el-breadcrumb-item>发布题目</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
        <el-col :span="10">
          <el-form-item label="题目：" >
            <el-input   v-model="formLabelAlign.topic" placeholder="请输入内容" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="题目要求：" >
            <el-input type="textarea" placeholder="按住右下角可拖动" v-model="formLabelAlign.topic_requirement" size="big" clearable></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="onSubmit" style="margin-top: 10vh">发布</el-button>
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
      labelPosition: 'top',
      formLabelAlign: {
        topic: '',
        topic_requirement: '',
      }
    };
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