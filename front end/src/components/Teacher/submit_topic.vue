<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/tea_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>发布题目</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <el-form :label-position="labelPosition" label-width="80px" :model="form" :rules="submit_topic_rules" ref="submitTopicRef">
        <el-col :span="10">
          <el-form-item label="题目：" prop="top_name">
            <el-input   v-model="form.top_name" placeholder="请输入内容" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="题目Q群：" prop="top_qq">
            <el-input   v-model="form.top_qq" placeholder="请输入内容" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="题目介绍：" prop="top_intro">
            <el-input type="textarea" placeholder="按住右下角可拖动" v-model="form.top_intro" size="small" :autosize="{ minRows: 2, maxRows: 8}"></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" @click="submit_topic" style="margin-top: 10vh">发布</el-button>
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
      form: {
        top_name: '',
        top_intro: '',
        top_qq: '',
        top_teacher:this.toStr(localStorage.getItem("user"))
      },
      submit_topic_rules: {
        top_name: [
          { required: true, message: '请输入课题名称', trigger: 'blur' },
        ],
        top_intro: [
          { required: true, message: '请输入课题介绍', trigger: 'blur' },
        ],
        top_qq: [
          { required: true, message: '请输入课题Q群', trigger: 'blur' },
        ]

      }
    };
  },

  created() {
    this.checkTeacher()
  },

  methods: {
    submit_topic() {
      let valid_out = false
      this.$refs.submitTopicRef.validate(valid =>{
        valid_out = valid;
      });
      if (!valid_out) {
        return
      }
      const message =this.$message
      this.$confirm('确定提交？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/tea_submittopic",this.form)
        if(res.data==="创建题目成功"){
          message.success("创建题目成功")
        }else {
          message.error("创建题目失败,请检查输入信息是否正确")
        }
      }).catch(() => {
      });
    },
    checkTeacher() {
      let user = this.toStr(localStorage.getItem('user'))
      if (!user || user.substring(0,1)==='4' || user.substring(0,1)==='a') {
        this.$router.replace('/login')
      }
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