<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right" >
      <el-breadcrumb-item :to="{ path: '/tea_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>指导小组</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">指导小组</h2>
    <div class="div_header"></div>
    <div class="div_search">

      <el-table
          :key="group.stu_group"
          v-for="group in data"
          :data="group"
          style="width: 100%;margin-bottom: 5vh"
          :row-class-name="tableRowClassName">

        <el-table-column
            prop="stu_id"
            label="学号"
            min-width="15%">
        </el-table-column>
        <el-table-column
            prop="stu_name"
            label="姓名"
            min-width="12%">
        </el-table-column>
        <el-table-column
            prop="stu_profession"
            label="专业"
            min-width="13%">
        </el-table-column>
        <el-table-column
            prop="stu_phone"
            label="电话"
            min-width="15%">
        </el-table-column>
        <el-table-column
            prop="stu_email"
            label="邮箱"
            min-width="25%">
        </el-table-column>
        <el-table-column
            prop="topic"
            label="选题"
            min-width="20%">
        </el-table-column>
      </el-table>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        data:[]
      }
    },
    created() {
      this.checkTeacher()
      this.getDataFromBack()
    },
    methods: {
      tableRowClassName({rowIndex}) {
        if (rowIndex === 0) {
          return 'success-row';
        }
      },
      async getDataFromBack(){

        let teacher = this.toStr(localStorage.getItem("user"))
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/tea_searchgroup",{teacher})
        if (res.status==200){

          this.data =res.data
          console.log(this.data)
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

<style>
  .el-table .success-row {
    background: #f0f9eb;
  }

  .div_search {
    margin-top: 3vh;
    padding-left: 5vw;
  }

  .div_header {
    border-bottom: 1px solid #DDDDDD;
    padding-bottom: 2vh;
  }

</style>