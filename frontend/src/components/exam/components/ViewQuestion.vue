<template>
  <a-modal title="题目信息" :width="640" :visible="visible" :confirmLoading="confirmLoading" @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <h3><b>题干：</b></h3>
        <div v-html="question.name"></div>
        <br>
        <h3><b>选项：</b></h3>
        <ul>
          <li v-for="option in question.options" :key="option.questionOptionId" v-html="option.questionOptionContent"/>
        </ul>
        <br>
        <h3><b>答案：</b></h3>
        <ul>
          <li v-for="option in question.options" :key="option.id" v-show="judge(option)"
              v-html="option.questionOptionContent"/>
        </ul>
        <br>
      </a-form>
    </a-spin>
    <template slot="footer">
      <a-button key="cancel" @click="handleCancel">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>

export default {
  // 问题查看的弹出框，用于查看问题/修改问题
  name: 'ViewQuestion',
  data() {
    return {
      visible: false,
      confirmLoading: false,

      form: this.$form.createForm(this),
      // 每个问题
      question: {},
    }
  },
  methods: {
    edit(record) {
      this.visible = true
      // 把当前的记录赋值到data中的变量
      this.question = record

      console.log(record)

    },

    judge(option) {
      var i = 0
      for (; i < this.question.answers.length; i++) {
        if (option.questionOptionId === this.question.answers[i].questionOptionId)
          return true
      }
      return false
    },

    handleCancel() {
      // clear form & currentStep
      this.visible = false
    },
  }
}
</script>
