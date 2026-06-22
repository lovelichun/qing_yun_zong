import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    redirect: '/treasure',
    children: [
      { path: '/treasure', name: 'Treasure', component: () => import('@/views/treasure/index.vue') },
      { path: '/magic-tool', name: 'MagicTool', component: () => import('@/views/magic-tool/index.vue') },
      { path: '/weapon', name: 'Weapon', component: () => import('@/views/weapon/index.vue') },
      { path: '/pill', name: 'Pill', component: () => import('@/views/pill/index.vue') },
      { path: '/spirit-material', name: 'SpiritMaterial', component: () => import('@/views/spirit-material/index.vue') },
      { path: '/skill', name: 'Skill', component: () => import('@/views/skill/index.vue') },
      { path: '/spirit-stone', name: 'SpiritStone', component: () => import('@/views/spirit-stone/index.vue') },
      { path: '/order', name: 'Order', component: () => import('@/views/order/index.vue') },
      { path: '/system/user', name: 'User', component: () => import('@/views/system/user/index.vue') },
      { path: '/system/role', name: 'Role', component: () => import('@/views/system/role/index.vue') },
      { path: '/system/permission', name: 'Permission', component: () => import('@/views/system/permission/index.vue') },
      { path: '/menu', name: 'Menu', component: () => import('@/views/menu/index.vue') },
      { path: '/dict', name: 'Dict', component: () => import('@/views/dict/index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else {
    if (token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
