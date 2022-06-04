<template>
<div class="user-login">

  <a-form class="login-form" @submit="submit">

    <a-tabs
        :active-key="keyState"
        :tab-bar-style="{textAlign:'center',borderBottom: 'unset'}"
        @change="tabClick"
    >

      <a-tab-pane key="login" tab="登录">
        <a-form-item>
          <a-input
              size="large"
              type="text"
              placeholder="请输入帐户名/邮箱/手机号"
              v-model:value="state.username"
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
              type="password"
              autocomplete="false"
              placeholder="请输入密码"
              v-model:value="state.password"
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

      </a-tab-pane>

    </a-tabs>


  </a-form>

</div>
</template>

<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data(){
    return{
      button_dis:false,
      state:{
        username:'',
        password:''
      },
      keyState:'login'
    }
  },
  methods:{
    submit(){

    },
    tabClick(){

    },
    validateEmailOrPhone(){

    }
  }

}
</script>

<style lang="less" scoped>

</style>