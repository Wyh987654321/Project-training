<template>

  <div class="background" :style="backgroundDiv">

    <div class="login_box">

    </div>
  </div>


</template>


<script>
  export default {
    data() {
      return {
        backgroundDiv: {
          backgroundImage:'url(' + require('@/assets/image/background.png') + ')',
          backgroundRepeat:'no-repeat',
          backgroundSize:'100% 100%'
        }
      }
    }
  }
</script>

<style>
  .background {
    width: 100%;
    height: 100%;
    position: fixed;
  }
  .login_box {
    width: 30vw;
    height: 30vh;
    background-color: red;
    position: absolute;
    left: 50%;
    top: 50%;
  }
</style>