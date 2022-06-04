<template>
  <div class="user-layout">
    <div class="top">
      <div class="header">
        <a href="/">
          <img src="../assets/logo.svg" class="logo">
          <span class="title">Online Exam</span>
        </a>
      </div>

      <div class="description">
        SpringBoot+Vue
      </div>

    </div>

    <div class="mid">
      <router-view></router-view>
    </div>

    <div class="footer">
      <div class="CodeAdd">
        <a href="https://github.com/RealLiuzheng/web_exam_project">代码仓库</a>
      </div>
      <div class="author">
        2022&nbsp;
        <a class="lz" href="https://github.com/RealLiuzheng">RealLiuZheng</a>
        &nbsp;& &nbsp;
        <a class="cym" href="https://github.com/codejoker-c">codejoker-c</a>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "UserLayout"
}
</script>

<style scoped>

.user-layout{
  height: 100%;
  width: 100%;
  background: url("../assets/background.svg");
  background-size: 100%;
}

.top{
  text-align: center;
}

.header{
  height: 44px;
  line-height: 44px;
}

.logo {
  height: 44px;
  vertical-align: top;
  margin-right: 16px;
  border-style: none;
}

.title {
  font-size: 33px;
  color: rgba(0, 0, 0, .85);
  font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
  font-weight: 600;
  position: relative;
  top: 2px;
}

.desc {
  font-size: 14px;
  color: rgba(0, 0, 0, 0.45);
  margin-top: 12px;
  margin-bottom: 40px;
}

.footer{
  position: absolute;
  width: 100%;
  bottom: 0;
  padding: 0 16px;
  margin: 48px 0 24px;
  text-align: center;
}

.CodeAdd{
  margin-bottom: 8px;
  font-size: 14px;
}

.author{
  color: rgba(0, 0, 0, 0.45);
  font-size: 14px;
}

</style>