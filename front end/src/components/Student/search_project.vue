<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>我的项目</el-breadcrumb-item>
    </el-breadcrumb>
    <!--            :data="groupData"-->
    <h2 style="margin-top: 5vh;margin-left: 3vw">小组成员</h2>
      <div class="div_header"></div>
      <div class="div_search">
        <el-table
            :data="data[0]"
            style="width: 100%"
            :row-class-name="tableRowClassName">
          <el-table-column
              prop="stu_id"
              label="学号"
              min-width="15%">
          </el-table-column>
          <el-table-column
              prop="stu_name"
              label="姓名"
              min-width="15%">
          </el-table-column>
          <el-table-column
              prop="stu_profession"
              label="专业"
              min-width="15%">
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
          <el-table-column label="操作" min-width="15%">
            <template scope="scope">
              <el-button
                  size="medium"
                  type="text"
                  @click="removeMember(scope.row)"
                  v-if="scope.$index!==0"
                  v-show="isLeader==='true'">移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    <h2 style="margin-top: 5vh;margin-left: 3vw">指导老师</h2>
      <div class="div_header"></div>
      <div class="div_search">
        <el-table :data="data[1]" style="width: 90%">
          <el-table-column
              prop="tea_name"
              label="姓名"
              min-width="15%">
          </el-table-column>
          <el-table-column
              prop="tea_phone"
              label="电话"
              min-width="15%">
          </el-table-column>
          <el-table-column
              prop="tea_email"
              label="邮箱"
              min-width="15%">
          </el-table-column>
        </el-table>
      </div>

    <h2 style="margin-top: 5vh;margin-left: 3vw">所选项目</h2>
    <div class="div_header"></div>
    <div class="div_search_topic">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>{{topic.top_name}}</span>
        </div>
        <div class="text item"> 指导老师：{{topic.top_teacher}}</div>
        <div class="text item"> 项目Q群：{{topic.top_qq}}</div>
        <el-collapse>
          <el-collapse-item title="课题介绍：">
            <div>{{topic.top_intro}}</div>
          </el-collapse-item>
        </el-collapse>
      </el-card>
    </div>

  </div>
</template>

<script>
  export default {
    created() {
      this.checkStudent()
      this.getDataFromBack()
    },
    methods: {
      tableRowClassName({rowIndex}) {
        if (rowIndex === 0) {
          return 'success-row';
        }
      },
      async getDataFromBack(){
        let user = this.toStr(localStorage.getItem("user"))
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/searchproject",{user})
       // const res = await this.$http.post("http://127.0.0.1:9090/Test1/searchproject",{user})
        if (res.status==200){
          this.data =res.data
          if (res.data[2]!=null){
            this.topic = res.data[2]
          }else {
            this.topic.top_id=""
            this.topic.top_name=""
            this.topic.top_qq=""
            this.topic.top_teacher=""
            this.topic.top_intro=""
          }

        }
      },
      removeMember(info) {
        this.tempInfo = [...Object.values(info)]
        this.$confirm('确定删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          let command ="3"
          let stu_info =this.tempInfo[4]
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admgroup",{command,stu_info}
          // const res = await this.$http.post("http://127.0.0.1:9090/Test1/admgroup",{command,stu_info}
          )
          if (res.data==="移除成功"){
            this.$message.success("移除成功")
            clearTimeout(this.timer);  //清除延迟执行
            this.timer = setTimeout(()=>{   //设置延迟执行
              location.reload()
            },1000);
          }else{
            this.$message.error("移除失败")
          }
        }).catch(() => {
        });
      },
      checkStudent() {
        let user = this.toStr(localStorage.getItem('user'))
        if (!user || user.substring(0,1)!=='4') {
          this.$router.replace('/login')
        }
      }
    },
    data() {
      return {
        teacherData: [
         ],
        data:[],
        topic:{},
        isLeader: localStorage.getItem('isLeader'),
        tempInfo: []
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

  .div_header {
    border-bottom: 1px solid #DDDDDD;
    padding-bottom: 2vh;
  }

</style>