<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>成绩查询</el-breadcrumb-item>
      <el-breadcrumb-item>成绩名单</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_header">
      <div style="display: inline-block">
        <el-input
            @input="handleSearch"
            style="width: 14vw;margin-right: 2vw"
            v-model="search"
            size="medium"
            @change="handleSearch"
            placeholder="输入列值即可搜索">
        </el-input>
      </div>
      <div style="display: inline-block">
        <el-button type="success" size="medium" @click="printGrade">打印成绩名单</el-button>
      </div>
    </div>
    <div  id="printme" ref="print">
    <el-table
        style="padding-left: 2vw;width: 100%;"
        ref="parentTable"
        :row-key='handleReserve'
        @sort-change="sort_change"
        :data="showData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        @filter-change="handleFilterChange">

      <el-table-column
          prop="stu_group"
          label="组长学号"
          min-width="20%"
          align="center"
          column-key="stu_group"
          :filters="filter_group"
          filter-placement="bottom-end">
      </el-table-column>

      <el-table-column
          prop="stu_id"
          label="学号"
          min-width="20%"
          align="center">
      </el-table-column>

      <el-table-column
          prop="stu_name"
          label="姓名"
          min-width="20%"
          align="center">
      </el-table-column>

      <el-table-column
          prop="stu_profession"
          label="专业"
          min-width="20%"
          align="center">
      </el-table-column>

      <el-table-column
          prop="stu_grade1"
          label="小组成绩"
          min-width="20%"
          sortable="custom"
          align="center">
      </el-table-column>

      <el-table-column
          prop=""
          label="个人成绩"
          min-width="20%"
          align="center">
      </el-table-column>
    </el-table>
    </div>
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

  </div>
</template>

<script>
  import {unique} from "@/utils";
  export default {
    data() {
      return {
        search:'',
        selectValue:'',

        currentPage: 1,
        total: 100,
        pageSize: 10,

        tableData: [{
          stu_id: '41811001',
          stu_name: '张三',
          stu_profession: '信管' ,
          stu_grade1: '80',
          stu_grade2: '',
        }],

        filter_group: [],

        showData: [],
        tempList: [],
        propType: '',

      }
    },

    //页面创建时，用map方法取出所有的课题放到筛选器中
    created() {
      this.checkAdmin()
      this.getDataFromBack()
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

      handleSearch() {
        let search = this.search

        this.showData = [...this.tableData.filter(data => !search
            || data.stu_id.includes(search)
            || data.stu_name.includes(search)
            || data.stu_profession.includes(search)
        )]

        if(search==='') {
          this.currentPage = 1
          this.showData=[...this.tableData]
        }
      },

      handleFilterChange(filters) {
        let Group = filters.stu_group
        if (Group.length === 0) {
          this.showData = [...this.tableData]
        } else {
          this.showData = [...'']
          for (let i = 0; i < Group.length; i++) {
            //filter()查找数组返回满足条件的数组
            //push(...)合并数组
            if (i === 0) {
              this.showData = [...this.tableData.filter(item => item.stu_group === Group[i])]
              continue
            }
            this.tempList = [...this.tableData.filter(item => item.stu_group === Group[i])]
            this.showData.push(...this.tempList)
            this.tempList = [...'']
          }
          //设置当前页面为1，如果现在不是在第一页开启筛选功能可能会导致显示不出数据
          //因为显示的是showData.slice((currentPage-1)*pageSize,currentPage*pageSize)
          this.currentPage = 1
        }
      },

      //设置表格数据的唯一值,防止翻页过后覆盖
      handleReserve(row) {
        return row.stu_id
      },

      handleSizeChange(val) {
        this.currentPage = 1;
        this.pageSize = val;
      },

      handleCurrentChange(val) {
        this.currentPage = val;
      },

      printGrade() {
        this.$print(this.$refs.print);
      },
      async getDataFromBack(){
        const res = await this.$http.get("http://127.0.0.1:9090/Test1/admstudent"
        //const res = await this.$http.get("http://121.4.96.102:8080/Test1/admstudent"
        )
        if (res.status==200){
          this.tableData =res.data
          this.showData = [...this.tableData]
          console.log(this.tableData)
          let stu_group = []
          for (let i=0;i<this.tableData.length;i++) {
            stu_group[i] = this.tableData[i].stu_group
          }
          let unique_group = unique(stu_group)
          for (let i=0;i<unique_group.length;i++) {
            let object ={}
            object.text =unique_group[i]
            object.value =unique_group[i]
            this.filter_group.push(object)
          }
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

  @media print {
    #printme{
      transform:scale(0.80);
      width: 1500px;
      margin-left: -100px;
    }
  }

  .el-table-filter {
    max-height: 300px;
    overflow: auto;
  }

</style>