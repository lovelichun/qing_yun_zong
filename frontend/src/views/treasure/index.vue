<template>
  <div class="treasure-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>宝物管理</span>
          <el-button type="primary" @click="handleAdd">新增宝物</el-button>
        </div>
      </template>
      
      <!-- 搜索表单 -->
      <el-form :inline="true" :model="queryForm" class="query-form">
        <el-form-item label="宝物类别">
          <el-select v-model="queryForm.category" placeholder="请选择" clearable>
            <el-option v-for="item in dict.categories" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="宝物级别">
          <el-select v-model="queryForm.level" placeholder="请选择" clearable>
            <el-option v-for="item in dict.levels" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="宝物品阶">
          <el-select v-model="queryForm.grade" placeholder="请选择" clearable>
            <el-option v-for="item in dict.grades" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="宝物名称">
          <el-input v-model="queryForm.name" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="category" label="宝物类别" width="100" />
        <el-table-column prop="level" label="宝物级别" width="100" />
        <el-table-column prop="grade" label="宝物品阶" width="100" />
        <el-table-column prop="name" label="宝物名称" width="150" />
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="description" label="说明" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
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

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="宝物类别" prop="category">
          <el-select v-model="form.category" placeholder="请选择">
            <el-option v-for="item in dict.categories" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="宝物级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择">
            <el-option v-for="item in dict.levels" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="宝物品阶" prop="grade">
          <el-select v-model="form.grade" placeholder="请选择">
            <el-option v-for="item in dict.grades" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="宝物名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入宝物名称" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input-number v-model="form.quantity" :min="0" />
        </el-form-item>
        <el-form-item label="说明" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入说明" />
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
import { 
  getTreasureList, 
  addTreasure, 
  updateTreasure, 
  deleteTreasure,
  getTreasureDict 
} from '@/api/treasure'

// 查询表单
const queryForm = reactive({
  category: '',
  level: '',
  grade: '',
  name: ''
})

// 分页
const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

// 表格数据
const tableData = ref([])

// 字典数据
const dict = ref({
  categories: [],
  levels: [],
  grades: [],
  names: []
})

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('新增宝物')
const formRef = ref(null)

// 表单
const form = reactive({
  id: null,
  category: '',
  level: '',
  grade: '',
  name: '',
  quantity: 0,
  description: ''
})

// 表单验证规则
const rules = {
  category: [{ required: true, message: '请选择宝物类别', trigger: 'change' }],
  level: [{ required: true, message: '请选择宝物级别', trigger: 'change' }],
  grade: [{ required: true, message: '请选择宝物品阶', trigger: 'change' }],
  name: [{ required: true, message: '请输入宝物名称', trigger: 'blur' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }]
}

// 加载表格数据
const loadData = async () => {
  try {
    const res = await getTreasureList({
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      ...queryForm
    })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

// 加载字典数据
const loadDict = async () => {
  try {
    const res = await getTreasureDict()
    dict.value = res.data
  } catch (error) {
    ElMessage.error('加载字典数据失败')
  }
}

// 查询
const handleQuery = () => {
  pagination.pageNum = 1
  loadData()
}

// 重置
const handleReset = () => {
  Object.assign(queryForm, {
    category: '',
    level: '',
    grade: '',
    name: ''
  })
  handleQuery()
}

// 分页大小变化
const handleSizeChange = () => {
  loadData()
}

// 页码变化
const handleCurrentChange = () => {
  loadData()
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增宝物'
  Object.assign(form, {
    id: null,
    category: '',
    level: '',
    grade: '',
    name: '',
    quantity: 0,
    description: ''
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑宝物'
  Object.assign(form, row)
  dialogVisible.value = true
}

// 提交表单
const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateTreasure(form)
          ElMessage.success('更新成功')
        } else {
          await addTreasure(form)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        loadData()
      } catch (error) {
        ElMessage.error(form.id ? '更新失败' : '新增失败')
      }
    }
  })
}

// 删除
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该宝物吗?', '提示', {
      type: 'warning'
    })
    await deleteTreasure(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

onMounted(() => {
  loadData()
  loadDict()
})
</script>

<style scoped>
.treasure-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.query-form {
  margin-bottom: 20px;
}
</style>