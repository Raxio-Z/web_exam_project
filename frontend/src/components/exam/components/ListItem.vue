<!--这是每个小标的样式（下面那六个小标）-->
<template>
  <li :key="item.title">
    <div class="page1-box">
      <div class="page1-point-wrapper"></div>
      <div class="page1-image" :style="{ boxShadow: `${item.shadowColor} 0px 6px 12px` }">
        <img :src="item.src" />
      </div>
      <h3>{{ item.title }}</h3>
      <p>{{ item.content }}</p>
    </div>
  </li>
</template>

<script>
export default {
  name: 'ListItem',
  props: {
    item: {
      type: Object,
      required: true
    }
  }
}
</script>
<style lang="less" scoped>
.page1-point-wrapper {
  position: absolute;
  width: 0;
  left: 50%;
  top: 0;
  .point-0 {
    width: 4px;
    height: 4px;
  }
  .point-2, .point-ring {
    width: 10px;
    height: 10px;
  }
  .point-ring {
    border-style: solid;
    border-width: 1px;
    background: transparent !important;
  }
  .point-1 {
    width: 6px;
    height: 6px;
  }
  .point-3 {
    width: 15px;
    height: 15px;
  }
  i {
    display: inline-block;
    border-radius: 100%;
    position: absolute;
    opacity: 0;
    transform: translate(0, 30px);
  }
}
</style>