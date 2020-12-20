<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>发布文档</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_header">
      <el-button type="success" @click="upload_doc">上传文档</el-button>
    </div>

      <el-dialog title="上传文档"
                 top="10vh"
                 :visible.sync="dialogFormVisible"
                 center
                 width="30vw">
        <el-upload
            class="upload-demo"
            ref="upload"
            action="http://121.4.96.102:8080/Test1/upload2"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
            :auto-upload="false"
            style="padding-left: 10vw;width: 12vw">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button type="success" @click="submitUpload" style="margin-top: 5vh;">上传至服务器</el-button>
        </div>
      </el-dialog>

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
        dialogFormVisible: false,
        href:'http://121.4.96.102:8080/Test1/download/',
        // href:'http://127.0.0.1:9090/Test1/download/',

        file:[],
        files:[],
        urls:[],
        icon: [],
      }
    },
    created() {
      this.checkAdmin()
      this.getDataFromBack()
    },
    methods: {
      upload_doc() {
        this.dialogFormVisible = true
      },

      submitUpload() {
        this.$refs.upload.submit();

      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      async handleSuccess(res){
        if (res==="上传成功"){
          this.$message.success("上传成功")
          this.dialogFormVisible = false
          clearTimeout(this.timer);  //清除延迟执行
          this.timer = setTimeout(()=>{   //设置延迟执行
            location.reload()
          },1000);
        }
        else{
          this.$message.error("上传失败")
        }
      },


      async getDataFromBack(){
        // const user = this.toStr(localStorage.getItem("user"))
        let user ="student"
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/download2",{user})
        if (res.status==200) {
          console.log(res.data)
          this.files = res.data
          this.urls = JSON.parse(JSON.stringify(res.data))
          for (let i = 0; i < this.urls.length; i++) {
            this.urls[i] = this.href + decodeURI(this.urls[i])
            let object = {}
            object.url = this.urls[i]
            object.filename = this.files[i]
            console.log(object.url)
            this.icon[i] = matchType(object.filename)
            this.file.push(object)
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

  .icon {
    display: inline-block;
    width: 20px;
    height: 20px;
    vertical-align: middle;
  }

</style>