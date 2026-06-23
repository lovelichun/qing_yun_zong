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
        <template v-for="menu in menuTree" :key="menu.id">
          <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path">
            <template #title>
              <el-icon><component :is="getIcon(menu.icon)" /></el-icon>
              <span>{{ menu.menuName }}</span>
            </template>
            <el-menu-item 
              v-for="child in menu.children" 
              :key="child.id" 
              :index="child.path"
            >
              <el-icon><component :is="getIcon(child.icon)" /></el-icon>
              <span>{{ child.menuName }}</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="menu.path">
            <el-icon><component :is="getIcon(menu.icon)" /></el-icon>
            <span>{{ menu.menuName }}</span>
          </el-menu-item>
        </template>
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
import { ref, computed, onMounted } from 'vue'
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
import { getUserMenus } from '@/api/system'

const router = useRouter()
const route = useRoute()

const iconMap = {
  'icon-treasure': Wallet,
  'icon-tool': Tools,
  'icon-sword': KnifeFork,
  'icon-pill': Cherry,
  'icon-leaf': Flag,
  'icon-book': Document,
  'icon-gem': GoldMedal,
  'icon-order': ShoppingCart,
  'icon-setting': Setting,
  'icon-user': User,
  'icon-role': UserFilled,
  'icon-key': Key,
  'icon-menu': Menu,
  'icon-coin': Coin,
  'icon-dict': Coin
}

const menuTree = ref([])

const activeMenu = computed(() => route.path)

const getIcon = (iconName) => {
  return iconMap[iconName] || Document
}

const buildMenuTree = (menus) => {
  const map = new Map()
  const roots = []
  
  menus.forEach(menu => {
    map.set(menu.id, { ...menu, children: [] })
  })
  
  menus.forEach(menu => {
    if (menu.parentId === 0) {
      roots.push(map.get(menu.id))
    } else {
      const parent = map.get(menu.parentId)
      if (parent) {
        parent.children.push(map.get(menu.id))
      }
    }
  })
  
  return roots.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
}

const loadMenus = async () => {
  try {
    const res = await getUserMenus()
    menuTree.value = buildMenuTree(res.data)
  } catch (error) {
    console.error('加载菜单失败:', error)
  }
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  localStorage.removeItem('roles')
  localStorage.removeItem('permissions')
  router.push('/login')
}

onMounted(() => {
  loadMenus()
})
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