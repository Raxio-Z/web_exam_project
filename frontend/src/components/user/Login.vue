<template>
  <div class="user-login">
    <div class="container">
      <a-tabs
          :active-key="keyState"
          :tab-bar-style="{textAlign:'center',borderBottom: 'unset'}"
          @change="tabClick"
      >

        <a-tab-pane key="login" tab="登录">
          <a-form class="login-form" @submit="loginSubmit">
            <a-form-item
                :rules="[{ required: true, message: '请输入邮箱' }]"
            >
              <a-input
                  size="large"
                  class="login-username"
                  ref="loginUsername"
                  type="text"
                  placeholder="请输入邮箱"
                  v-model="loginState.userUsername"
                  v-decorator="[
                'loginusername',
                {rules: [{ required: true, message: '请输入邮箱！' }, { validator: validateEmailOrPhone }], validateTrigger: 'change'}
              ]"
              >
                <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>

            <a-form-item>
              <a-input-password
                  size="large"
                  class="login-password"
                  type="password"
                  autocomplete="false"
                  placeholder="请输入密码"
                  v-model="loginState.userPassword"
                  v-decorator="[
                'login_password',
                {rules: [{ required: true, message: '请输入密码！' }], validateTrigger: 'blur'}
              ]"
              >
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input-password>
            </a-form-item>

            <a-form-item style="margin-top:24px">
              <a-button
                  size="large"
                  type="primary"
                  htmlType="submit"
                  class="login-button"
                  :disabled="login_button_dis"
              >登录
              </a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>

        <a-tab-pane key="register" tab="注册">
          <a-form class="register-form" @submit="registerSubmit">
            <a-form-item

            >
              <a-input
                  size="large"
                  class="register-username"
                  type="text"
                  placeholder="请输入邮箱"
                  v-model="regState.userUsername"
                  v-decorator="[
                'register-username',
                {rules: [{ required: true, message: '请输入邮箱/手机号' }, { validator: validateEmailOrPhone }], validateTrigger: 'change'}
              ]"
              >

              </a-input>

            </a-form-item>

            <a-popover placement="rightTop" trigger="click" :visible="levelVisible">
              <template slot="content">
                <div :style="{ width: '240px' }">
                  <div :class="['user-register', levelClass]">强度：<span>{{ levelName }}</span></div>
                  <a-progress :percent="levelPercent" :showInfo="false" :strokeColor=" levelColor "/>
                  <div style="margin-top: 10px;">
                    <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
                  </div>
                </div>
              </template>
              <a-form-item>
                <a-input-password
                    size="large"
                    type="password"
                    autocomplete="false"
                    class="register-password"
                    placeholder="至少6位密码，区分大小写"
                    v-model="regState.userPassword"
                    v-decorator="['reg_password', {rules: [{ required: true, message: '至少6位密码，区分大小写'}, { validator: this.ComputeLevel }], validateTrigger: ['change', 'blur']}]"
                ></a-input-password>
              </a-form-item>
            </a-popover>

            <a-form-item>
              <a-input-password
                  size="large"
                  class="register-password-confirm"
                  type="password"
                  autocomplete="false"
                  placeholder="确认密码"
                  v-model="userRePassword"
                  v-decorator="['reg_password2', {rules: [{ required: true, message: '至少6位密码，区分大小写' }, { validator: this.PasswordCheck }], validateTrigger: ['change', 'blur']}]"
              >
              </a-input-password>
            </a-form-item>

            <a-form-item style="margin-top:24px">
              <a-button
                  size="large"
                  type="primary"
                  htmlType="submit"
                  class="register-button"
                  :disabled="reg_button_dis"

              >注册
              </a-button>
            </a-form-item>
          </a-form>

        </a-tab-pane>

      </a-tabs>
    </div>

  </div>
</template>

<script>

import {mapMutations} from 'vuex'
import request from "@/utils/request";
import {timeFix} from "@/utils/publicFunction";

const levelNameForm = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClassForm = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColorForm = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      login_button_dis: true,
      reg_button_dis: true,
      loginType: 0,
      keyState: 'login',

      userToken: '',

      passwordLevel: 0,
      levelVisible: false,
      levelColor: levelColorForm[0],
      levelName: levelNameForm[0],
      levelClass: levelClassForm[0],
      levelPercent: 10,

      loginState: {
        userUsername: '',
        userPassword: ''
      },
      regState: {
        userUsername: '',
        userPassword: '',
      },
      userRePassword: '',
    }
  },

  watch: {
    keyState(newVal) {
      if (newVal === 'login')
        console.log('现在是登录哦')
      if (newVal === 'register')
        console.log('现在是注册哦')
    },
    loginState: {
      handler() {
        console.log('登录信息变化啦')
        if (this.loginState.userUsername !== '' && this.loginState.userPassword !== '')
          this.login_button_dis = false;
        else
          this.login_button_dis = true;
      },
      immediate: true,
      deep: true
    },
    Merge: {
      handler() {
        if (this.regState.userUsername !== '' && this.regState.userPassword !== '' && this.userRePassword !== '')
          this.reg_button_dis = false;
        else
          this.reg_button_dis = true;
      },
      immediate: true,
      deep: true
    },
    passwordLevel: {
      handler(newVal) {
        this.levelColor = levelColorForm[newVal]
        this.levelName = levelNameForm[newVal]
        this.levelClass = levelClassForm[newVal]
      }
    }
  },
  computed: {
    Merge() {
      const {regState, userRePassword} = this
      return {
        regState,
        userRePassword
      }
    }
  },

  methods: {
    // 这个是从Vuex中直接继承过来，从而可以当本地方法用，store/index.js
    ...mapMutations(['Login']),
    loginSubmit(e) {
      // // 阻止默认操作
      e.preventDefault()

      //ref也能拿到数据
      //console.log(this.$refs.loginUsername.value)
      console.log(this.loginState)

      request.post("/user/login", this.loginState)
          .then(res => this.loginResponse(res))
          .catch(err => this.loginFail(err))
      //.finally(()=>{this.login_button_dis=false})

    },
    //登录成功，进行跳转并弹出提示信息
    loginResponse(res) {
      //console.log(res)
      //console.log(res.data.userPassword)
      console.log(res)
      if (res.code === '0') {
        console.log(res.data.token)
        //this.userToken = 'Bearer ' + res.data.token;
        this.userToken = res.data.token;
        // 将用户token保存到vuex和localStorage中
        this.Login({
          Authorization: this.userToken
          , Username: this.loginState.userUsername
        });

        this.$router.push({name: 'welcome'})

        setTimeout(() => {
          this.$notification.success({
            message: '欢迎',
            description: `${timeFix()},欢迎回来`
          })
        }, 600)
      } else {
        this.$notification['error']({
          message: '错误',
          description: res.msg,
          duration: 4
        })
      }

    },
    //登录失败，提示错误
    loginFail(err) {
      console.log(err)
      this.$notification['error']({
        message: '错误',
        description: ((err.response || {}).data || {}).message || '出错啦',
        duration: 4
      })
    },

    ComputeLevel(rule, value, callback) {
      var level = 0
      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.passwordLevel = level
      this.levelPercent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.levelPercent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },

    PasswordCheck(rule, value, callback) {
      if (this.userRePassword === undefined)
        callback(new Error('请再次输入密码'))
      if (this.userRePassword && this.regState.userPassword && this.userRePassword !== this.regState.userPassword)
        callback(new Error('两次密码不一致'))
    },

    registerSubmit(e) {
      e.preventDefault()

      //ref也能拿到数据
      //console.log(this.$refs.loginUsername.value)
      console.log(this.regState)

      if (this.userRePassword === undefined) {
        this.$notification['error']({
          message: '错误',
          description: '请再次输入密码',
          duration: 4
        })
        // callback(new Error('请再次输入密码'))
      } else if (this.userRePassword && this.regState.userPassword && this.userRePassword !== this.regState.userPassword) {
        this.$notification['error']({
          message: '错误',
          description: '两次密码不一致',
          duration: 4
        })
        // callback(new Error('两次密码不一致'))
      } else {
        request.post("/user/register", this.regState)
            .then(res => this.regResponce(res))
            .catch(err => this.regFail(err))
      }


    },

    //登录成功，进行跳转并弹出提示信息
    regResponce(res) {
      console.log(res)

      if (res.code === '0') {

        location.reload()
        this.$router.go(0)
        // this.$router.push({name: 'welcome'})

        setTimeout(() => {
          this.$notification.success({
            message: '注册成功，请登录！'
          })
        }, 200)

      } else {
        this.$notification['error']({
          message: '错误',
          description: res.msg,
          duration: 4
        })
      }

    },
    //登录失败，提示错误
    regFail(err) {
      console.log(err)
      this.$notification['error']({
        message: '错误',
        description: ((err.response || {}).data || {}).message || '出错啦',
        duration: 4
      })
    },

    tabClick(key) {
      this.keyState = key;
    },
    //判断是不是邮箱
    validateEmailOrPhone(rule, value, callback) {
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        this.loginType = 0
      } else {
        this.loginType = 1
      }
      callback()
    }
  }

}
</script>

<style lang="less" scoped>

.container {
  background: rgba(255, 255, 255, .9);
  width: 500px;
  margin: 0 auto;
  border-radius: 12px;
}

.login-username, .login-password, .register-username, .register-password, .register-password-confirm {
  width: 350px;
  height: 40px;
}

.user-register {
  &.error {
    color: #ff0000;
  }

  &.warning {
    color: #ff7e05;
  }

  &.success {
    color: #52c41a;
  }
}
</style>