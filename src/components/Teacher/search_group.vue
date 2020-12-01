<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>我的小组</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
        <el-table
            :data="tableData"
            style="width: 90%">
          <el-table-column
              prop="group_num"
              label="组长学号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="group_leader_name"
              label="组长"
              width="180">
          </el-table-column>
          <el-table-column
              prop="number"
              label="学号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="姓名">
          </el-table-column>
        </el-table>
      </div>
    </div>
</template>




<script>
export default {
  data() {
    return {
      tableData: [{
        group_num: '41811002',
        group_leader_name: '王元浩',
        number: '41811001',
        name:'向宏宇',
      },]
    }
  }
}
</script>