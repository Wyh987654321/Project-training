<template>
<div>
  <div >
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>提交项目</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
    <div class="div_submit2" >
<!--      action="http://127.0.0.1:9090/Test1/upload"-->
    <el-upload
        class="upload-demo"
        ref="upload"
        action="http://121.4.96.102:8080/Test1/upload"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :auto-upload="false">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button :style="background" size="small" type="success" v-on:click="submitUpload" :disabled="isDisabled">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">上传项目文件压缩包</div>
      <div slot="tip" class="el-upload__tip">格式：小组号_指导教师_题目名称.zip</div>
    </el-upload>
    </div>

</div>
</template>

<script>
  export default {
    data() {
      return {
        isDisabled:false,
        leader_id:this.toStr(localStorage.getItem("user")),
        background: {
          marginLeft: '10px',
          backgroundColor: '#409EFF',
          borderColor: '#409EFF'
        },
      }
    },
    created() {
      this.checkStudent()
      this.isSubmit()
    },
    beforeDestroy() {
      clearTimeout(this.timer);
    },
    methods: {
      submitUpload() {
        this.$refs.upload.submit();

      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      async handleSuccess(res){
        if (res==="上传成功"){
          await this.success()
          this.$message.success("上传成功")
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);

        }
        else{
          this.$message.error("上传失败")
        }
      },

      async success(){
        let id123456 = this.leader_id

        const res = await this.$http.post("http://121.4.96.102:8080/Test1/selecttopic",{id123456})
        //const res = await this.$http.post("http://127.0.0.1:9090/Test1/selecttopic",{id123456})
        if (res==="修改成功"){
          console.log("修改成功")

        }
      },
      async isSubmit(){
        let command ="判断是否提交过项目"
        let user =this.toStr(localStorage.getItem('user'))
        // const res = await this.$http.post("http://127.0.0.1:9090/Test1/getStatus",{command,user})
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/getStatus",{command,user})
        if (res.data==="提交过了"){
          this.background.backgroundColor = '#C0C0C0'
          this.background.borderColor = '#C0C0C0'
          this.isDisabled = true
        }
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

<style >
  .div_submit2 {
    margin-top: 15vh;
    padding-left: 30vw;
    width: 20%;
  }

</style>