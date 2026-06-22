<template>
  <el-container class="layout-container">
    <el-aside class="layout-aside">
      <div class="logo">
        <h2>青云宗管理系统</h2>
      </div>
      <el-menu 
        :default-active="activeMenu" 
        class="layout-menu"
        router
        mode="vertical"
      >
        <el-menu-item index="/treasure">
          <el-icon><component :is="icons.treasure" /></el-icon>
          <span>宝物管理</span>
        </el-menu-item>
        <el-menu-item index="/magic-tool">
          <el-icon><component :is="icons.tool" /></el-icon>
          <span>法器管理</span>
        </el-menu-item>
        <el-menu-item index="/weapon">
          <el-icon><component :is="icons.sword" /></el-icon>
          <span>兵器管理</span>
        </el-menu-item>
        <el-menu-item index="/pill">
          <el-icon><component :is="icons.pill" /></el-icon>
          <span>丹药管理</span>
        </el-menu-item>
        <el-menu-item index="/spirit-material">
          <el-icon><component :is="icons.leaf" /></el-icon>
          <span>灵材管理</span>
        </el-menu-item>
        <el-menu-item index="/skill">
          <el-icon><component :is="icons.book" /></el-icon>
          <span>功法管理</span>
        </el-menu-item>
        <el-menu-item index="/spirit-stone">
          <el-icon><component :is="icons.gem" /></el-icon>
          <span>灵石管理</span>
        </el-menu-item>
        <el-menu-item index="/order">
          <el-icon><component :is="icons.order" /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
        <el-sub-menu index="/system">
          <template #title>
            <el-icon><component :is="icons.setting" /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/system/user">
            <el-icon><component :is="icons.user" /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/system/role">
            <el-icon><component :is="icons.role" /></el-icon>
            <span>角色管理</span>
          </el-menu-item>
          <el-menu-item index="/system/permission">
            <el-icon><component :is="icons.key" /></el-icon>
            <span>权限管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/menu">
          <el-icon><component :is="icons.menu" /></el-icon>
          <span>菜单管理</span>
        </el-menu-item>
        <el-menu-item index="/dict">
          <el-icon><component :is="icons.dict" /></el-icon>
          <span>数据字典</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container class="layout-main">
      <el-header class="layout-header">
        <div class="header-right">
          <el-button @click="logout" type="text">退出登录</el-button>
        </div>
      </el-header>
      <el-main class="layout-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { 
  Wallet, 
  Tools, 
  KnifeFork, 
  Cherry, 
  Flag, 
  Document, 
  GoldMedal, 
  ShoppingCart,
  Setting,
  User,
  UserFilled,
  Key,
  Menu,
  Coin
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const icons = {
  treasure: Wallet,
  tool: Tools,
  sword: KnifeFork,
  pill: Cherry,
  leaf: Flag,
  book: Document,
  gem: GoldMedal,
  order: ShoppingCart,
  setting: Setting,
  user: User,
  role: UserFilled,
  key: Key,
  menu: Menu,
  dict: Coin
}

const activeMenu = computed(() => route.path)

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.layout-aside {
  width: 200px;
  background-color: #2b3a42;
  color: #fff;
}

.logo {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #3a4a54;
}

.logo h2 {
  margin: 0;
  font-size: 16px;
  font-weight: bold;
}

.layout-menu {
  border-right: none;
}

.layout-menu :deep(.el-menu-item) {
  color: #b8c7ce;
}

.layout-menu :deep(.el-menu-item:hover) {
  background-color: #3a4a54;
}

.layout-menu :deep(.el-menu-item.is-active) {
  background-color: #1f9fff;
  color: #fff;
}

.layout-main {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.layout-header {
  height: 60px;
  line-height: 60px;
  padding: 0 20px;
  background-color: #fff;
  border-bottom: 1px solid #e8e8e8;
  display: flex;
  justify-content: flex-end;
}

.header-right {
  display: flex;
  align-items: center;
}

.layout-content {
  flex: 1;
  padding: 20px;
  overflow: auto;
}
</style>
