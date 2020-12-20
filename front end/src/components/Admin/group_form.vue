<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>名单列表</el-breadcrumb-item>
      <el-breadcrumb-item>小组名单</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_header">
      <el-input
          @input="handleSearch"
          style="width: 14vw;"
          v-model="search"
          size="medium"
          placeholder="输入组长学号即可搜索">
      </el-input>
      <el-button
          size="medium"
          type="success"
          style="margin-left: 2vw;"
          @click="open">新建小组
<!--          @click="newGroup">新建小组-->

      </el-button>
    </div>

    <el-table
        style="padding-left: 2vw;width: 100%;"
        ref="parentTable"
        @filter-change="handleFilterChange"
        :row-key="handleReserve"
        :data="showData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        @expand-change="expandChange"
        :expand-row-keys="expands">

      <el-table-column type="expand" >
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="小组成员：" v-for="item in props.row.members" :key="item">
              <span>{{item}}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
          prop="gro_id"
          label="组长学号"
          min-width="10%">
      </el-table-column>

      <el-table-column
          prop="gro_name"
          label="组长姓名"
          min-width="10%">
      </el-table-column>

      <el-table-column
          prop="gro_pro"
          label="组长专业"
          min-width="10%"
          column-key="gro_pro"
          :filters="filter_profession"
          filter-placement="bottom-end">
      </el-table-column>

      <el-table-column
          prop="gro_num"
          label="小组人数"
          min-width="10%"
          column-key="gro_num"
          :filters="filter_num"
          filter-placement="bottom-end">
      </el-table-column>

      <el-table-column
          prop="gro_topic"
          label="选题情况"
          min-width="20%"
          column-key="gro_topic"
          :filters="filter_select"
          filter-placement="bottom-end">
        <template scope="scope">
          <span v-if="scope.row.gro_topic==='未选题'" style="color:red">{{ scope.row.gro_topic }}</span>
          <span v-else>{{ scope.row.gro_topic }}</span>
        </template>
      </el-table-column>

      <el-table-column
          prop="gro_sub"
          label="项目提交情况"
          min-width="14%"
          column-key="gro_sub"
          :filters="filter_submit"
          filter-placement="bottom-end">
        <template scope="scope">
          <span v-if="scope.row.gro_sub==='未提交'" style="color:red">{{ scope.row.gro_sub }}</span>
          <span v-else style="color: #37B328">{{ scope.row.gro_sub }}</span>
        </template>
      </el-table-column>

      <el-table-column
          prop="gro_teacher"
          label="指导教师"
          min-width="10%"
          column-key="gro_teacher"
          :filters="filter_teacher"
          filter-placement="bottom-end">
      </el-table-column>

      <el-table-column label="操作" min-width="16%">
        <template scope="scope">
          <el-button
              size="medium"
              type="primary"
              @click="modify_group(scope.row)">编辑成员
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

    <el-dialog title="编辑成员"
               :visible.sync="dialogFormVisible_1"
               top="10vh"
               @close="closeDialog_1"
               center
               width="30vw">

      <el-form
          label-width="90px"
          ref="modifyRef">
        <el-form-item :style="form_item" label="组长学号：">
          <div style="display: inline-block;margin-right: 1vw;">
            {{tempInfo[0]}}
          </div>
        </el-form-item>
        <el-form-item :style="form_item2" label="小组成员：" v-for="(item,index) in group_info.members" :key="index">
          <div style="display: inline-block;width: 70%">
            {{group_info.members[index]}}
          </div>
          <div style="display: inline-block;width: 30%">
            <el-button type="text" size="small" @click="removeMember(index)">移除</el-button>
          </div>
        </el-form-item>
      </el-form>

      <el-dialog
          width="30%"
          center
          title="新增成员"
          @close="closeDialog_3"
          :visible.sync="innerVisible"
          append-to-body>

        <el-form
            label-width="100px"
            :model="newMemberData"
            :rules="newMember_rules"
            ref="newMemberRef">
          <el-form-item label="小组成员：" prop="newMember">
            <el-select v-model="newMemberData.newMember" placeholder="请输入组员信息">
              <el-option v-for="item in noGroupStu" :key="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取消</el-button>
          <el-button type="primary" @click="confirm_newMember">确定</el-button>
        </div>
      </el-dialog>

      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="removeGroup" style="margin-right: 2vw">删除小组</el-button>
        <el-button type="success" @click="innerVisible = true">新增成员</el-button>
      </div>
    </el-dialog>

    <el-dialog title="新建小组"
               top="10vh"
               @close="closeDialog_2"
               :visible.sync="dialogFormVisible_2"
               center
               width="35vw">

      <el-form label-width="100px"
               :model="newGroupData"
               :rules="newGroup_rules"
               ref="newGroupRef">
        <el-form-item :style="form_item" label="组长信息：" prop="leader_id">
          <el-select v-model="newGroupData.leader_id" placeholder="请输入组长信息" size="small" style="width: 20vw">
            <el-option v-for="item in noGroupStu" :key="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            :style="form_item"
            label="组员信息："
            v-for="(item,index) in newGroupData.members"
            :key="index">
          <el-select v-model="newGroupData.members[index]" placeholder="请输入组员信息" size="small" style="width: 20vw">
            <el-option v-for="item in noGroupStu" :key="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible_2 = false">取 消 </el-button>
        <el-button type="primary" @click="confirm_newGroup">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {unique} from "@/utils";

  export default {
    data() {
      return {
        search: '',
        selectValue: '',

        dialogFormVisible_1: false,
        dialogFormVisible_2: false,
        innerVisible: false,

        currentPage: 1,
        total: 100,
        pageSize: 10,

        form_item: {
          marginBottom: "3vh",
        },

        form_item2: {
          marginBottom: "3vh",
        },

        group_info: {
          members: [],
        },

        newGroup_rules: {
          leader_id: [
            {required: true, message: '请输入组长信息', trigger: 'blur'},
          ],
        },

        newMember_rules: {
          newMember: [
            {required: true, message: '请输入组员信息', trigger: 'blur'},
          ],
        },
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
        filter_num: [],
        filter_select: [
          {
            text: '暂无',
            value: '暂无'
          }],
        filter_submit: [
          {
            text: '已提交',
            value: '已提交'
          },
          {
            text: '未提交',
            value: '未提交'
          }],
        filter_teacher: [],

        noGroupStu: [],

        newMemberData: {
          newMember: '',
        },

        newGroupData: {
          leader_id: '',
          leader_name: '',
          leader_profession: '',
          num: '',
          isSelect: '未选题',
          isSubmit: '未提交',
          teacher: '',
          members: ['','','','',''],
        },

        showData: [],
        tempList: [],
        tempInfo: [],
        expands:[],
        row_index: 0,

      }
    },

    //页面创建时，用map方法取出所有的课题放到筛选器中
    created() {
      this.checkAdmin()
      this.getDataFromBack()
      this.newGroup()

    },

    methods: {
      expandChange(row, expandedRows) {
        let that = this
        if (expandedRows.length) {
          that.expands = []
          if (row) {
            that.expands.push(row.gro_id)
          }
        } else {
          that.expands = []
        }
      },

      closeDialog_1() {
        this.$refs.modifyRef.resetFields();
        this.group_info.members = []
      },

      closeDialog_2() {
        this.$refs.newGroupRef.resetFields();
        this.newGroupData.leader_id = ''
        this.newGroupData.members = ['','','','','']
      },

      closeDialog_3() {
        this.$refs.newMemberRef.resetFields();
      },

      findGroupIndex(gro_id) {
        let index = this.tableData.findIndex(function(item) {
          if (item.gro_id === gro_id)
            return true
        })
        return index
      },

      handleSearch() {
        let search = this.search

        if (search.length !== 0) {
          this.$refs.parentTable.clearFilter()
        }

        this.showData = [...this.tableData.filter(data => !search
            || data.gro_id.includes(search)

        )]


        if (search === '') {
          this.currentPage = 1
          this.showData = [...this.tableData]
        }
      },

      modify_group(info) {
        this.tempInfo = [...Object.values(info)]
        for (let i=0; i<this.tempInfo[7].length; i++) {
          this.group_info.members[i] = this.tempInfo[7][i]
        }
        this.dialogFormVisible_1 = true
      },

      //通过column-key传过来Project实时监听筛选器筛选条件的变化
      handleFilterChange(filters) {
        if (!(typeof (filters.gro_pro) == "undefined")) {
          let Profession = filters.gro_pro
          if (Profession.length === 0) {
            this.showData = [...this.tableData]
          } else {
            this.showData = [...'']
            for (let i = 0; i < Profession.length; i++) {
              //filter()查找数组返回满足条件的数组
              //push(...)合并数组
              if (i === 0) {
                this.showData = [...this.tableData.filter(item => item.gro_pro === Profession[i])]
                continue
              }
              this.tempList = [...this.tableData.filter(item => item.gro_pro === Profession[i])]
              this.showData.push(...this.tempList)
              this.tempList = [...'']
            }
            //设置当前页面为1，如果现在不是在第一页开启筛选功能可能会导致显示不出数据
            //因为显示的是showData.slice((currentPage-1)*pageSize,currentPage*pageSize)
            this.currentPage = 1
          }
          this.$refs.parentTable.clearFilter()
        }

        if (!(typeof (filters.gro_num) == "undefined")) {
          let Num = filters.gro_num
          if (Num.length === 0) {
            this.showData = [...this.tableData]
          } else {
            this.showData = [...'']
            for (let i = 0; i < Num.length; i++) {
              if (i === 0) {
                this.showData = [...this.tableData.filter(item => item.gro_num === Num[i])]
                continue
              }
              this.tempList = [...this.tableData.filter(item => item.gro_num === Num[i])]
              this.showData.push(...this.tempList)
              this.tempList = [...'']
            }
            this.currentPage = 1
          }
          this.$refs.parentTable.clearFilter()
        }

        if (!(typeof (filters.gro_topic) == "undefined")) {
          let Select = filters.gro_topic
          if (Select.length === 0) {
            this.showData = [...this.tableData]
          } else {
            this.showData = [...'']
            for (let i = 0; i < Select.length; i++) {
              if (i === 0) {
                this.showData = [...this.tableData.filter(item => item.gro_topic === Select[i])]
                continue
              }
              this.tempList = [...this.tableData.filter(item => item.gro_topic === Select[i])]
              this.showData.push(...this.tempList)
              this.tempList = [...'']
            }
            this.currentPage = 1
          }
          this.$refs.parentTable.clearFilter()
        }

        if (!(typeof (filters.gro_sub) == "undefined")) {
          let Submit = filters.gro_sub
          if (Submit.length === 0) {
            this.showData = [...this.tableData]
          } else {
            this.showData = [...'']
            for (let i = 0; i < Submit.length; i++) {
              if (i === 0) {
                this.showData = [...this.tableData.filter(item => item.gro_sub === Submit[i])]
                continue
              }
              this.tempList = [...this.tableData.filter(item => item.gro_sub === Submit[i])]
              this.showData.push(...this.tempList)
              this.tempList = [...'']
            }
            this.currentPage = 1
          }
          this.$refs.parentTable.clearFilter()
        }

        if (!(typeof (filters.gro_teacher) == "undefined")) {
          let Teacher = filters.gro_teacher
          if (Teacher.length === 0) {
            this.showData = [...this.tableData]
          } else {
            this.showData = [...'']
            for (let i = 0; i < Teacher.length; i++) {
              if (i === 0) {
                this.showData = [...this.tableData.filter(item => item.gro_teacher === Teacher[i])]
                continue
              }
              this.tempList = [...this.tableData.filter(item => item.gro_teacher === Teacher[i])]
              this.showData.push(...this.tempList)
              this.tempList = [...'']
            }
            this.currentPage = 1
          }
          this.$refs.parentTable.clearFilter()
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

      //从服务器获取未分组的同学名单
      async newGroup() {

        let command ='1'
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/admgroup",{command}
        //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admgroup",{command}
        )
        if (res.status==200){
          this.noGroupStu = res.data
        }
      },
      open(){
        this.dialogFormVisible_2 = true
      },

      removeMember(index) {
        this.$confirm('确定删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          // this.$message({
          //   type: 'success',
          //   message: '删除成功!'
          // });

          let index_2 = this.findGroupIndex(this.tempInfo[0])
          let command ="3"
          let stu_info = this.tableData[index_2].members[index].split(" ")[0]

          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admgroup",{command,stu_info}
          //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admgroup",{command,stu_info}
          )
          if (res.data==="移除成功"){
            this.$message.success("移除成功")
            this.tableData[index_2].members.splice(index, 1)
            this.tableData[index_2].gro_num--
            this.showData = [...this.tableData]
          }else{
            this.$message.error("移除失败")
          }
          this.dialogFormVisible_1 = false

        }).catch(() => {
        });
      },

      removeGroup() {
        this.$confirm('确定删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {

          this.dialogFormVisible_1 = false
          let index = this.findGroupIndex(this.tempInfo[0])
          let leader =this.tableData[index].gro_id
          let members =[]
          for (let i=0;i<this.tableData[index].members.length;i++){
            members.push(this.tableData[index].members[i].split(" ")[0])
          }
          let command ="5"
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admgroup",{leader,members,command}
          //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admgroup",{leader,members,command}
          )
          if (res.data==="删除成功"){
            this.$message.success("删除成功")
            this.tableData.splice(index, 1)
            this.showData = [...this.tableData]
          }else if(res.data==="0"){
            this.$message.error("小组删除失败，整个小组取消删除")
          }else {
            this.$message.error("删除部分成员失败"+res.data)
          }

        }).catch(() => {
        });
      },

      confirm_newGroup() {
        let valid_out = false;
        this.$refs.newGroupRef.validate(valid => {
          valid_out = valid
        });
        if (!valid_out) {
          return
        }
        this.$confirm('确定新建？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async() => {

          this.dialogFormVisible_2 = false

          //组长学号
          let leader = this.newGroupData.leader_id.split(' ')[0]
          let members = []
          for (let i=0;i<this.newGroupData.members.length;i++){
            if (this.newGroupData.members[i].length>0){
              let members_id =this.newGroupData.members[i].split(' ')[0]
              members.push(members_id)
            }
          }
          let command ="2"
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admgroup",{leader,members,command}
          //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admgroup",{leader,members,command}
          )
          console.log(res);
          if (res.data==="0"){
            this.$message.error("新建组长失败，取消小组新建")
          }else if(res.data==="新建成功"){
            this.$message.success("小组新建成功")
          }else{
            this.$message.error("部分成员插入失败，学号为"+res.data)
          }
          clearTimeout(this.timer);  //清除延迟执行
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);
        }).catch(() => {
        });
      },

      confirm_newMember() {
        let valid_out = false;
        this.$refs.newMemberRef.validate(valid => {
          valid_out = valid
        });
        if (!valid_out) {
          return
        }
        this.$confirm('确定新增？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          this.innerVisible = false
          this.dialogFormVisible_1 = false
          let index = this.findGroupIndex(this.tempInfo[0])
          // console.log(this.tableData[index].gro_id)  //组长学号
          // console.log(this.newMemberData.newMember)   //学生学号
          let command ="4"
          let leader =this.tableData[index].gro_id
          let member = this.newMemberData.newMember
          const res = await this.$http.post("http://121.4.96.102:8080/Test1/admgroup",{command,leader,member}
          //const res = await this.$http.post("http://127.0.0.1:9090/Test1/admgroup",{command,leader,member}
          )
          if (res.data==="添加成功"){
           this.$message.success("添加成功")
            this.tableData[index].members.push(member)
            console.log(this.tableData[index].members)
            this.showData = [...this.tableData]
          }else {
            this.$message.error("添加失败")
          }

        }).catch(() => {
        });
      },
      //从服务器获取数据
      async getDataFromBack(){
        const res = await this.$http.get("http://121.4.96.102:8080/Test1/admgroup"
        //const res = await this.$http.get("http://127.0.0.1:9090/Test1/admgroup"
        )
        if (res.status==200){
          this.tableData =res.data
          this.showData = [...this.tableData]
          let num = []
          let teacher =[]
          for (let i=0;i<this.tableData.length;i++) {
            num[i] = this.tableData[i].gro_num
            teacher[i] = this.tableData[i].gro_teacher
          }
          let unique_num = unique(num)
          let unique_teacher = unique(teacher)
          for (let i=0;i<unique_num.length;i++) {
            let object ={}
            object.text =unique_num[i]
            object.value =unique_num[i]
            this.filter_num.push(object)
          }

          for (let i=0;i<unique_teacher.length;i++) {
            let object ={}
            object.text =unique_teacher[i]
            object.value =unique_teacher[i]
            this.filter_teacher.push(object)
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