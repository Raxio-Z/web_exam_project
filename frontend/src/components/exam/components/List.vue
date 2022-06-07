<!--这个是那六个小标的整体，使用props传过来值，是个二维数组，使用两个v-for循环进行排版-->
<template>
  <div>
    <ul class="page1-box-wrapper" v-for="(list, index) in dataSource" :key="index">
      <template v-for="item in list">
        <list-item :item="item" :key="item.title"/>
      </template>
    </ul>
  </div>
</template>

<script>
import ListItem from './ListItem'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'List',
  components: {
    ListItem
  },
  props: {
    dataSource: {
      type: Array,
      requited: true,
      default: () => {
        return []
      }
    }
  },
  watch: {
    dataSource(val) {
      console.log('dataSource::update', val)
    }
  }
}
</script>

<style lang="less" scoped>

.page1-box-wrapper {
  margin-bottom: 62px;
  display: flex;
  align-items: flex-start;

  li {
    width: 33.33%;
    display: inline-block;
    will-change: transform;

    .page1-box {
      width: 194px;
      margin: auto;
      text-align: center;
      position: relative;

      .page1-image {
        width: 80px;
        height: 80px;
        border-radius: 40px;
        margin: 20px auto 32px;
        position: relative;
        z-index: 1;
        display: flex;
        align-items: center;
        justify-content: center;
        background: #fff;
        transition: box-shadow .3s ease-out, transform .3s ease-out;
      }

      &:hover .page1-image {
        transform: translateY(-5px);
      }

      h3 {
        color: #0d1a26;
        font-size: 16px;
        margin: 8px auto;
      }
    }
  }
}

</style>
