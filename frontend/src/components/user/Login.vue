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
          <a-form-item>
            <a-input
                ref="loginUsername"
                size="large"
                class="login-username"
                type="text"
                placeholder="请输入邮箱/手机号"

                v-decorator="[
                'loginUsername',
                {rules: [
                    {
                      required: true,
                      message: '请输入邮箱/手机号'
                    }
                  ]
                }
              ]"
            >
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>

          </a-form-item>

          <a-form-item>
            <a-input
                size="large"
                class="login-password"
                type="password"
                autocomplete="false"
                placeholder="请输入密码"

                v-decorator="[
                'loginPassword',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>

          <a-form-item style="margin-top:24px">
            <a-button
                size="large"
                type="primary"
                htmlType="submit"
                class="login-button"
                :loading="button_dis"
                :disabled="button_dis"
            >确定
            </a-button>
          </a-form-item>
        </a-form>
      </a-tab-pane>

      <a-tab-pane key="register" tab="注册">
        <a-form class="register-form" @submit="registerSubmit">
          <a-form-item>
            <a-input
                size="large"
                class="register-username"
                type="text"
                placeholder="请输入邮箱/手机号"
                v-model="regState.username"
                v-decorator="[
                'register-username',
                {rules: [{ required: true, message: '请输入邮箱/手机号' }, { validator: validateEmailOrPhone }], validateTrigger: 'change'}
              ]"
            >

            </a-input>

          </a-form-item>

          <a-form-item>
            <a-input
                size="large"
                class="register-password"
                type="password"
                autocomplete="false"
                placeholder="至少6位密码，区分大小写"
                v-model="regState.password"
                v-decorator="[
                'register-password',
                {rules: [{ required: true, message: '至少6位密码，区分大小写' }], validateTrigger: 'blur'}
              ]"
            >
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input
                size="large"
                class="register-password-confirm"
                type="password"
                autocomplete="false"
                placeholder="确认密码"
                v-model="regState.password"
                v-decorator="[
                'register-password-confirm',
                {rules: [{ required: true, message: '确认密码' }], validateTrigger: 'blur'}
              ]"
            >
            </a-input>
          </a-form-item>

          <a-form-item style="margin-top:24px">
            <a-button
                size="large"
                type="primary"
                htmlType="submit"
                class="register-button"
                :loading="button_dis"
                :disabled="button_dis"
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

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data(){
    return{
      button_dis:false,
      loginType:0,
      keyState:'login',
      State:{
        button_dis:false,
        loginType:0,
        keyState:'login'
      },
      loginState:{
        username:'',
        password:''
      },
      regState:{
        username:'',
        phone:'',
        password:''
      },

    }
  },
  //https://www.jianshu.com/p/c24997512be7
  methods:{
    loginSubmit(e){

      console.log(this.$refs.loginUsername.value)
      // // 阻止默认操作
       e.preventDefault()
      const {
        form: { validateFields },
        state,
        Login
      } = this

      state.loginBtn = true
      const validateFieldsKey =  ['loginUsername', 'loginPassword']
      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        console.log(values) // 打印用户的登录参数
        if (!err) {
          const loginParams = {} // 声明登录的参数
          loginParams.loginType = state.loginType // 登录类型，0 email, 1 username
          loginParams.userInfo = values.username // 设置用户信息，因为email还是username不确定，所以用userinfo字段来代替
          loginParams.password = values.password // 用户的密码，无加密
          console.log(loginParams)
          Login(loginParams) // 请求登录接口
              .then((res) => this.loginSuccess(res)) // 成功
              .catch(err => this.requestFailed(err)) // 失败
              .finally(() => {
                state.loginBtn = true // 不管登录成功与否，都要把按钮置灰，防止用户重复点击
              })
        } else {
          setTimeout(() => {
            state.loginBtn = false
          }, 600)
        }
      })
    },
    registerSubmit(){

    },
    tabClick(key){
      this.keyState=key;
    },
    //判断是不是邮箱
    validateEmailOrPhone(rule,value,callback){
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        state.loginType = 0
      } else {
        state.loginType = 1
      }
      callback()
    }
  }

}
</script>

<style lang="less" scoped>

.container{
  background: rgba(255,255,255,.9);
  width: 500px;
  margin:0 auto;
  border-radius: 12px;
}

.login-username,.login-password,.register-username,.register-password,.register-password-confirm{
  width: 350px;
  height: 40px;
}

</style>