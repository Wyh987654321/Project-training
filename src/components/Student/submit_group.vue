<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>填写分组</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign" :rules="submit_group_rules" ref="submitGroupRef">
        <el-col :span="10">
          <el-form-item label="组长学号：" prop="leader_id">
            <el-input v-model="formLabelAlign.leader_id" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="我的学号：" prop="my_id">
            <el-input v-model="formLabelAlign.my_id" size="small" clearable></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="submit_group" style="margin-top: 10vh">提交分组</el-button>
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
        leader_id: '',
        my_id: '',
      },
      submit_group_rules: {
        leader_id: [
          { required: true, message: '请输入组长学号', trigger: 'blur' },
        ],
        my_id: [
          { required: true, message: '请输入我的学号', trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    submit_group() {
      this.$refs.submitGroupRef.validate(valid =>{
        console.log(valid);
      });
    }
  }
}
</script>

<style>
  .a {
    margin-top: 5vh;
    padding-left: 20vw;
  }

</style>