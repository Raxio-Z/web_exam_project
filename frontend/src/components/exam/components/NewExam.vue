<template>
  <!--  对话框浮层-->
  <a-modal
      title="创建考试"
      :width="640"
      :visible="visible"
      :confirmLoading="confirmLoading"
      @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-steps :current="currentStep" :style="{ marginBottom: '28px' }" size="small">
        <a-step title="考试描述"/>
        <a-step title="每题分数"/>
        <a-step title="选择题目"/>
      </a-steps>
      <a-form :form="form">
        <!-- step1 -->
        <div v-show="currentStep === 0">
          <a-form-item
              label="考试名称"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-input v-decorator="['name', {rules: [{required: true}]}]"/>
          </a-form-item>
          <a-form-item
              label="考试限时"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-input-number
                :min="1"
                :max="200"
                v-decorator="['elapse', {initialValue: '90',rules: [{required: true}]}]"
            />
            分钟
          </a-form-item>
          <a-form-item
              label="考试科目"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-select
                style="width: 120px"
                v-decorator="['subject', {rules: [{required: true}]}]"
            >
              <a-select-option v-for="category in categories" :key="category.examCategoryId">
                {{ category.examCategoryName }}
              </a-select-option>
            </a-select>

          </a-form-item>
          <a-form-item
              label="难度"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-select
                style="width: 120px"
                v-decorator="['level', {rules: [{required: true}]}]"
            >
              <a-select-option v-for="level in levels" :key="level.questionLevelId">
                {{ level.questionLevelName }}
              </a-select-option>
            </a-select>

          </a-form-item>
        </div>

        <div v-show="currentStep === 1">
          <a-form-item
              label="单选题"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-input-number
                :min="1"
                :max="20"
                v-decorator="['radioScore', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
              label="多选题"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-input-number
                :min="1"
                :max="20"
                v-decorator="['checkScore', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
              label="判断题"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
          >
            <a-input-number
                :min="1"
                :max="20"
                v-decorator="['judgeScore', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>
        </div>

        <div v-show="currentStep === 2">
          <a-form-item
              label="选择单选题"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              enterButton="Search"
          >
            <!-- 单选 -->
            <a-select
                mode="multiple"
                :size="size"
                placeholder="请选择单选题"
                style="width: 100%"
                @popupScroll="popupScroll"
                @change="handleRadioChange"
            >
              <a-select-option v-for="radio in radios" :value="radio.name" :key="radio.id">
                {{ radio.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item
              label="选择多选题"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              enterButton="Search"
          >
            <!-- 多选 -->
            <a-select
                mode="multiple"
                :size="size"
                placeholder="请选择多选题"
                style="width: 100%"
                @popupScroll="popupScroll"
                @change="handleCheckChange"
            >
              <a-select-option v-for="check in checks" :value="check.name" :key="check.id">
                {{ check.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item
              label="选择判断题"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              enterButton="Search"
          >
            <!-- 判断 -->
            <a-select
                mode="multiple"
                :size="size"
                placeholder="请选择判断题"
                style="width: 100%"
                @popupScroll="popupScroll"
                @change="handleJudgeChange"
            >
              <a-select-option v-for="judge in judges" :value="judge.name" :key="judge.id">
                {{ judge.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </div>
      </a-form>
    </a-spin>
    <template slot="footer">
      <a-button key="back" @click="backward" v-if="currentStep > 0" :style="{ float: 'left' }">上一步</a-button>
      <a-button key="cancel" @click="handleCancel">取消</a-button>
      <a-button key="forward" :loading="confirmLoading" type="primary" @click="handleNext(currentStep)">
        {{ currentStep === 2 && '完成' || '下一步' }}
      </a-button>
    </template>
  </a-modal>
</template>

<script>
// TODO 需要建立地址
import request from "@/utils/request";

//import { getExamQuestionTypeList, examCreate } from '../../../api/exam' //这是两个后端接口

const stepForms = [
  ['name', 'elapse', 'desc'],
  ['radioScore', 'checkScore', 'judgeScore'],
  ['option']
]

export default {
  name: 'NewExam',
  data() {
    return {
      labelCol: {
        xs: {span: 24},
        sm: {span: 7}
      },
      size: 'default',
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 13}
      },
      // 弹出框是否显示
      visible: false,
      confirmLoading: false,
      currentStep: 0,
      mdl: {},

      form: this.$form.createForm(this),
      // 单选题对象列表
      radios: [],
      // 多选题对象列表
      checks: [],
      // 判断题对象列表
      judges: [],
      categories: [],
      levels: []
    }
  },
  methods: {
    // 点击新建按钮之后调用create函数获取问题列表
    create() {
      this.visible = true
      //从后端数据获取单选题、多选题和判断题的列表
      // getExamQuestionTypeList().then(res => {
      request.get('exam/question/select').then(res => {
        console.log(res)
        if (res.code === '0') {
          console.log(res.data)
          this.radios = res.data.radios
          this.checks = res.data.checks
          this.judges = res.data.judges
          this.categories = res.data.categories
          this.levels = res.data.levels
          for (var i = 0; i < this.levels.length; i++) {
            if (this.levels[i].questionLevelName === 'hard')
              this.levels[i].questionLevelName = '难'
            else if (this.levels[i].questionLevelName === 'mid')
              this.levels[i].questionLevelName = '中'
            else if (this.levels[i].questionLevelName === 'easy')
              this.levels[i].questionLevelName = '易'
          }
        } else {
          this.$notification.error({
            message: '获取问题列表失败',
            description: res.msg
          })
        }
      }).catch(err => {
        // 失败就弹出警告消息
        this.$notification.error({
          message: '获取问题列表失败',
          description: err.message
        })
      })
      // this.radios = [
      //   {
      //     name: '单选1',
      //     id: 1
      //   },
      //   {
      //     name: "单选2",
      //     id: 2
      //   }
      // ]
      // this.checks = [
      //   {
      //     name: '多选3',
      //     id: 3
      //   },
      //   {
      //     name: "多选4",
      //     id: 4
      //   }
      // ]
      // this.judges = [
      //   {
      //     name: '判断5',
      //     id: 5
      //   },
      //   {
      //     name: "判断6",
      //     id: 6
      //   }
      // ]
    },
    popupScroll() {
      console.log('popupScroll')
    },
    handleNext(step) {
      // 处理下一步或者完成事件
      const {form: {validateFields}} = this
      const currentStep = step + 1
      if (currentStep <= 2) {
        // stepForms
        validateFields(stepForms[this.currentStep], (errors) => {
          if (!errors) {
            this.currentStep = currentStep
          }
        })
        return
      }
      // last step，最后一步，代表完成考试编写，需要点击"完成"创建考试
      this.confirmLoading = true
      validateFields((errors, values) => { // values就是表单中校验的值，后面提交到后端接口时主要就是用这个values
        // 设置单选题、多选题和判断题的内容
        values.radios = this.radios
        values.checks = this.checks
        values.judges = this.judges
        console.log('提交数据到后端')
        this.confirmLoading = false
        if (!errors) {
          // 在这里把创建的考试的内容(存放在values中)提交给后端接口，需要的参数都已经封装成values
          console.log('values:', values)

          // ToDo 需要向后端传参数value，怎么传
          // TODO 地址是多少
          request.post('/exam/create', values).then(res => {
            // 成功就跳转到结果页面
            console.log(res)
            if (res.code === '0') {
              this.$notification.success({
                message: '创建成功',
                description: '考试创建成功'
              })
              // 关闭弹出框
              this.visible = false
              this.$emit('ok')
            }
          }).catch(err => {
            // 失败就弹出警告消息
            this.$notification.error({
              message: '考试创建失败',
              description: err.message
            })
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    backward() {
      this.currentStep--
    },
    handleCancel() {
      // clear form & currentStep
      this.visible = false
      this.currentStep = 0
    },
    // 改变选择的题目列表,这里需要分单选、多选和判断进行单独更新，下面的代码针对radios、checks和judges分别适配
    handleRadioChange(values) {
      console.log(values)
      // 更新单选题的信息，两层循环判断每一个是否已选中
      for (let i = 0; i < this.radios.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.radios[i].name
        // 当前问题是否被选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被选中
            checked = true
            this.radios[i].checked = true
            break
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.radios[i].checked = false
        }
      }
    },

    // 更新多选题信息
    handleCheckChange(values) {
      console.log(values)
      // 更新单选题的信息
      for (let i = 0; i < this.checks.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.checks[i].name
        // 当前问题是否被选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被选中
            checked = true
            this.checks[i].checked = true
            break
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.checks[i].checked = false
        }
      }
    },

    // 更新判断题信息
    handleJudgeChange(values) {
      console.log(values)
      // 更新单选题的信息
      for (let i = 0; i < this.judges.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.judges[i].name
        // 当前问题是否被选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被选中
            checked = true
            this.judges[i].checked = true
            break
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.judges[i].checked = false
        }
      }
    }
  }
}
</script>









