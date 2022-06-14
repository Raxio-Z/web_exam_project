<template>
  <div class="settings-wrapper">
    <a-row :gutter="16">
      <a-col :md="24" :lg="16">

        <a-form layout="vertical">
          <a-form-item
              label="昵称"
          >
            <a-input placeholder="给自己起个名字"/>
          </a-form-item>
          <a-form-item
              label="个人简介"
          >
            <a-textarea rows="4" placeholder="You are not alone."/>
          </a-form-item>

          <a-form-item>
            <a-button type="primary" @click="handleSubmit">提交</a-button>
            <a-button style="margin-left: 8px" @click="handleSave">保存</a-button>
          </a-form-item>
        </a-form>

      </a-col>

    </a-row>

    <avatar-modal ref="modal">

    </avatar-modal>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Settings",
  methods:{
    handleSubmit(){
      this.$notification.success({
        message:'提交成功！'
      })
    },
    handleSave(){
      this.$notification.success({
        message:'保存成功！'
      })
    }
  }
}
</script>

<style lang="less" scoped>

.settings-wrapper {
  width: 500px;
  margin: 0 auto;
}

</style>