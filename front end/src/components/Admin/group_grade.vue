<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>成绩查询</el-breadcrumb-item>
      <el-breadcrumb-item>小组成绩</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_header">
      <el-input
          @input="handleSearch"
          style="width: 14vw;"
          v-model="search"
          size="medium"
          placeholder="输入列值即可搜索">
      </el-input>
      <el-button
          size="medium"
          type="success"
          style="margin-left: 2vw;"
          @click="modifyGrade">调整成绩比例
      </el-button>
    </div>

    <el-table
        style="padding-left: 2vw;width: 100%;"
        ref="parentTable"
        :row-key="handleReserve"
        :data="showData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        @sort-change="sort_change">

      <el-table-column
          prop="gro_id"
          label="组长学号"
          min-width="14%">
      </el-table-column>

      <el-table-column
          prop="leader"
          label="组长姓名"
          min-width="14%">
      </el-table-column>


      <el-table-column
          prop="gro_teacher"
          label="指导老师"
          min-width="14%">
      </el-table-column>

      <el-table-column
          prop="gro_topic"
          label="小组选题"
          min-width="14%">
      </el-table-column>

      <el-table-column
          prop="grade1"
          label="指导成绩"
          min-width="14%"
          sortable="custom">
      </el-table-column>

      <el-table-column
          prop="grade2"
          label="答辩成绩"
          min-width="14%"
          sortable="custom">
      </el-table-column>

      <el-table-column
          prop="grade3"
          label="最终成绩"
          min-width="16%"
          sortable="custom">
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

    <el-dialog title="调整成绩比例"
               top="10vh"
               @close="closeDialog"
               :visible.sync="dialogFormVisible"
               center
               width="30vw">

      <el-form label-width="140px"
               :rules="rate_rules"
               :model="rateData"
               ref="newRateRef"
               style="width: 80%">
        <el-form-item :style="form_item" label="指导成绩比例：" prop="guide_rate">
          <el-input v-model="rateData.guide_rate" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩成绩比例：" >
          {{(1-this.rateData.guide_rate).toFixed(1)}}
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible_2 = false">取 消 </el-button>
        <el-button type="primary" @click="confirm_newRate">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        search: '',
        selectValue: '',

        dialogFormVisible: false,

        form_item: {
          marginBottom: "3vh",
        },

        rate_rules: {
          guide_rate: [
            {required: true, message: '请输入指导成绩比例', trigger: 'blur'},
          ],
        },

        currentPage: 1,
        total: 100,
        pageSize: 10,

        tableData: [],

        filter_profession: [
          {
            text: '信管',
            value: '信管'
          },
          {
            text: '电商',
            value: '电商'
          },
          {
            text: '计科',
            value: '计科'
          },
          {
            text: '金信',
            value: '金信'
          },],

        rateData: {
          guide_rate: 0.4,
        },

        showData: [],
        tempList: [],
        expands:[],
        propType: '',

      }
    },

    //页面创建时，用map方法取出所有的课题放到筛选器中
    created() {
      this.checkAdmin()
      this.getDataFromBack()
      this.getRateFromBack()
      // this.selectList = this.tableData.map(item => {
      //   return {
      //     text: item.project,
      //     value: item.project
      //   }
      //
      // });
    },

    methods: {
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


      closeDialog() {
        this.$refs.newRateRef.resetFields();
      },

      handleSearch() {
        let search = this.search

        if (search.length !== 0) {
          this.$refs.parentTable.clearFilter()
        }

        this.showData = [...this.tableData.filter(data => !search
            || data.gro_id.includes(search)
            || data.leader.includes(search)
            || data.gro_teacher.includes(search)
            || data.gro_topic.includes(search)
        )]

        if (search === '') {
          this.currentPage = 1
          this.showData = [...this.tableData]
        }
      },

      //设置表格数据的唯一值,防止翻页过后覆盖
      handleReserve(row) {
        return row.gro_id
      },

      handleSizeChange(val) {
        this.currentPage = 1;
        this.pageSize = val;
      },

      handleCurrentChange(val) {
        this.currentPage = val;
      },
      async getDataFromBack(){
        const res = await this.$http.get("http://127.0.0.1:9090/Test1/groupgrade"
         //const res = await this.$http.get("http://121.4.96.102:8080/Test1/groupgrade"
        )
        if (res.status==200){
          this.tableData =res.data
          this.showData = [...this.tableData]
          console.log(this.tableData)
        }
      },
      modifyGrade() {
        this.dialogFormVisible = true
      },

      async confirm_newRate() {
        let valid_out = false;
        this.$refs.newRateRef.validate(valid =>{
          valid_out = valid
        });
        if (!valid_out) {
          return
        }
        this.$confirm('确定修改？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          let rate = this.rateData.guide_rate
          let command = "设置比例"
          const res = await this.$http.post("http://127.0.0.1:9090/Test1/groupgrade",{rate,command}
           //const res = await this.$http.post("http://121.4.96.102:8080/Test1/groupgrade",{rate,command}
          )
          if (res.data==="修改成功"){
            this.dialogFormVisible = false
            this.$message.success("设置成功")
            clearTimeout(this.timer);  //清除延迟执行
            this.timer = setTimeout(()=>{   //设置延迟执行
              location.reload()
            },1000);
          }else {
            this.message.error("设置失败")
          }
        }).catch(() => {
        });

      },
      async getRateFromBack(){
        let command="获取比例"
        const res = await this.$http.post("http://127.0.0.1:9090/Test1/groupgrade",{command}
         //const res = await this.$http.post("http://121.4.96.102:8080/Test1/groupgrade",{command}
        )
        if (res.status==200){
          console.log(res.data);
          this.rateData.guide_rate = parseFloat(res.data)
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