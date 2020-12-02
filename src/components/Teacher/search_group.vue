<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" >
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>我的小组</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="a">
      <h2>小组1</h2>
        <el-table
            :data="tableData1"
            style="width: 90%"
            :row-class-name="tableRowClassName">
          <el-table-column
              prop="id"
              label="学号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="姓名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="phone_num"
              label="电话"
              width="180">
          </el-table-column>
          <el-table-column
              prop="email"
              label="邮箱">
          </el-table-column>
        </el-table>
      <br>
      <h2>小组2</h2>
      <el-table
          :data="tableData2"
          style="width: 90%"
          :row-class-name="tableRowClassName">
        <el-table-column
            prop="id"
            label="学号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="phone_num"
            label="电话"
            width="180">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱">
        </el-table-column>
      </el-table>
      </div>
    </div>
</template>




<script>
export default {
  methods: {
    tableRowClassName({rowIndex}) {
      if (rowIndex === 0) {
        return 'success-row';
      }
    }
  },
  data() {
    return {
      tableData1: [{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },],
      tableData2: [{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },{
        id: '41811002',
        name: '王元浩',
        phone_num: '10086',
        email:'123456@qq.com',
      },]
    }
  }
}
</script>

<style>
.el-table .success-row {
  background: #f0f9eb;
}
</style>