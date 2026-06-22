import request from '@/utils/request'

export const login = (data) => {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export const getCaptcha = () => {
  return request({
    url: '/captcha',
    method: 'get'
  })
}

export const logout = () => {
  return request({
    url: '/logout',
    method: 'post'
  })
}
