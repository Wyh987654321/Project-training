<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>我的成绩</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">我的成绩</h2>
    <div class="div_header"></div>
    <div class="div_search">
      <el-table :data="data" style="width: 90%">
        <el-table-column
            prop="stu_id"
            label="学号"
            min-width="25%"
            align="center">
        </el-table-column>
        <el-table-column
            prop="stu_name"
            label="姓名"
            min-width="25%"
            align="center">
        </el-table-column>
        <el-table-column
            prop="stu_grade1"
            label="小组成绩"
            min-width="25%"
            align="center">
        </el-table-column>
        <el-table-column
            prop="stu_grade2"
            label="个人成绩"
            min-width="25%"
            align="center">
        </el-table-column>
      </el-table>
    </div>

    <div style="margin-top: 10vh;margin-left: 5vw;width: 85%">
      <el-collapse>
        <el-collapse-item title="备注!" class="collapse">
          <div>管理员发放成绩名单后，由小组成员线下相互评分来确定个人最终成绩。</div>
        </el-collapse-item>
      </el-collapse>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        gradeData: [{
          stu_id: '41811000',
          stu_name: '王小虎',
          group_grade: '85',
        }],
        data:[]
      }
    },
    created() {
      this.checkStudent()
      this.getDataFromBack()
    },
    methods:{
      async getDataFromBack(){

        let user = this.toStr(localStorage.getItem("user"))
        const res = await this.$http.post("http://127.0.0.1:9090/Test1/searchgrade", {user})
        // const res = await this.$http.post("http://121.4.96.102:8080/Test1/searchgrade", {user})
        if (res.status==200){
          this.data =res.data
          console.log(this.data);
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

<style>
  .div_search {
    margin-top: 3vh;
    padding-left: 5vw;
  }

  .collapse {
    padding-left: 1vw;
  }

  .div_header {
    border-bottom: 1px solid #DDDDDD;
    padding-bottom: 2vh;
  }

</style>