<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>我的项目</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">小组成员</h2>
      <div class="div_search">
        <el-table
            :data="groupData"
            style="width: 90%"
            :row-class-name="tableRowClassName">
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
              prop="stu_phone"
              label="电话"
              width="180">
          </el-table-column>
          <el-table-column
              prop="stu_email"
              label="邮箱">
          </el-table-column>
        </el-table>
      </div>

    <h2 style="margin-top: 5vh;margin-left: 3vw">指导老师</h2>
      <div class="div_search">
        <el-table :data="teacherData" style="width: 90%">
          <el-table-column
              prop="tea_name"
              label="姓名"
              width="180">
          </el-table-column>
          <el-table-column
              prop="tea_phone"
              label="电话"
              width="180">
          </el-table-column>
          <el-table-column
              prop="tea_email"
              label="邮箱">
          </el-table-column>
        </el-table>
      </div>

    <h2 style="margin-top: 5vh;margin-left: 3vw">所选项目</h2>
      <div class="div_search">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>项目名称</span>
          </div>
          <div class="text item"> QQ群：</div>
          <el-collapse>
            <el-collapse-item title="课题介绍">
              <div>这里是一份详细的课题介绍</div>
            </el-collapse-item>
          </el-collapse>
        </el-card>
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
        groupData: [{
          stu_id: '41811000',
          stu_name: '王小虎',
          stu_phone: '19912345678',
          stu_email: '123456789@qq.com'
        }, {
          stu_id: '41811000',
          stu_name: '王小虎',
          stu_phone: '19912345678',
          stu_email: '123456789@qq.com'
        }, {
          stu_id: '41811000',
          stu_name: '王小虎',
          stu_phone: '19912345678',
          stu_email: '123456789@qq.com'
        }, {
          stu_id: '41811000',
          stu_name: '王小虎',
          stu_phone: '19912345678',
          stu_email: '123456789@qq.com'
        }],
        teacherData: [{
          tea_name: '王大虎',
          tea_phone: '18012345678',
          tea_email: '987654321@qq.com'
        }]
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

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both;
  }

  .box-card {
    width: 50vw;
    margin-top: 5vh;
  }

</style>