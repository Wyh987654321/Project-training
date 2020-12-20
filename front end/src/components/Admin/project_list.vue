<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>名单列表</el-breadcrumb-item>
      <el-breadcrumb-item>课题列表</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_header">
      <el-input
          @input="handleSearch"
          style="width: 14vw;"
          v-model="search"
          size="medium"
          placeholder="输入列值即可搜索">
      </el-input>
    </div>

    <el-table
        style="padding-left: 2vw;width: 100%;"
        ref="parentTable"
        :row-key='handleReserve'
        :data="showData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        @expand-change="expandChange"
        :expand-row-keys="expands"
        @sort-change="sort_change">

      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="组长信息：" v-for="item in props.row.groups" :key="item">
              <span>{{item}}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
          prop="top_name"
          label="课题名称"
          min-width="25%">
      </el-table-column>

      <el-table-column
          prop="top_teacher"
          label="指导老师"
          min-width="10%">
      </el-table-column>

      <el-table-column
          prop="group_num"
          label="已选小组数量"
          align="center"
          sortable="custom"
          min-width="25%">
      </el-table-column>

      <el-table-column label="课题介绍" min-width="15%">
        <template scope="scope">
          <el-button
              size="medium"
              type="text"
              @click="view_details(scope.row)">查看详情
          </el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" min-width="25%">
        <template scope="scope">
          <el-button
              size="medium"
              type="primary"
              @click="modify_group(scope.row)">编辑小组
          </el-button>
          <el-button
              size="medium"
              type="danger"
              @click="remove_topic(scope.row)">删除课题
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 1vh">
      <el-pagination align="center"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="currentPage"
                     :page-sizes="[10,20,50,100]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="showData.length">
      </el-pagination>
    </div>

    <el-dialog title="编辑小组"
               top="10vh"
               :visible.sync="dialogFormVisible_1"
               center
               width="26vw">

      <el-form
          label-width="90px"
          ref="modifyRef">
        <el-form-item :style="form_item" label="课题名称：">
          <div style="display: inline-block;margin-right: 1vw;">
            {{tempInfo[4]}}
          </div>
        </el-form-item>
        <el-form-item :style="form_item" label="指导教师：">
          <div style="display: inline-block;margin-right: 1vw;">
            {{tempInfo[6]}}
          </div>
        </el-form-item>
        <el-form-item :style="form_item" label="组长信息：" v-for="(item,index) in tempInfo[1]" :key="index">
          <div style="display: inline-block;width: 80%">
            {{tempInfo[1][index]}}
          </div>
          <div style="display: inline-block;width: 20%">
            <el-button type="text" size="small" @click="removeGroup(index)">移除</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="查看详情"
               top="10vh"
               :visible.sync="dialogFormVisible_2"
               center
               width="35vw">

      <el-form
          label-width="90px"
          ref="modifyRef">
        <el-form-item :style="form_item" label="课题名称：">
            {{tempInfo[4]}}
        </el-form-item>
        <el-form-item :style="form_item" label="指导教师：">
            {{tempInfo[6]}}
        </el-form-item>
        <el-form-item :style="form_item" label="项目Q群：">
          {{tempInfo[5]}}
        </el-form-item>
        <el-form-item :style="form_item" label="课题介绍：">
            {{tempInfo[3]}}
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        search:'',
        selectValue:'',

        dialogFormVisible_1: false,
        dialogFormVisible_2: false,

        currentPage: 1,
        total: 100,
        pageSize: 10,

        form_item: {
          marginBottom: "1vh",
        },

        tableData: [],

        showData: [],
        expands: [],
        tempList: [],
        tempInfo: [],
        propType: '',

      }
    },

    //页面创建时，用map方法取出所有的课题放到筛选器中
    created() {
      this.checkAdmin()
      this.getDataFromBack()
    },

    methods: {
      expandChange(row, expandedRows) {
        let that = this
        if (expandedRows.length) {
          that.expands = []
          if (row) {
            that.expands.push(row.top_id)
          }
        }
        else {
          that.expands = []
        }
      },

      findTopicIndex(top_id) {
        let index = this.tableData.findIndex(function(item) {
          if (item.top_id === top_id)
            return true
        })
        return index
      },

      my_desc_sort(a, b) {
        return b[this.propType] - a[this.propType];
      },

      my_asc_sort(a, b) {
        return a[this.propType] - b[this.propType];
      },

      sort_change(column) {
        this.propType = column.prop;
        if (column.order === "descending") {
          this.tableData.sort(this.my_desc_sort);
        } else if (column.order === "ascending") {
          this.tableData.sort(this.my_asc_sort);
        }
        this.showData=[...this.tableData]
        this.currentPage = 1;
      },

      handleSearch() {
        let search = this.search

        if(search.length!==0) {
          this.$refs.parentTable.clearFilter()
        }

        this.showData = [...this.tableData.filter(data => !search
            || data.top_name.includes(search)
            || data.top_teacher.includes(search)
        )]

        if(search==='') {
          this.currentPage = 1
          this.showData=[...this.tableData]
        }
      },

      //设置表格数据的唯一值,防止翻页过后覆盖
      handleReserve(row) {
        return row.top_id
      },

      handleSizeChange(val) {
        this.currentPage = 1;
        this.pageSize = val;
      },

      handleCurrentChange(val) {
        this.currentPage = val;
      },

      removeGroup(index) {
        this.$confirm('确定删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          let info = this.tempInfo[1][index]
          let command ="1"
          //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admtopic",{info,command}
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admtopic",{info,command}
          )
          if (res.data==="移除成功"){
            this.$message.success("移除成功")
          }else {
            this.$message.error("移除失败")
          }
          clearTimeout(this.timer);  //清除延迟执行
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);
          this.dialogFormVisible_1 = false

        }).catch(() => {
        });
      },

      modify_group(info) {
        this.tempInfo = [...Object.values(info)]
        this.dialogFormVisible_1 = true
      },

      remove_topic(info) {
        this.$confirm('确定删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          this.tempInfo = [...Object.values(info)]
          let id =this.tempInfo[2]
          let command ="2"
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admtopic",{id,command}
          //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admtopic",{id,command}
          )
          if (res.data==="移除成功"){
            this.$message.success("移除成功")
            let index = this.findTopicIndex(this.tempInfo[2])
            this.tableData.splice(index, 1)
            this.showData = [...this.tableData]
          }else {
            this.$message.error("移除失败")
          }
        }).catch(() => {
        });
      },

      view_details(info) {
        this.tempInfo = [...Object.values(info)]
        this.dialogFormVisible_2 = true
      },

      async getDataFromBack(){

        const res = await this.$http.get("http://121.4.96.102:8080/Test1/admtopic"
        //const res = await this.$http.get("http://127.0.0.1:9090/Test1/admtopic"
      )
        if (res.status==200){
          this.tableData =res.data
          this.showData = [...this.tableData]
          console.log(this.tableData)
        }
      },

      checkAdmin (){
        let user = this.toStr(localStorage.getItem('user'))
        if (!user || user.substring(0,1)!=='a') {
          this.$router.replace('/login')
        }
      }

    }
  }
</script>

<style>
  .div_header {
    border-bottom: 1px solid #DDDDDD;
    padding-bottom: 2vh;
    text-align: end;

  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item{
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

</style>