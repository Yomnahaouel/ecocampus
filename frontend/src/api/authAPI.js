import apiClient from './axiosConfig'

export const authAPI = {
  register: (payload) => apiClient.post('/auth/register', payload).then((res) => res.data),
  login: (payload) => apiClient.post('/auth/login', payload).then((res) => res.data),
  me: () => apiClient.get('/auth/me').then((res) => res.data),
}

