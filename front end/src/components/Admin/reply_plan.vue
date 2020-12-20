<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>答辩安排</el-breadcrumb-item>
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
        <el-button type="success" size="medium" style="margin-right: 2vw" @click="newReply">新增答辩</el-button>
      </div>
      <div style="display: inline-block">
        <el-upload
            action
            accept=".xlsx, .xls"
            :auto-upload="false"
            :show-file-list="false"
            :on-change="import_rep">
          <el-button slot="trigger" type="success" size="medium">导入答辩名单</el-button>
        </el-upload>
      </div>
    </div>

    <el-table
        style="padding-left: 2vw;width: 100%;"
        ref="parentTable"
        :row-key='handleReserve'
        @filter-change="handleFilterChange"
        :data="showData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        @sort-change="sort_change">

      <el-table-column
          prop="rep_group"
          label="组长学号"
          min-width="15%">
      </el-table-column>

      <el-table-column
          prop="rep_time"
          label="答辩时间"
          min-width="25%"
          sortable="custom">
      </el-table-column>

      <el-table-column
          prop="rep_address"
          label="答辩地点"
          min-width="17%"
          column-key="rep_address"
          :filters="filter_address"
          filter-placement="bottom-end">
      </el-table-column>

      <el-table-column
          prop="rep_teacher"
          label="答辩老师"
          min-width="23%">
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
               :visible.sync="dialogFormVisible_1"
               @close="closeDialog_1"
               center
               width="35vw">

      <el-form label-width="100px"
               :model="rep_info"
               :rules="reply_rules"
               ref="infoRef">

        <el-form-item :style="form_item" label="组长学号：" prop="rep_group">
          <el-input v-model="rep_info.rep_group" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩时间：" prop="rep_time">
          <el-date-picker
              v-model="rep_info.rep_time"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              size="small"
              style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩地点：" prop="rep_address">
          <el-input v-model="rep_info.rep_address" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩老师：" prop="rep_teacher">
          <el-checkbox-group v-model="rep_info.rep_teacher">
            <el-checkbox v-for="teacher in teachers" :label="teacher" :key="teacher"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible_1=false">取 消</el-button>
        <el-button type="primary" @click="confirm_modify">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新增答辩"
               top="10vh"
               @close="closeDialog_2"
               :visible.sync="dialogFormVisible_2"
               center
               width="35vw">

      <el-form label-width="100px"
               :model="newReplyData"
               :rules="reply_rules"
               ref="newReplyRef">

        <el-form-item :style="form_item" label="组长学号：" prop="rep_group">
          <el-input v-model="newReplyData.rep_group" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩时间：" prop="rep_time">
          <el-date-picker
              v-model="newReplyData.rep_time"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              size="small"
              style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩地点：" prop="rep_address">
          <el-input v-model="newReplyData.rep_address" size="small" clearable></el-input>
        </el-form-item>
        <el-form-item :style="form_item" label="答辩老师：" prop="rep_teacher">
          <el-checkbox-group v-model="newReplyData.rep_teacher">
            <el-checkbox v-for="teacher in teachers" :label="teacher" :key="teacher"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible_2 = false">取 消</el-button>
        <el-button type="primary" @click="confirm_newReply">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {readFile, transformData_rep} from "@/utils";
import {unique} from "@/utils";

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
        marginBottom: "3vh",
      },

      rep_info: {
        rep_group: '',
        rep_time: '',
        rep_address: '',
        rep_teacher: []
      },

      reply_rules: {
        rep_group: [
          { required: true, message: '请输入组长学号', trigger: 'blur' },
        ],
        rep_time: [
          { required: true, message: '请输入答辩时间', trigger: 'blur' },
        ],
        rep_address: [
          { required: true, message: '请输入答辩地点', trigger: 'blur' },
        ],
        rep_teacher: [
          { required: true, message: '请输入答辩老师', trigger: 'blur' },
        ],
      },

      tableData: [],

      filter_address: [],

      newReplyData: {
        rep_group: '',
        rep_time: '',
        rep_address: '',
        rep_teacher: []
      },

      showData: [],
      expands: [],
      tempList: [],
      tempInfo: [],
      propType: '',
      tempData:[],
      errorArr:[],
      teachers:[],

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
      let a2 = a[this.propType]
      let b2 = b[this.propType]
      let final_a = a2.substring(8,10) + a2.substring(11,13) + a2.substring(14,16)
      let final_b = b2.substring(8,10) + b2.substring(11,13) + b2.substring(14,16)
      return final_b - final_a;
    },

    my_asc_sort(a, b) {
      let a2 = a[this.propType]
      let b2 = b[this.propType]
      let final_a = a2.substring(8,10) + a2.substring(11,13) + a2.substring(14,16)
      let final_b = b2.substring(8,10) + b2.substring(11,13) + b2.substring(14,16)
      return final_a - final_b;
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

    findReplyIndex(rep_id) {
      let index = this.tableData.findIndex(function(item) {
        if (item.rep_id === rep_id)
          return true
      })
      return index
    },

    closeDialog_1() {
      this.$refs.infoRef.resetFields();
    },

    closeDialog_2() {
      this.$refs.newReplyRef.resetFields();
    },

    handleSearch() {
      let search = this.search

      if(search.length!==0) {
        this.$refs.parentTable.clearFilter()
      }

      this.showData = [...this.tableData.filter(data => !search
          || data.rep_group.includes(search)
          || data.rep_address.includes(search)
      )]

      if(search==='') {
        this.currentPage = 1
        this.showData=[...this.tableData]
      }
    },

    async modify_info(info) {
      this.tempInfo = [...Object.values(info)]
      this.rep_info.rep_group = this.tempInfo[1]
      this.rep_info.rep_time = this.tempInfo[4]
      this.rep_info.rep_address = this.tempInfo[0]
      this.rep_info.rep_teacher = this.tempInfo[3].split(' ')
      await this.getTeachers()
      this.dialogFormVisible_1 = true
    },

    //通过column-key传过来Project实时监听筛选器筛选条件的变化
    handleFilterChange(filters) {
      let Group = filters.rep_address
      if (Group.length === 0) {
        this.showData = [...this.tableData]
      } else {
        this.showData = [...'']
        for (let i = 0; i < Group.length; i++) {
          //filter()查找数组返回满足条件的数组
          //push(...)合并数组
          if (i === 0) {
            this.showData = [...this.tableData.filter(item => item.rep_address === Group[i])]
            continue
          }
          this.tempList = [...this.tableData.filter(item => item.rep_address === Group[i])]
          this.showData.push(...this.tempList)
          this.tempList = [...'']
        }
        //设置当前页面为1，如果现在不是在第一页开启筛选功能可能会导致显示不出数据
        //因为显示的是showData.slice((currentPage-1)*pageSize,currentPage*pageSize)
        this.currentPage = 1
      }
      this.$refs.parentTable.clearFilter()
    },

    //设置表格数据的唯一值,防止翻页过后覆盖
    handleReserve(row) {
      return row.rep_id
    },

    handleSizeChange(val) {
      this.currentPage = 1;
      this.pageSize = val;
    },

    handleCurrentChange(val) {
      this.currentPage = val;
    },
    //读取excel文件数据
    async import_rep(ev) {
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
        console.log("data= ")
        console.log(data)
        //把读取出来的数据替换成可以发给服务器的数据（姓名 : stu_name）
        this.tempData = transformData_rep(data)
      }
      await this.submit()
    },

    //提交数据到服务器
    async submit(){
      if (!Object.prototype.hasOwnProperty.call(this.tempData[0],"rep_time")||
          !Object.prototype.hasOwnProperty.call(this.tempData[0],"rep_address")||
          !Object.prototype.hasOwnProperty.call(this.tempData[0],"rep_teacher")||
          !Object.prototype.hasOwnProperty.call(this.tempData[0],"rep_group")){
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
            this.errorArr.clear()
            return
          }
          this.$message.success("上传成功")
          return
        }
        let reply = this.tempData[n]
        //const res = await this.$http.post("http://127.0.0.1:9090/Test1/import",{reply})
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/import",{reply})
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

    async newReply() {
      this.dialogFormVisible_2 = true
      await this.getTeachers()
    },

    confirm_newReply() {
      let valid_out = false;

      this.$refs.newReplyRef.validate(valid =>{
        valid_out = valid
      });
      if (!valid_out) {
        return
      }
      this.$confirm('确定新增？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {

        let object ={}
        let command ="2"
        object.rep_group = this.newReplyData.rep_group
        object.rep_time = this.newReplyData.rep_time
        object.rep_address = this.newReplyData.rep_address
        object.rep_teacher = this.newReplyData.rep_teacher.join(" ")
        console.log(object.rep_time)
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/admreply",{object,command})
        //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admreply",{object,command})
        if (res.data==="新建成功"){
          this.$message.success("新建成功")
          this.dialogFormVisible_2 = false
          clearTimeout(this.timer);  //清除延迟执行
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);
        }else {
          this.$message.error("新建失败")
        }

      }).catch(() => {
      });
    },

    confirm_modify() {
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
      }).then(async () => {
        for (let i=0;i<this.rep_info.rep_teacher.length;i++){
          if (this.rep_info.rep_teacher[i].length===0||this.rep_info.rep_teacher[i]==null){
            this.rep_info.rep_teacher.splice(i,1)
          }
        }
        let teachers = this.rep_info.rep_teacher.join(' ')
        let info = {}
        info.rep_address = this.rep_info.rep_address
        info.rep_time = this.rep_info.rep_time
        info.rep_group = this.rep_info.rep_group
        info.rep_teacher = teachers
        let command = '1'
        console.log(info.rep_teacher)
        console.log(teachers.length)
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/admreply",{info,command}
        // const res = await this.$http.post("http://127.0.0.1:9090/Test1/admreply",{info,command}
         )
        if (res.data==="修改成功"){
          this.dialogFormVisible_1 = false
          this.$message.success("修改成功")
          clearTimeout(this.timer);  //清除延迟执行
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);
        }else {
          this.$message.error("修改失败")
        }

      }).catch(() => {
      });
    },
    async getDataFromBack(){
      const res = await this.$http.get("http://121.4.96.102:8080/Test1/admreply"
      //const res = await this.$http.get("http://127.0.0.1:9090/Test1/admreply"
    )
      if (res.status==200){
        this.tableData =res.data
        this.showData = [...this.tableData]
        console.log(this.tableData)
        let address = []
        for (let i=0;i<this.tableData.length;i++) {
          address[i] = this.tableData[i].rep_address
        }
        let unique_address = unique(address)
        for (let i=0;i<unique_address.length;i++) {
          let object ={}
          object.text =unique_address[i]
          object.value =unique_address[i]
          this.filter_address.push(object)
        }
      }
    },
    async getTeachers(){
      if (this.teachers.length!=0){

        return
      }
      const res = await this.$http.get("http://121.4.96.102:8080/Test1/admteacher"
      //const res = await this.$http.get("http://127.0.0.1:9090/Test1/admteacher"
      )
      if (res.status==200){
        for (let i =0;i<res.data.length;i++){
          this.teachers.push(res.data[i].tea_name)
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

</style>