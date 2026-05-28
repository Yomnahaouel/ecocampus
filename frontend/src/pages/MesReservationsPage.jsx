import { useEffect, useState } from 'react'
import toast from 'react-hot-toast'
import { getApiError } from '../api/axiosConfig'
import { reservationAPI } from '../api/reservationAPI'
import LoadingSpinner from '../components/common/LoadingSpinner'
import ReservationCard from '../components/reservations/ReservationCard'

export default function MesReservationsPage() {
  const [reservations, setReservations] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    load()
  }, [])

  async function load() {
    try {
      setReservations(await reservationAPI.mine())
    } catch (error) {
      toast.error(getApiError(error))
    } finally {
      setLoading(false)
    }
  }

  async function annuler(id) {
    try {
      await reservationAPI.annuler(id)
      toast.success('Reservation annulee')
      load()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  if (loading) return <LoadingSpinner />

  return (
    <main className="page">
      <h1 className="mb-5 text-3xl font-black">Mes reservations</h1>
      <div className="grid gap-4 md:grid-cols-2">
        {reservations.map((reservation) => <ReservationCard key={reservation.id} reservation={reservation} onCancel={annuler} />)}
      </div>
    </main>
  )
}
