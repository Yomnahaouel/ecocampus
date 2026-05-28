import apiClient from './axiosConfig'

export const platAPI = {
  findAll: () => apiClient.get('/plats').then((res) => res.data),
  findDisponibles: () => apiClient.get('/plats/disponibles').then((res) => res.data),
  findById: (id) => apiClient.get(`/plats/${id}`).then((res) => res.data),
  search: (nom) => apiClient.get('/plats/search', { params: { nom } }).then((res) => res.data),
  byCategorie: (id) => apiClient.get(`/plats/categorie/${id}`).then((res) => res.data),
  create: (payload) => apiClient.post('/plats', payload).then((res) => res.data),
  update: (id, payload) => apiClient.put(`/plats/${id}`, payload).then((res) => res.data),
  remove: (id) => apiClient.delete(`/plats/${id}`).then((res) => res.data),
}

