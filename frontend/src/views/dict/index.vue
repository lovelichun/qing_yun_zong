<template>
  <div class="dict-container">
    <el-card title="数据字典管理" class="dict-card">
      <div class="dict-grid">
        <el-card title="宝物类别" class="dict-item">
          <el-tag v-for="(item, index) in dict.categories" :key="index" type="primary" class="dict-tag">
            {{ item }}
          </el-tag>
        </el-card>
        <el-card title="宝物级别" class="dict-item">
          <el-tag v-for="(item, index) in dict.levels" :key="index" type="success" class="dict-tag">
            {{ item }}
          </el-tag>
        </el-card>
        <el-card title="宝物品阶" class="dict-item">
          <el-tag v-for="(item, index) in dict.grades" :key="index" type="warning" class="dict-tag">
            {{ item }}
          </el-tag>
        </el-card>
        <el-card title="宝物名称" class="dict-item">
          <el-tag v-for="(item, index) in dict.names" :key="index" type="info" class="dict-tag">
            {{ item }}
          </el-tag>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getTreasureDict } from '@/api/treasure'

const dict = ref({
  categories: [],
  levels: [],
  grades: [],
  names: []
})

const loadDict = async () => {
  const res = await getTreasureDict()
  dict.value = res.data
}

onMounted(() => {
  loadDict()
})
</script>

<style scoped>
.dict-container {
  padding: 20px;
}

.dict-card {
  margin-bottom: 20px;
}

.dict-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.dict-item {
  min-height: 150px;
}

.dict-tag {
  margin: 5px;
}
</style>
