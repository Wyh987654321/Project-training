<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>成绩填写</el-breadcrumb-item>
      <el-breadcrumb-item>答辩成绩填写</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form ref="form" :model="form" label-width="100px">
        <el-col :span="12">
          <el-form-item label="小组选择">
            <el-select v-model="form.groups" placeholder="请选择小组" style="width: 100%" size="small">
              <el-option label="小组一" value="group1"></el-option>
              <el-option label="小组二" value="group2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分数：">
            <el-input v-model="form.reply_grade" placeholder="请输入成绩" size="small" clearable></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="onSubmit" style="margin-top: 10vh">提交成绩</el-button>
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
        groups: '',
        reply_grade: '',
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