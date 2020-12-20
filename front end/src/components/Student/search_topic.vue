<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/stu_welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息查询</el-breadcrumb-item>
      <el-breadcrumb-item>课题介绍</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="div_search_topic">
      <el-card v-for="topic in topics" :key="topic.top_id" class="box-card">
        <div slot="header" class="clearfix">
          <div style="display:inline-block;width: 85%">
            <span>{{topic.top_name}}</span>
          </div>
          <div style="display:inline-block;width: 15%;">
            <el-button type="primary" size="small" v-on:click="submit(topic.top_name)" v-show="isLeader==='true'" :style="background" :disabled="isDisabled">选此题目</el-button>
          </div>
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
    data() {
      return {
        topics:[],
        form: {
          leader_id: this.toStr(localStorage.getItem('user')),
          topics: ''
        },
        isLeader: localStorage.getItem('isLeader'),
        background: {
          backgroundColor: '#409EFF',
          borderColor: '#409EFF'
        },
        isDisabled: false
      };
    },
    created() {
      this.checkStudent()
      this.getTopicFromBack()
      this.isSelect()
    },
    beforeDestroy() {
      clearTimeout(this.timer);
    },
    methods:{
      async getTopicFromBack(){
        const res = await this.$http.get("http://121.4.96.102:8080/Test1/searchtopic"
        )
        if (res.status==200){
          console.log(res.data)
          this.topics =res.data
        }
      },
      submit(name) {
        if (this.isDisabled) {
          return
        } else {
          this.form.topics = name
          let form = this.form
          let axios = this.$http
          let message = this.$message
          // eslint-disable-next-line no-unused-vars
          let background = this.background

          this.$confirm('确定提交？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async function () {

            const res = await axios.post("http://121.4.96.102:8080/Test1/selecttopic", form)
            if (res.data === "选题成功") {
              message.success("选题成功")

              this.timer = setTimeout(()=>{   //设置延迟执行
                location.reload()
              },1000);
            } else {
              message.error("选题失败,请检查输入信息是否正确")
            }

          }).catch(e => e)
        }
        },
      async isSelect(){
        let command ="判断是否选过题"
        let user =this.toStr(localStorage.getItem('user'))
        const res = await this.$http.post("http://121.4.96.102:8080/Test1/getStatus",{command,user})
        if (res.data==="选过了"){
          this.background.backgroundColor = '#C0C0C0'
          this.background.borderColor = '#C0C0C0'
          this.isDisabled = true
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

  .div_search_topic {
    margin-top: 5vh;
    padding-left: 10vw;
  }

</style>