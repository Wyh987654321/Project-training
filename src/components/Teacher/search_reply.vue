<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>答辩安排</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <template>
        <el-table
            :data="tableData"
            style="width: 90%">
          <el-table-column
              prop="reply_time"
              label="答辩时间"
              width="230">
          </el-table-column>
          <el-table-column
              prop="reply_place"
              label="答辩地点">
          </el-table-column>
        </el-table>
    </template>
    </div>
  </div>

</template>
<script>
export default {
  data() {
    return {
      tableData: [{
        reply_time: '12月15日15：00-17：00',
        reply_place:'H301',
      },]
    }
  }
}
</script>
