<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/tea_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息填写</el-breadcrumb-item>
      <el-breadcrumb-item>成绩填写</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="flex: 1;display:flex;flex-flow: column;">
      <h2 style="margin-top: 5vh;margin-left: 3vw">答辩成绩填写</h2>
      <div class="div_header"></div>
      <div class="div_search2">
        <el-form :model="grade_reply" label-width="100px" :rules="grade_reply_rules" ref="gradeReplyRef">
          <el-col :span="12">
            <el-form-item label="小组选择：" prop="groups">
              <el-select @change="getGroup1" v-model="grade_reply.groups" placeholder="请选择小组" style="width: 100%" size="small">
                <el-option v-for="group in groups1" :key="group"  :value='group'></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="成绩：" prop="reply_grade">
              <el-input v-model="grade_reply.reply_grade" placeholder="请输入分数" size="small" clearable></el-input>
            </el-form-item>
            <div style="width:100%;text-align:center">
              <el-button type="primary" @click="submit_grade1" style="margin-top: 5vh">提交成绩</el-button>
            </div>
          </el-col>
        </el-form>
      </div>

      <h2 style="margin-top: 5vh;margin-left: 3vw">指导成绩填写</h2>
      <div class="div_header"></div>
      <div class="div_search2">
        <el-form :model="grade_guide" label-width="100px" :rules="grade_guide_rules" ref="gradeGuideRef">
          <el-col :span="12">
            <el-form-item label="小组选择：" prop="groups">
              <el-select @change="getGroup2" v-model="grade_guide.groups" placeholder="请选择小组" style="width: 100%" size="small">
                <el-option v-for="group in groups2" :key="group"  :value='group'></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="成绩：" prop="guide_grade">
              <el-input v-model="grade_guide.guide_grade" placeholder="请输入分数" size="small" clearable></el-input>
            </el-form-item>
            <div style="width:100%;text-align:center">
              <el-button type="primary" @click="submit_grade2" style="margin-top: 5vh">提交成绩</el-button>
            </div>
          </el-col>
        </el-form>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        grade_reply: {
          groups: '',
          reply_grade: '',
          teacher: this.toStr(localStorage.getItem("user"))
        },
        grade_guide: {
          groups: '',
          guide_grade: '',
          teacher: this.toStr(localStorage.getItem("user"))
        },
        grade_reply_rules: {
          groups: [
            { required: true, message: '请选择小组', trigger: 'blur' },
          ],
          reply_grade: [
            { required: true, message: '请输入成绩', trigger: 'blur' },
          ],
        },
        grade_guide_rules: {
          groups: [
            { required: true, message: '请选择小组', trigger: 'blur' },
          ],
          guide_grade: [
            { required: true, message: '请输入成绩', trigger: 'blur' },
          ],
        },
        groups1: [],
        groups2: [],
      }
    },
    created() {
      this.checkTeacher()
      this.getTopicFromBack1()
      this.getTopicFromBack2()
    },
    methods: {
      submit_grade1() {
        let valid_out = false

        this.$refs.gradeReplyRef.validate(valid =>{
          valid_out = valid;
        });
        if (!valid_out) {
          return
        }
        this.$confirm('确定提交？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          let command ="2"
          let grade_reply =this.grade_reply
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/tea_writereply",{grade_reply,command})
          if(res.data==="填写成功"){
            this.$message.success("填写成功")
          }else {
            this.$message.error("填写失败,请检查输入信息是否正确")
          }
        }).catch(() => {
        });
      },
      submit_grade2() {
        let valid_out = false
        this.$refs.gradeGuideRef.validate(valid =>{
          valid_out = valid;
        });
        if (!valid_out) {
          return
        }
        this.$confirm('确定提交？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/tea_writeguide",this.grade_guide)
          if(res.data==="填写成功"){
            this.$message.success("填写成功")
          }else {
            this.$message.error("填写失败,请检查输入信息是否正确")
          }
        }).catch(() => {
        });
      },
      getGroup1(val){
        this.grade_reply.groups=val
      },
      async getTopicFromBack1(){
        let command="1"
        const teacher =this.toStr(localStorage.getItem("user"))
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/tea_writereply",{teacher,command})
        if (res.status==200){
          this.groups1 =res.data
          console.log(res.data)

        }
      },
      getGroup2(val){
        this.grade_guide.groups=val
      },
      async getTopicFromBack2(){
        const teacher =this.toStr(localStorage.getItem("user"))
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/tea_writeguide",{teacher})
        if (res.status==200){
          this.groups2 =res.data
        }
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
  .div_search2 {
    margin-top: 3vh;
    padding-left: 20vw;
  }
  .div_header {
    border-bottom: 1px solid #DDDDDD;
    padding-bottom: 2vh;
  }

</style>