<!--顶部栏头像那部分-->
<template>
  <div class="user-wrapper">
    <div class="content-box">

      <a-dropdown>
        <a class="profile">
          <a-avatar style="backgroundColor:#87d068" icon="user"/>
          <span class="usernameOn">CodingLogan</span>
        </a>

        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <a-menu-item key="1">
            <router-link :to="{ name: 'settings' }">
              <a-icon type="setting"/>
              <span>账户设置</span>
            </router-link>
          </a-menu-item>

          <a-menu-divider/>

          <a-menu-item key="3">
            <a href="javascript:;" @click="handleLogout">
              <a-icon type="logout"/>
              <span>退出登录</span>
            </a>
          </a-menu-item>
        </a-menu>

      </a-dropdown>

    </div>
  </div>
</template>

<script>
import {mapMutations} from 'vuex'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Selfie',
  methods: {
    ...mapMutations(['Logout']), // 清除token和localStorage中的信息
    handleLogout() {
      const that = this

      this.$confirm({
        title: '提示',
        content: '确定退出登录 ?',
        onOk() {
          that.Logout();
          that.$router.push({name:'login'});

        },
        onCancel() {
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>

.profile{
  .usernameOn{
    margin-left: 5px;
    color: rgba(255, 255, 255, 0.95);
  }
}

</style>
