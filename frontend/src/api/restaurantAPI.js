import apiClient from './axiosConfig'

export const restaurantAPI = {
  findAll: () => apiClient.get('/restaurants').then((res) => res.data),
  create: (payload) => apiClient.post('/restaurants', payload).then((res) => res.data),
  update: (id, payload) => apiClient.put(`/restaurants/${id}`, payload).then((res) => res.data),
  remove: (id) => apiClient.delete(`/restaurants/${id}`).then((res) => res.data),
}

