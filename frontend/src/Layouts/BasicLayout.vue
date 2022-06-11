<template>

  <a-layout class="index-header">
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo1">
        <a href="/">
          <img src="../assets/logo.svg" class="logo" alt="logo">
          <span class="title">Online Exam</span>
        </a>
      </div>
      <!--      头像部分  靠右放置-->
      <div class="selfie">
        <selfie></selfie>
      </div>

      <a-menu theme="dark"
              mode="horizontal"
              class="headMenu"
              v-model="current"
              :style="{ lineHeight: '64px' }"
      >

        <a-menu-item key="welcome">
          <router-link :to="{name:'welcome'}">
            <div class="welcome">
              <a-icon type="home"/>
              <span>首页</span>
            </div>
          </router-link>


        </a-menu-item>
        <a-menu-item key="examList">
          <router-link :to="{name:'examList'}">
            <div class="exam-list">
              <a-icon type="appstore"/>
              <span>考试列表</span>
            </div>
          </router-link>
        </a-menu-item>
        <a-menu-item key="myExam">
          <router-link :to="{name:'myExam'}">
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
import Selfie from "@/components/exam/components/Selfie";

export default {
  name: "BasicLayout",
  components:{
    Selfie
  },
  data(){
    return {
      current:[this.$route.name],
    }
  }

}
</script>

<style lang="less" scoped>
.headMenu{
  text-align: center;
}
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

.selfie{
  width: 250px;
  height: 30px;
  float: right;

  .username{
    margin-left: 5px;
    color: rgba(255, 255, 255, 0.95);
  }
}

.mid-content {
  width: 100%;
}

</style>