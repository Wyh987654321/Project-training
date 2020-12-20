<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>填写分组</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_submit3">
      <el-form :label-position="labelPosition" label-width="100px" :model="formLabelAlign" :rules="submit_group_rules" ref="submitGroupRef">
        <el-col :span="10">
          <el-form-item label="组长学号：" prop="leader_id">
            <el-input v-model="formLabelAlign.leader_id" size="small" clearable></el-input>
          </el-form-item>
          <div style="width:100%;text-align:center">
            <el-button type="primary" v-on:click.once="submit_group" :style="background" :disabled="isDisabled">提交分组</el-button>
          </div>
        </el-col>
      </el-form>
    </div>

    <div style="position: fixed;bottom: 20vh;margin-left: 5vw;width: 70%">
      <el-collapse>
        <el-collapse-item title="备注!" class="collapse">
          <div>组长提交分组后，请重新登陆系统，方可进行组长操作。</div>
        </el-collapse-item>
      </el-collapse>
    </div>

  </div>
</template>

<script>

export default {
  data() {
    return {
      isDisabled:false,
      labelPosition: 'left',
      formLabelAlign: {
        leader_id: '',
        my_id: this.toStr(localStorage.getItem('user'))
      },
      submit_group_rules: {
        leader_id: [
          { required: true, message: '请输入组长学号', trigger: 'blur' },
        ],
      },
      background: {
        marginTop: '5vh',
        backgroundColor: '#409EFF',
        borderColor: '#409EFF'
      },
    };
  },
  created() {
    this.checkStudent()
    this.isSubmit()
  },
  beforeDestroy() {
    clearTimeout(this.timer);
  },
  methods: {
    async isSubmit(){
      console.log("???");
      let command ="判断是否选过组"
      let user =this.toStr(localStorage.getItem('user'))
      const res = await this.$http.post("http://121.4.96.102:8080/Test1/getStatus",{command,user})
      console.log(res)
      if (res.data==="选过了"){
        this.background.backgroundColor = '#C0C0C0'
        this.background.borderColor = '#C0C0C0'
        this.isDisabled = true
        console.log(this.isDisabled)
      }
    },
     submit_group() {
       let axios =this.$http
       let formLabelAlign =this.formLabelAlign
       let message =this.$message
       let valid_out = false
      this.$refs.submitGroupRef.validate(valid =>{
        valid_out = valid
      });
       if (!valid_out) {
         return
       }
      this.$confirm('确定提交？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then( async function (){
        console.log("组长学号 ="+formLabelAlign.leader_id);
        const res = await axios.post("http://121.4.96.102:8080/Test1/selectgroup",formLabelAlign
        )
        if(res.data==="分组成功"){
          message.success("分组成功")

          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);

        }else {
          message.error("分组失败,请检查输入信息是否正确")
        }
          }

      )

    },

    checkStudent() {
      let user = this.toStr(localStorage.getItem('user'))
      if (!user || user.substring(0,1)!=='4') {
        this.$router.replace('/login')
      }
    }

  }
}
</script>

<style>
  .div_submit3 {
    margin-top: 10vh;
    padding-left: 20vw;
  }

</style>