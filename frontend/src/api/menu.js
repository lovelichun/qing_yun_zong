import request from '@/utils/request'

export const getMenuList = (params) => {
  return request({
    url: '/menu/list',
    method: 'get',
    params
  })
}

export const getMenuById = (id) => {
  return request({
    url: `/menu/${id}`,
    method: 'get'
  })
}

export const addMenu = (data) => {
  return request({
    url: '/menu',
    method: 'post',
    data
  })
}

export const updateMenu = (data) => {
  return request({
    url: '/menu',
    method: 'put',
    data
  })
}

export const deleteMenu = (id) => {
  return request({
    url: `/menu/${id}`,
    method: 'delete'
  })
}

export const getAllMenus = () => {
  return request({
    url: '/menu/all',
    method: 'get'
  })
}
