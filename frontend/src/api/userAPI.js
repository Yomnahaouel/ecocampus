import apiClient from './axiosConfig'

export const userAPI = {
  findAll: () => apiClient.get('/users').then((res) => res.data),
  remove: (id) => apiClient.delete(`/users/${id}`).then((res) => res.data),
}
