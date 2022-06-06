<template>

  <a-layout class="index-header">
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo1">
        <img src="../assets/logo.svg" class="logo" alt="logo">
        <span class="title">Online Exam</span>
      </div>

      <a-menu theme="dark"
              mode="horizontal"
              :default-selected-keys="['1']"
              :style="{ lineHeight: '64px' }"
      >

        <a-menu-item key="1">

          <div class="welcome">
            <a-icon type="home"/>
            <span>首页</span>
          </div>

        </a-menu-item>
        <a-menu-item key="2">
          <router-link to="{name:'examList'}">
            <div class="exam-list">
              <a-icon type="appstore"/>
              <span>考试卡片</span>
            </div>
          </router-link>
        </a-menu-item>
        <a-menu-item key="3">
          <router-link to="{name:'myExam'}">
            <div class="my-exam">
              <a-icon type="book"/>
              <span>我的考试</span>
            </div>
          </router-link>
        </a-menu-item>

      </a-menu>


    </a-layout-header>

    <a-layout-content :style="{marginTop: '64px' }">
      <div class="mid-content">
        <router-view></router-view>
      </div>
    </a-layout-content>

    <a-layout-footer class="footer" :style="{ textAlign: 'center' }">
      <div class="CodeAdd">
        <a href="https://github.com/RealLiuzheng/web_exam_project">Github代码仓库</a>
      </div>
      <div class="author">
        2022&nbsp;
        <a class="lz" href="https://github.com/RealLiuzheng">RealLiuZheng</a>
        &
        <a class="cym" href="https://github.com/codejoker-c">codejoker-c</a>
      </div>
    </a-layout-footer>

  </a-layout>

</template>

<script>
export default {
  name: "BasicLayout"
}
</script>

<style lang="less" scoped>

.logo1 {
  width: 250px;
  height: 30px;
  float: left;

  .logo {
    height: 45px;
    vertical-align: top;
    margin-top: 10px;
    margin-right: 16px;
    border-style: none;
  }
  .title {
    font-size: 18px;
    color: rgba(255, 255, 255, 0.95);
    font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
    font-weight: 600;
    position: relative;
    top: 2px;
  }
}

.mid-content{
  width: 100%;
}

</style>