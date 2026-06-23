<template>
  <div class="menu-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>菜单管理</span>
          <el-button v-hasPermi="'menu:add'" type="primary" @click="handleAdd">新增菜单</el-button>
        </div>
      </template>
      
      <el-form :inline="true" :model="queryForm" class="query-form">
        <el-form-item label="菜单名称">
          <el-input v-model="queryForm.menuName" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="请选择" clearable>
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="parentId" label="父菜单ID" width="100" />
        <el-table-column prop="menuName" label="菜单名称" width="150" />
        <el-table-column prop="path" label="路径" width="200" />
        <el-table-column prop="component" label="组件" width="200" />
        <el-table-column prop="icon" label="图标" width="100" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button v-hasPermi="'menu:update'" link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button v-hasPermi="'menu:delete'" link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.pageNum"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="父菜单ID" prop="parentId">
          <el-input-number v-model="form.parentId" :min="0" />
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="form.path" placeholder="请输入路径" />
        </el-form-item>
        <el-form-item label="组件" prop="component">
          <el-input v-model="form.component" placeholder="请输入组件" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="form.icon" placeholder="请输入图标" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMenuList, addMenu, updateMenu, deleteMenu } from '@/api/menu'

const queryForm = reactive({ menuName: '', status: null })
const pagination = reactive({ pageNum: 1, pageSize: 10, total: 0 })
const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增菜单')
const formRef = ref(null)
const form = reactive({ id: null, parentId: 0, menuName: '', path: '', component: '', icon: '', sortOrder: 0, status: 1 })
const rules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  path: [{ required: true, message: '请输入路径', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const res = await getMenuList({ pageNum: pagination.pageNum, pageSize: pagination.pageSize, ...queryForm })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) { ElMessage.error('加载数据失败') }
}

const handleQuery = () => { pagination.pageNum = 1; loadData() }
const handleReset = () => { Object.assign(queryForm, { menuName: '', status: null }); handleQuery() }
const handleSizeChange = () => loadData()
const handleCurrentChange = () => loadData()
const handleAdd = () => { dialogTitle.value = '新增菜单'; Object.assign(form, { id: null, parentId: 0, menuName: '', path: '', component: '', icon: '', sortOrder: 0, status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑菜单'; Object.assign(form, row); dialogVisible.value = true }

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) { await updateMenu(form); ElMessage.success('更新成功') }
        else { await addMenu(form); ElMessage.success('新增成功') }
        dialogVisible.value = false; loadData()
      } catch (error) { ElMessage.error(form.id ? '更新失败' : '新增失败') }
    }
  })
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该菜单吗?', '提示', { type: 'warning' })
    await deleteMenu(id); ElMessage.success('删除成功'); loadData()
  } catch (error) { if (error !== 'cancel') ElMessage.error('删除失败') }
}

onMounted(() => { loadData() })
</script>

<style scoped>
.menu-container { padding: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.query-form { margin-bottom: 20px; }
</style>