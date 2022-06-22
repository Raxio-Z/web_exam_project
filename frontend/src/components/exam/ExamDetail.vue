<template>
  <a-layout>

    <a-layout-header class="header">
      <!--   v-if="examDetail.exam" 是为了防止 异步请求时页面渲染的时候还没有拿到这个值而报错， 下面多处这个判断都是这个道理 -->

      <span class="profile" v-if="examDetail.exam">
              {{ examDetail.exam.name }}
              <span style="font-size:15px;">{{ examDetail.exam.subject }} </span>
            </span>

      <span style="float: right;">
        <span class="countdown" v-if="examDetail.exam">
          考试限时：{{ examDetail.exam.duration }}分钟
        </span>

        <a-button type="danger" ghost style="margin-right: 60px;" @click="finishExam()">交卷</a-button>

        <div class="selfie" style="float: right;">
          <selfie></selfie>
        </div>
      </span>
    </a-layout-header>

    <a-layout>
      <a-layout-sider
          class="sider"
          width="190"
          :style="{background: '#ffffff',overflow: 'auto', height: '100vh', position: 'fixed', left: 0 }"
          collapsible
      >
        <a-menu
            mode="inline"
            :defaultSelectedKeys="['1']"
            :defaultOpenKeys="['question_radio', 'question_check', 'question_judge']"
            :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="question_radio">

            <span slot="title" v-if="examDetail.exam">
              <a-icon type="check-circle" theme="twoTone"/>
              单选题(每题{{ examDetail.exam.radioScore }}分)
            </span>

            <a-menu-item v-for="(item, index) in examDetail.radioIds" :key="item" @click="getQuestionDetail(item)">
              <a-icon type="smile" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
              题目{{ index + 1 }}
              <!--              <a-icon type="smile" theme="twoTone" twoToneColor="#52c41a" v-if="starMap.get(item)"/>-->
            </a-menu-item>

          </a-sub-menu>

          <a-sub-menu key="question_check">
            <span slot="title" v-if="examDetail.exam">
              <a-icon type="check-square" theme="twoTone"/>
              多选题(每题{{ examDetail.exam.checkScore }}分)
            </span>

            <a-menu-item v-for="(item, index) in examDetail.checkIds" :key="item" @click="getQuestionDetail(item)">
              <a-icon type="smile" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
              题目{{ index + 1 }}
              <!--              <a-icon type="smile" theme="twoTone" twoToneColor="#52c41a" v-if="starMap.get(item)"/>-->
            </a-menu-item>
          </a-sub-menu>

          <a-sub-menu key="question_judge">
            <span slot="title" v-if="examDetail.exam"><a-icon type="like" theme="twoTone"/>
              判断题(每题{{ examDetail.exam.judgeScore }}分)
            </span>

            <a-menu-item v-for="(item, index) in examDetail.judgeIds" :key="item" @click="getQuestionDetail(item)">
              <a-icon type="smile" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
              题目{{ index + 1 }}
              <!--              <a-icon type="smile" theme="twoTone" twoToneColor="#52c41a" v-if="starMap.get(item)"/>-->
            </a-menu-item>
          </a-sub-menu>

        </a-menu>
      </a-layout-sider>

      <a-layout :style="{ marginLeft: '200px' }">

        <a-layout-content :style="{ margin: '24px 16px 0',height: '84vh', overflow: 'initial' }">
          <div :style="{ padding: '24px', background: '#fff',height: '84vh'}">
            <span v-show="currentQuestion === ''"
                  style="font-size: 30px;font-family: Consolas,serif">
              欢迎参加考试，请点击左侧题目编号开始答题
            </span>

            <div class="QuestionDetail">
              <strong style="font-size: 22px">{{ currentQuestion.type }} </strong>
              <p style="font-size: 18px;margin-top: 10px" v-html="currentQuestion.name"></p>
              <!-- 单选题和判断题 --> <!-- key不重复只需要在一个for循环中保证即可 -->
              <a-radio-group @change="onRadioChange" v-model="radioValue"
                             v-if="currentQuestion.type === '单选题' || currentQuestion.type === '判断题'">
                <a-radio v-for="option in currentQuestion.options" :key="option.questionOptionId" :style="optionStyle"
                         :value="option.questionOptionId">
                  {{ option.questionOptionContent }}
                </a-radio>
              </a-radio-group>

              <!-- 多选题 -->
              <a-checkbox-group @change="onCheckChange" v-model="checkValues" v-if="currentQuestion.type === '多选题'">
                <a-checkbox v-for="option in currentQuestion.options" :key="option.questionOptionId"
                            :style="optionStyle"
                            :value="option.questionOptionId">
                  {{ option.questionOptionContent }}
                </a-checkbox>
              </a-checkbox-group>
            </div>

          </div>
        </a-layout-content>

        <a-layout-footer :style="{ textAlign: 'center' }"/>

      </a-layout>
    </a-layout>

  </a-layout>
</template>

<script>
import Selfie from "@/components/exam/components/Selfie";
import request from "@/utils/request";


export default {
  name: 'ExamDetail',
  components: {
    Selfie
  },
  data() {
    return {
      // 考试详情对象
      examDetail: {},
      // 用户做过的问题都放到这个数组中，键为问题id, 值为currentQuestion(其属性answers属性用于存放答案选项地id或ids),，用于存放用户勾选的答案
      answersMap: {},
      //用户标记的问题
      starMap: {},
      // 当前用户的问题
      currentQuestion: '',
      // 单选或判断题的绑定值，用于从answersMap中初始化做题状态
      radioValue: '',
      // 多选题的绑定值，用于从answersMap中初始化做题状态
      checkValues: [],
      optionStyle: {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
        marginLeft: '0px',
        fontSize: '16px'
      }
    }
  },
  mounted() {
    this.answersMap = new Map()
    this.starMap = new Map()
    // 从后端获取考试的详细信息，渲染到考试详情里

    // TODO 修改url值
    // getExamDetail(this.$route.params.id)

    // request.get("/exam/all", this.$route.params.id)
    //     .then(res => {
    //       if (res.code === 0) {
    //         // 赋值考试对象
    //         this.examDetail = res.data
    //         return res.data
    //       } else {
    //         this.$notification.error({
    //           message: '获取考试详情失败',
    //           description: res.msg
    //         })
    //       }
    //     })

    this.examDetail = {
      exam: {
        name: '三角函数',
        subject: '数学',
        duration: 90,
        radioScore: 5,
        checkScore: 5,
        judgeScore: 5
      },
      radioIds: [1, 5, 6],
      checkIds: [2, 7, 8],
      judgeIds: [3, 4, 9],
    }

    this.currentQuestion = {
      type: '单选题',
      name: '这是问题题目',
      options: [
        {
          questionOptionId: 1,
          questionOptionContent: '第一个选项'
        },
        {
          questionOptionId: 2,
          questionOptionContent: '第二个选项'
        }
      ]
    }

  },
  methods: {

    getQuestionDetail(questionId) {
      // 问题切换时从后端拿到问题详情，渲染到前端content中
      const that = this
      // 清空问题绑定的值
      this.radioValue = ''
      this.checkValues = []

      //TODO: 修改url
      // getQuestionDetail(questionId)
      request.get("/exam/all", questionId)
          .then(res => {
            if (res.code === 0) {
              // 赋值当前考试对象
              // 通过currentQuestion得到数据
              that.currentQuestion = res.data
              // 查看用户是不是已经做过这道题又切换回来的，answersMap中查找，能找到这个题目id对应的值数组不为空说明用户做过这道题
              if (that.answersMap.get(that.currentQuestion.id)) {
                // 说明之前做过这道题了
                if (that.currentQuestion.type === '单选题' || that.currentQuestion.type === '判断题') {
                  that.radioValue = that.answersMap.get(that.currentQuestion.id)[0]
                } else if (that.currentQuestion.type === '多选题') {
                  // 数组是引用类型，因此需要进行拷贝，千万不要直接赋值
                  Object.assign(that.checkValues, that.answersMap.get(that.currentQuestion.id))
                }
              }
              return res.data
            } else {
              this.$notification.error({
                message: '获取问题详情失败',
                description: res.msg
              })
            }
          })
    },
    /**
     * 单选题勾选是触发的变化事件
     * @param e
     */
    onRadioChange(e) {
      const userOptions = []
      userOptions.push(e.target.value)
      // 更新做题者选择的答案
      this.answersMap.set(this.currentQuestion.id, userOptions)
    },
    /**
     * 多选题触发的变化事件
     * @param checkedValues
     */
    onCheckChange(checkedValues) {
      // 更新做题者选择的答案
      this.answersMap.set(this.currentQuestion.id, checkedValues)
    },
    _strMapToObj(strMap) {
      const obj = Object.create(null)
      for (const [k, v] of strMap) {
        obj[k] = v
      }
      return obj
    },
    /**
     *map转换为json
     */
    _mapToJson(map) {
      return JSON.stringify(this._strMapToObj(map))
    },
    /**
     * 结束考试并交卷
     */
    finishExam() {
      // TODO: 修改url
      // Todo:向后端提交作答信息数组answersMap
      // finishExam(this.$route.params.id, this._mapToJson(this.answersMap))

      var that = this
      this.$confirm({
        title: '提示',
        content: '确定交卷 ?',
        onOk() {
          that.submitResponse()
        },
        onCancel() {
        }
      })

    },
    submitResponse() {
      request.get("/exam/all", this.$route.params.id, this._mapToJson(this.answersMap))
          .then(res => {
            if (res.code === 0) {
              // 考试交卷，后端判分完成，然后跳转到我的考试界面
              this.$notification.success({
                message: '考卷提交成功！'
              })
              this.$router.push('/myExam')
              return res.data
            } else {
              this.$notification.error({
                message: '交卷失败！',
                description: res.msg
              })
            }
          })
    }
  }
}
</script>

<style lang="less" scoped>

.header {
  background: #ebedf0;

  .profile {
    font-size: 25px;
    margin-left: 0px;
  }

  .countdown {
    margin-right: 60px;
    font-size: 20px
  }

}

.QuestionDetail {
  margin-left: 50px;

}

</style>
