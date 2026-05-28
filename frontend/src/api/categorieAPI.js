import apiClient from './axiosConfig'

export const categorieAPI = {
  findAll: () => apiClient.get('/categories').then((res) => res.data),
  create: (payload) => apiClient.post('/categories', payload).then((res) => res.data),
  update: (id, payload) => apiClient.put(`/categories/${id}`, payload).then((res) => res.data),
  remove: (id) => apiClient.delete(`/categories/${id}`).then((res) => res.data),
}

