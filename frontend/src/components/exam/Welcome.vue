<template>
  <div>
    <div class="banner-wrapper">
      <!--    走马灯，用于展示-->
      <div class="banner-image-wrapper" style="opacity: 1;">
        <a-carousel class="carousel" arrows autoplay>
          <div v-for="i in 5" :key="i">
            <img :src="`/home/cover${i}.jpg`" style="height: 324px"/>
          </div>
        </a-carousel>
      </div>
    </div>

    <page1/>
  </div>
</template>

<script>
import Page1 from './components/Page1'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Welcome",
  components:{
    Page1
  }
}
</script>

<style lang="less" scoped>
.banner-wrapper {
  margin: 20px auto 0;
  height: 300px;
  width: 100%;
  max-width: 1500px;
  position: center;

  .banner-image-wrapper {
    height: 300px;
    position: center;
    right: 8%;
    width: 600px;
    margin:0 auto;
    top: 0;
    bottom: 0;
    opacity: 0;
  }
}

</style>