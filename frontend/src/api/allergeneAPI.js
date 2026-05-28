import apiClient from './axiosConfig'

export const allergeneAPI = {
  findAll: () => apiClient.get('/allergenes').then((res) => res.data),
  create: (payload) => apiClient.post('/allergenes', payload).then((res) => res.data),
  update: (id, payload) => apiClient.put(`/allergenes/${id}`, payload).then((res) => res.data),
  remove: (id) => apiClient.delete(`/allergenes/${id}`).then((res) => res.data),
}

