<template>
  <div style="width:100%;text-align:start">

    <h2 style="margin-top: 5vh;text-align: center">Welcome!</h2>
    <div style="margin-top: 3vh;text-align: center">欢迎使用项目实训管理系统，本系统还存在许多不完善之处，如在使用过程中，给您带来不好的体验，请多多包涵。</div>

    <div style="margin-left: 25vw;margin-top: 6vh">
      <el-card class="box-card2">
        <div slot="header" class="clearfix">
          <span>注意事项</span>
        </div>
        <div class="text item">
          1.每位同学只能填写一次分组，组长可以移除小组成员，被移除的同学可以重新填写分组。
        </div>
        <div class="text item">
          2.每位组长只能提交一次选题，如果提交的选题有误，请及时联系管理员进行修改。
        </div>
        <div class="text item">
          3.每位组长只能提交一次项目文件，请确保文件内容齐全，如果内容有误，请及时联系指导老师并补交。
        </div>
        <div class="text item">
          4.txt、pdf和png等图片格式的文件直接点击下载会在网页中打开，若要下载到本地，需要右键下载，选择将链接另存为即可。
        </div>
      </el-card>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
      }
    },

    created() {
      this.checkStudent()
    },

    methods: {
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
    clear: both
  }

  .box-card2 {
    width: 480px;
  }

</style>