<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>提交项目</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form :model="form" label-width="110px" label-position="left" :rules="submit_project_rules" ref="submitProjectRef">
        <el-col :span="12">
          <el-form-item label="组长学号：" prop="leader_id">
            <el-input v-model="form.leader_id" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="项目名称：" prop="project_name">
            <el-input v-model="form.project_name" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="托管库地址：" prop="repository_address">
            <el-input v-model="form.repository_address" size="small" clearable></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="submit_project" style="margin-top: 10vh">提交项目</el-button>
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
          leader_id: '',
          project_name: '',
          repository_address: '',
        },
        submit_project_rules: {
          leader_id: [
            { required: true, message: '请输入组长学号', trigger: 'blur' },
          ],
          project_name: [
            { required: true, message: '请输入项目名称', trigger: 'blur' },
          ],
          repository_address: [
            { required: true, message: '请输入托管库地址', trigger: 'blur' },
          ],
        },
      }
    },
    methods: {
      submit_project() {
        this.$refs.submitProjectRef.validate(valid =>{
          console.log(valid);
        });
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