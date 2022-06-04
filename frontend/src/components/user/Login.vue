<template>
<div class="user-login">
    <a-tabs
        :active-key="keyState"
        :tab-bar-style="{textAlign:'center',borderBottom: 'unset'}"
        @change="tabClick"
    >

      <a-tab-pane key="login" tab="登录">
        <a-form class="login-form" @submit="loginSubmit">
        <a-form-item>
          <a-input
              size="large"
              class="login-username"
              type="text"
              placeholder="请输入邮箱/手机号"
              v-model="loginState.username"
              v-decorator="[
                'username',
                {rules: [{ required: true, message: '请输入邮箱/手机号' }, { validator: validateEmailOrPhone }], validateTrigger: 'change'}
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
              v-model="loginState.password"
              v-decorator="[
                'password',
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
</template>

<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data(){
    return{
      button_dis:false,
      loginState:{
        username:'',
        password:''
      },
      regState:{
        username:'',
        phone:'',
        password:''
      },
      keyState:'login'
    }
  },
  methods:{
    loginSubmit(){

    },
    registerSubmit(){

    },
    tabClick(key){
      this.keyState=key;
    },
    validateEmailOrPhone(){

    }
  }

}
</script>

<style lang="less" scoped>

.login-username,.login-password,.register-username,.register-password,.register-password-confirm{
  width: 350px;
  height: 40px;
}

</style>