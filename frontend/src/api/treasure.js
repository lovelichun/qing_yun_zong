import request from '@/utils/request'

export const getTreasureList = (params) => {
  return request({
    url: '/treasure/list',
    method: 'get',
    params
  })
}

export const getTreasureById = (id) => {
  return request({
    url: `/treasure/${id}`,
    method: 'get'
  })
}

export const addTreasure = (data) => {
  return request({
    url: '/treasure',
    method: 'post',
    data
  })
}

export const updateTreasure = (data) => {
  return request({
    url: '/treasure',
    method: 'put',
    data
  })
}

export const deleteTreasure = (id) => {
  return request({
    url: `/treasure/${id}`,
    method: 'delete'
  })
}

export const getTreasureDict = () => {
  return request({
    url: '/data-dict/all',
    method: 'get'
  })
}

export const getTreasureByCategory = (category) => {
  return request({
    url: `/treasure/category/${category}`,
    method: 'get'
  })
}
