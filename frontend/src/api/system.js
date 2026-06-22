import request from '@/utils/request'

export const getUserList = (params) => {
  return request({
    url: '/system/user/list',
    method: 'get',
    params
  })
}

export const getUserById = (id) => {
  return request({
    url: `/system/user/${id}`,
    method: 'get'
  })
}

export const addUser = (data) => {
  return request({
    url: '/system/user',
    method: 'post',
    data
  })
}

export const updateUser = (data) => {
  return request({
    url: '/system/user',
    method: 'put',
    data
  })
}

export const deleteUser = (id) => {
  return request({
    url: `/system/user/${id}`,
    method: 'delete'
  })
}

export const getRoleList = (params) => {
  return request({
    url: '/system/role/list',
    method: 'get',
    params
  })
}

export const getRoleById = (id) => {
  return request({
    url: `/system/role/${id}`,
    method: 'get'
  })
}

export const addRole = (data) => {
  return request({
    url: '/system/role',
    method: 'post',
    data
  })
}

export const updateRole = (data) => {
  return request({
    url: '/system/role',
    method: 'put',
    data
  })
}

export const deleteRole = (id) => {
  return request({
    url: `/system/role/${id}`,
    method: 'delete'
  })
}

export const getPermissionList = (params) => {
  return request({
    url: '/system/permission/list',
    method: 'get',
    params
  })
}

export const getPermissionById = (id) => {
  return request({
    url: `/system/permission/${id}`,
    method: 'get'
  })
}

export const addPermission = (data) => {
  return request({
    url: '/system/permission',
    method: 'post',
    data
  })
}

export const updatePermission = (data) => {
  return request({
    url: '/system/permission',
    method: 'put',
    data
  })
}

export const deletePermission = (id) => {
  return request({
    url: `/system/permission/${id}`,
    method: 'delete'
  })
}
