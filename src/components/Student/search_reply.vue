<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>答辩安排</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">答辩安排</h2>
      <div class="a">
        <el-table :data="replyData" style="width: 90%">
          <el-table-column
              prop="leader_id"
              label="答辩小组（组长学号）"
              width="180">
          </el-table-column>
          <el-table-column
              prop="reply_time"
              label="答辩时间"
              width="180">
          </el-table-column>
          <el-table-column
              prop="reply_place"
              label="答辩地点">
          </el-table-column>
        </el-table>
      </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        replyData: [{
          leader_id: '41811000',
          reply_time: '2020-12-11 19:30',
          reply_place: 'G103',
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

</style>