import request from './index'

export const getDictsByType = (dictType) => {
  return request.get(`/data-dict/type/${dictType}`)
}

export const getDictLabels = (dictType) => {
  return request.get(`/data-dict/labels/${dictType}`)
}

export const getAllDicts = () => {
  return request.get('/data-dict/all')
}

export const listDicts = (params) => {
  return request.get('/data-dict/list', { params })
}

export const getDictById = (id) => {
  return request.get(`/data-dict/${id}`)
}

export const saveDict = (data) => {
  return request.post('/data-dict', data)
}

export const updateDict = (data) => {
  return request.put('/data-dict', data)
}

export const deleteDict = (id) => {
  return request.delete(`/data-dict/${id}`)
}
