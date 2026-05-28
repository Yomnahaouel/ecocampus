import apiClient from './axiosConfig'

export const reservationAPI = {
  create: (payload) => apiClient.post('/reservations', payload).then((res) => res.data),
  mine: () => apiClient.get('/reservations/user').then((res) => res.data),
  all: () => apiClient.get('/reservations').then((res) => res.data),
  byRestaurant: (id) => apiClient.get(`/reservations/restaurant/${id}`).then((res) => res.data),
  annuler: (id) => apiClient.put(`/reservations/${id}/annuler`).then((res) => res.data),
  confirmer: (id) => apiClient.put(`/reservations/${id}/confirmer`).then((res) => res.data),
}

