import request from '@/utils/request'

export const getOrderList = (params) => {
  return request({
    url: '/order/list',
    method: 'get',
    params
  })
}

export const getOrderById = (id) => {
  return request({
    url: `/order/${id}`,
    method: 'get'
  })
}

export const addOrder = (data) => {
  return request({
    url: '/order',
    method: 'post',
    data
  })
}

export const updateOrder = (data) => {
  return request({
    url: '/order',
    method: 'put',
    data
  })
}

export const deleteOrder = (id) => {
  return request({
    url: `/order/${id}`,
    method: 'delete'
  })
}
