<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>答辩安排</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">答辩安排</h2>
    <div class="div_header"></div>
      <div class="div_search">
        <el-table :data="data" style="width: 90%">
          <el-table-column
              prop="rep_group"
              label="组长学号"
              min-width="25%"
              align="center">
          </el-table-column>
          <el-table-column
              prop="rep_time"
              label="答辩时间"
              min-width="25%"
              align="center">
          </el-table-column>
          <el-table-column
              prop="rep_address"
              label="答辩地点"
              min-width="25%"
              align="center">
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
      this.checkStudent()
      this.getDataFronBack()
    },
    methods:{
      async getDataFronBack(){
        const user = this.toStr(localStorage.getItem("user"))
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/searchreply",{user})
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

  .div_header {
    border-bottom: 1px solid #DDDDDD;
    padding-bottom: 2vh;
  }

</style>