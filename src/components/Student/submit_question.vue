<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>提交选题</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form ref="form" :model="form" label-width="100px">
        <el-col :span="12">
          <el-form-item label="组长学号：">
              <el-input v-model="form.leader_num" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="题目列表：">
            <el-select v-model="form.questions" placeholder="请选择题目" style="width: 100%" size="small">
              <el-option label="题目一" value="question1"></el-option>
              <el-option label="题目二" value="question2"></el-option>
            </el-select>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="onSubmit" style="margin-top: 10vh">提交选题</el-button>
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
          questions: '',
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