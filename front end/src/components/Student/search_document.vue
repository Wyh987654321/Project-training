<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>课程文档</el-breadcrumb-item>
    </el-breadcrumb>

    <h2 style="margin-top: 5vh;margin-left: 3vw">课程文档</h2>
    <div class="div_header"></div>
    <el-table
        style="padding-left: 15vw;width: 70%;"
        :data="file">

      <el-table-column
          label="文件名称"
          width="300px">
        <template slot-scope="scope">
          <img :src="require('@/assets/icon/'+icon[scope.$index]+'.png')" class="icon"/>
          {{scope.row.filename}}
        </template>
      </el-table-column>

      <el-table-column label="下载地址" >
        <template slot-scope="scope">
          <el-button size="small" type="text">
            <a :href="scope.row.url" download target="_blank" style="font-size: 16px;">下载</a>
          </el-button>
        </template>
      </el-table-column>
    </el-table>


  </div>

</template>
<script>
import {matchType} from '@/utils';
  export default {
    data() {
      return {
        // href:'http://127.0.0.1:9090/Test1/download?file=',
        href:'http://121.4.96.102:8080/Test1/download/',
        // href:'http://127.0.0.1:9090/Test1/download/',
        file:[],
        files:[],
        urls:[],
        icon: [],
      }
    },
    created() {
      this.checkStudent()
      this.getDataFromBack()
    },
    methods:{
      async getDataFromBack(){
        // const user = this.toStr(localStorage.getItem("user"))
        let user ="student"
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/download2",{user})
        //const res = await this.$http.post("http://127.0.0.1:9090/Test1/download2",{user})
        if (res.status==200){
          console.log(res)
          this.files = res.data
          this.urls = JSON.parse(JSON.stringify(res.data))
          for (let i = 0; i < this.urls.length; i++) {
            this.urls[i] = this.href + this.urls[i]
            let object = {}
            object.url = this.urls[i]
            console.log(object.url)
            object.filename = this.files[i]
            this.icon[i] = matchType(object.filename)
            this.file.push(object)
          }
        }
      },

      checkStudent() {
        let user = this.toStr(localStorage.getItem('user'))
        if (!user || user.substring(0,1)!=='4') {
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
  }

  .icon {
    display: inline-block;
    width: 20px;
    height: 20px;
    vertical-align: middle;
  }

</style>