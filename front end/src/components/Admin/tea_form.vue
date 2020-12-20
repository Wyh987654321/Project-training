<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>名单列表</el-breadcrumb-item>
      <el-breadcrumb-item>教师名单</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_header">
      <div style="display: inline-block">
        <el-input
            @input="handleSearch"
            style="width: 14vw;margin-right: 2vw"
            v-model="search"
            size="medium"
            placeholder="输入列值即可搜索">
        </el-input>
      </div>
      <div style="display: inline-block">
        <el-upload
            action
            accept=".xlsx, .xls"
            :auto-upload="false"
            :show-file-list="false"
            :on-change="import_tea">
            <el-button slot="trigger" type="success" size="medium">导入老师名单</el-button>
        </el-upload>
      </div>
    </div>

    <el-table
        style="padding-left: 2vw;width: 100%;"
        ref="parentTable"
        :row-key='handleReserve'
        :data="showData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        @expand-change="expandChange"
        :expand-row-keys="expands">

      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="密码">
              <span>{{ props.row.tea_password }}</span>
            </el-form-item>
            <el-form-item label="联系电话">
              <span>{{ props.row.tea_phone }}</span>
            </el-form-item>
            <el-form-item label="电子邮箱">
              <span>{{ props.row.tea_email }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
          prop="tea_id"
          label="教师号"
          min-width="20%"
          align="center">
      </el-table-column>

      <el-table-column
          prop="tea_name"
          label="姓名"
          min-width="15%"
          align="center">
      </el-table-column>

      <el-table-column
          prop="topic_num"
          label="发布题目数量"
          align="center"
          min-width="20%">
        <template scope="scope">
          <span v-if="scope.row.topic_num==='0'" style="color:red">{{ scope.row.topic_num }}</span>
          <span v-else>{{ scope.row.topic_num }}</span>
        </template>
      </el-table-column>

      <el-table-column
          prop="guideNum_need"
          label="指导评分情况"
          align="center"
          min-width="25%">


      </el-table-column>

      <el-table-column label="操作" min-width="20%">
        <template scope="scope">
          <el-button
              size="medium"
              type="primary"
              @click="modify_info(scope.row)">修改信息
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

    <el-dialog title="修改信息"
               top="10vh"
               :visible.sync="dialogFormVisible"
               @close="closeDialog"
               center
               width="35vw">

      <el-form label-width="100px"
               :model="tea_info"
               :rules="info_rules"
               ref="infoRef">
        <el-form-item :style="form_item" label="教师号：">
          <div>
            {{tempInfo[2]}}
          </div>
        </el-form-item>
        <el-form-item :style="form_item" label="姓名：" prop="tea_name">
          <el-input v-model="tea_info.tea_name" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="密码：" prop="tea_password">
          <el-input v-model="tea_info.tea_password" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="电话：" prop="tea_phone">
          <el-input v-model="tea_info.tea_phone" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="邮箱：" prop="tea_email">
          <el-input v-model="tea_info.tea_email" size="small" clearable></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {readFile, transformData_tea} from "@/utils";
  export default {
    data() {
      return {
        search:'',
        selectValue:'',

        dialogFormVisible: false,

        currentPage: 1,
        total: 100,
        pageSize: 10,

        form_item: {
          marginBottom: "3vh",
        },

        tea_info: {
          tea_id: '',
          tea_name: '',
          tea_password: '',
          tea_phone: '',
          tea_email: '',
        },

        info_rules: {
          tea_name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
          ],
          tea_password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ],
          tea_phone: [
            { required: true, message: '请输入电话', trigger: 'blur' },
          ],
          tea_email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
          ],
        },

        tableData: [],
        tempData:[],
        showData: [],
        expands: [],
        tempList: [],
        tempInfo: [],
        errorArr:[],

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
      expandChange(row, expandedRows) {
        let that = this
        if (expandedRows.length) {
          that.expands = []
          if (row) {
            that.expands.push(row.tea_id)
          }
        }
        else {
          that.expands = []
        }
      },

      closeDialog() {
        this.$refs.infoRef.resetFields();
      },

      findStuIndex(tea_id) {
        let index = this.tableData.findIndex(function(item) {
          if (item.tea_id === tea_id)
            return true
        })
        return index
      },

      handleSearch() {
        let search = this.search

        if(search.length!==0) {
          this.$refs.parentTable.clearFilter()
        }

        this.showData = [...this.tableData.filter(data => !search
            || data.tea_id.includes(search)
            || data.tea_name.includes(search)
        )]

        if(search==='') {
          this.currentPage = 1
          this.showData=[...this.tableData]
        }
      },

      modify_info(info) {
        this.tempInfo = [...Object.values(info)]
        this.tea_info.tea_id = this.tempInfo[2]
        this.tea_info.tea_name = this.tempInfo[3]
        this.tea_info.tea_password = this.tempInfo[4]
        this.tea_info.tea_phone = this.tempInfo[5]
        this.tea_info.tea_email = this.tempInfo[1]
        this.dialogFormVisible = true
      },

      //设置表格数据的唯一值,防止翻页过后覆盖
      handleReserve(row) {
        return row.tea_id
      },

      handleSizeChange(val) {
        this.currentPage = 1;
        this.pageSize = val;
      },

      handleCurrentChange(val) {
        this.currentPage = val;
      },

      //读取excel文件数据
      async import_tea(ev) {
        let file =ev.raw
        if (!file){
          return
        }else{
          this.show=false
          //读取file中的数据(变成json格式)
          let data = await readFile(file)
          let workbook = this.$xlsx.read(data,{type:"binary"})
          let worksheet = workbook.Sheets[workbook.SheetNames[0]]
          data = this.$xlsx.utils.sheet_to_json(worksheet)
          //把读取出来的数据替换成可以发给服务器的数据（姓名 : stu_name）
          this.tempData = transformData_tea(data)
          console.log(this.tempData)
        }
        await this.submit()
      },
      //提交数据给后端
      async submit(){
        if (!Object.prototype.hasOwnProperty.call(this.tempData[0],"tea_id")||
            !Object.prototype.hasOwnProperty.call(this.tempData[0],"tea_name")||
            !Object.prototype.hasOwnProperty.call(this.tempData[0],"tea_email")||
            !Object.prototype.hasOwnProperty.call(this.tempData[0],"tea_phone")){
          this.$message({
            message:'Excel文件内容错误，请选择正确的文件导入',
            type: 'warning',
            showClose: true
          })
          return
        }
        let loadingInstance = this.$Loading.service({
          text:"请稍等，系统正在处理中",
          background:"rgba(248,248,255,.5)"
        })
        let n = 0
        let send = async () =>{
          if (n>this.tempData.length-1){
            //数据传输完毕
            if (this.errorArr.length>0){
              this.$message({
                message: "以下信息上传失败，分别是第"+this.errorArr.toString()+"条",
                type:"error",
                showClose: true
              })
              this.errorArr=[]
              return
            }
            this.$message.success("上传成功")
            return
          }
          let teacher = this.tempData[n]
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/import",{teacher})
          if (res.data==="导入成功"){
            n++;
            await send()
          }else {
            //记录上传失败的信息
            this.errorArr.push(n)
            n++
            await send()
          }
        }
        await send()
        loadingInstance.close()
        clearTimeout(this.timer);  //清除延迟执行
        this.timer = setTimeout(()=>{   //设置延迟执行
          location.reload()
        },1000);
      },
      confirm() {
        let valid_out = false;
        this.$refs.infoRef.validate(valid =>{
          valid_out = valid
        });
        if (!valid_out) {
          return
        }
        this.$confirm('确定修改？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async() => {
          // let teacher ={}
          // teacher.tea_id = this.tea_info.tea_id
          // teacher.tea_nema = this.tea_info.tea_name
          // teacher.tea_password = this.tea_info.tea_password
          // teacher.tea_phone = this.tea_info.tea_phone
          // teacher.tea_email = this.tea_info.tea_email
          let teacher = this.tea_info
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admteacher",{teacher}
          )
          if (res.data==="修改成功"){
            this.$message.success("修改成功")
            this.dialogFormVisible = false
          }else {
            this.$message.error("修改失败")
          }
          clearTimeout(this.timer);  //清除延迟执行
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);

        }).catch(() => {
        });
      },
      //从服务器获取数据
      async getDataFromBack(){
        const res = await this.$http.get("http://121.4.96.102:8080/Test1/admteacher"
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