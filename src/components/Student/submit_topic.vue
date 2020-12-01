<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>提交选题</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_submit">
      <el-form :model="form" label-width="100px" :rules="submit_topic_rules" ref="submitTopicRef">
        <el-col :span="12">
          <el-form-item label="组长学号：" prop="leader_id">
              <el-input v-model="form.leader_id" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="题目列表：" prop="topics">
            <el-select v-model="form.topics" placeholder="请选择题目" style="width: 100%" size="small">
              <el-option label="题目一" value="topic1"></el-option>
              <el-option label="题目二" value="topic2"></el-option>
            </el-select>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="submit_topic" style="margin-top: 10vh">提交选题</el-button>
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
          topics: '',
        },
        submit_topic_rules: {
          leader_id: [
            { required: true, message: '请输入组长学号', trigger: 'blur' },
          ],
          topics: [
            { required: true, message: '请选择题目', trigger: 'blur' },
          ],
        },
      }
    },
    methods: {
      submit_topic() {
        this.$refs.submitTopicRef.validate(valid =>{
          console.log(valid);
        });

        this.$confirm('确定提交？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          });
        }).catch(() => {
        });

      }
    }
  }
</script>

<style >
  .div_submit {
    margin-top: 5vh;
    padding-left: 20vw;
  }

</style>