<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>我的成绩</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">我的成绩</h2>
    <div class="a">
      <el-table :data="gradeData" style="width: 90%">
        <el-table-column
            prop="stu_id"
            label="学号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="stu_name"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="group_grade"
            label="小组成绩">
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
        }]
      }
    }
  }
</script>

<style>
  .a {
    margin-top: 3vh;
    padding-left: 5vw;
  }

  .collapse {
    padding-left: 1vw;
  }

</style>