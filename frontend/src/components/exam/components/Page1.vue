<!--这里是展示页面的下面部分-->
<template>
  <div class="home-page page1">
    <div class="home-page-wrapper" id="page1-wrapper">
      <div class="page1-bg" style="transform: translate(0px, 200.953px);"></div>
      <div class="title-line-wrapper page1-line"></div>
      <list :data-source="features"/>
    </div>
  </div>
</template>

<script>
import List from './List'

const featuresCN = [
  {
    title: '优雅美观',
    content: '基于 Ant Design Vue精心设计',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/VriUmzNjDnjoFoFFZvuh.svg',
    color: '#13C2C2',
    shadowColor: 'rgba(19,194,194,.12)'
  },
  {
    title: '常见设计模式',
    content: '借鉴常用网站设计理念',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/smwQOoxCjXVbNAKMqvWk.svg',
    color: '#2F54EB',
    shadowColor: 'rgba(47,84,235,.12)'
  },
  {
    title: '最新技术栈',
    content: '使用 Vue/vuex/antd 等前端前沿技术开发',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/hBbIHzUsSbSxrhoRFYzi.svg',
    color: '#F5222D',
    shadowColor: 'rgba(245,34,45,.12)'
  },
  {
    title: '实用场景',
    content: '针对线上考试特点设计',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/BISfzKcCNCYFmTYcUygW.svg',
    color: '#1AC44D',
    shadowColor: 'rgba(26,196,77,.12)'
  },
  {
    title: '最佳实践',
    content: 'Web技术课程实践作业',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/pbmKMSFpLurLALLNliUQ.svg',
    color: '#FA8C16',
    shadowColor: 'rgba(250,140,22,.12)'
  },
  {
    title: 'UI 测试',
    content: '测试多种前端控件确保美观',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/RpJIQitGbSCHwLMimybX.svg',
    color: '#1890FF',
    shadowColor: 'rgba(24,144,255,.12)'
  }
]

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Page1',
  components: {
    List
  },
  data() {
    return {
      features: featuresCN
    }
  },
  created() {
    this.updateFeatures()
  },
  methods: {
    updateFeatures() {
      const arr = featuresCN
      const newArr = [[], []]
      arr.forEach((item, index) => {
        newArr[Math.floor(index / 3)].push(item)
      })
      this.features = newArr
    }
  }
}
</script>
<style lang="less" scoped>

.page1-line.title-line-wrapper {
  width: 312px;
  margin: 24px auto 76px;

  .title-line {
    animation: page1TitleLine 3s ease-in-out 1.5s infinite;
  }
}

.page1-bg {
  font-size: 320px;
  color: #ebedf0;
  position: absolute;
  width: 100%;
  text-align: center;
  opacity: .25;
  top: 0;
  transform: translateY(960px);
}


</style>