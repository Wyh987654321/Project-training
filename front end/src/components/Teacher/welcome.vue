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
          1.如果发布的课题信息有误，请及时联系管理员删除错误课题，并重新发布正确的课题。
        </div>
        <div class="text item">
          2.如果提交的成绩有误，重新提交正确成绩即可，新成绩将会覆盖旧成绩。
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
      this.checkTeacher()
    },

    methods: {
      checkTeacher() {
        let user = this.toStr(localStorage.getItem('user'))
        if (!user || user.substring(0,1)==='4' || user.substring(0,1)==='a') {
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