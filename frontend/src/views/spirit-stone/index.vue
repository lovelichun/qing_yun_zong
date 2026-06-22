<template>
  <div class="spirit-stone-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>灵石管理</span>
          <el-button type="primary" @click="handleAdd">新增灵石</el-button>
        </div>
      </template>
      
      <el-form :inline="true" :model="queryForm" class="query-form">
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
        <el-form-item label="宝物类别">
          <el-select v-model="form.category" disabled>
            <el-option label="灵石" value="灵石" />
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
import { getTreasureList, addTreasure, updateTreasure, deleteTreasure, getTreasureDict } from '@/api/treasure'

const CATEGORY = '灵石'

const queryForm = reactive({ level: '', grade: '', name: '' })
const pagination = reactive({ pageNum: 1, pageSize: 10, total: 0 })
const tableData = ref([])
const dict = ref({ levels: [], grades: [] })
const dialogVisible = ref(false)
const dialogTitle = ref('新增灵石')
const formRef = ref(null)
const form = reactive({ id: null, category: CATEGORY, level: '', grade: '', name: '', quantity: 0, description: '' })
const rules = {
  level: [{ required: true, message: '请选择宝物级别', trigger: 'change' }],
  grade: [{ required: true, message: '请选择宝物品阶', trigger: 'change' }],
  name: [{ required: true, message: '请输入宝物名称', trigger: 'blur' }],
  quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const res = await getTreasureList({ pageNum: pagination.pageNum, pageSize: pagination.pageSize, category: CATEGORY, ...queryForm })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) { ElMessage.error('加载数据失败') }
}

const loadDict = async () => {
  try {
    const res = await getTreasureDict()
    dict.value = { levels: res.data.levels, grades: res.data.grades }
  } catch (error) { ElMessage.error('加载字典数据失败') }
}

const handleQuery = () => { pagination.pageNum = 1; loadData() }
const handleReset = () => { Object.assign(queryForm, { level: '', grade: '', name: '' }); handleQuery() }
const handleSizeChange = () => loadData()
const handleCurrentChange = () => loadData()
const handleAdd = () => { dialogTitle.value = '新增灵石'; Object.assign(form, { id: null, category: CATEGORY, level: '', grade: '', name: '', quantity: 0, description: '' }); dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑灵石'; Object.assign(form, row); dialogVisible.value = true }

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) { await updateTreasure(form); ElMessage.success('更新成功') }
        else { await addTreasure(form); ElMessage.success('新增成功') }
        dialogVisible.value = false; loadData()
      } catch (error) { ElMessage.error(form.id ? '更新失败' : '新增失败') }
    }
  })
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该灵石吗?', '提示', { type: 'warning' })
    await deleteTreasure(id); ElMessage.success('删除成功'); loadData()
  } catch (error) { if (error !== 'cancel') ElMessage.error('删除失败') }
}

onMounted(() => { loadData(); loadDict() })
</script>

<style scoped>
.spirit-stone-container { padding: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.query-form { margin-bottom: 20px; }
</style>