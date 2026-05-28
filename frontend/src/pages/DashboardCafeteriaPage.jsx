import { useEffect, useState } from 'react'
import toast from 'react-hot-toast'
import { allergeneAPI } from '../api/allergeneAPI'
import { categorieAPI } from '../api/categorieAPI'
import { getApiError } from '../api/axiosConfig'
import { platAPI } from '../api/platAPI'
import { reservationAPI } from '../api/reservationAPI'
import { restaurantAPI } from '../api/restaurantAPI'
import LoadingSpinner from '../components/common/LoadingSpinner'
import PlatForm from '../components/plats/PlatForm'
import ReservationCard from '../components/reservations/ReservationCard'

export default function DashboardCafeteria() {
  const data = useDashboardData()
  const [editingPlat, setEditingPlat] = useState(null)

  if (data.loading) return <LoadingSpinner />

  return (
    <main className="page">
      <h1 className="mb-5 text-3xl font-black">Dashboard cafeteria</h1>
      <section className="panel mb-5">
        <h2 className="mb-4 text-xl font-black">{editingPlat ? 'Modifier un plat' : 'Ajouter un plat'}</h2>
        <PlatForm
          categories={data.categories}
          restaurants={data.restaurants}
          allergenes={data.allergenes}
          initialValue={editingPlat}
          submitLabel={editingPlat ? 'Modifier le plat' : 'Ajouter le plat'}
          onSubmit={async (payload) => {
            if (editingPlat) {
              await data.updatePlat(editingPlat.id, payload)
              setEditingPlat(null)
            } else {
              await data.createPlat(payload)
            }
          }}
        />
        {editingPlat && <button className="btn-secondary mt-3" onClick={() => setEditingPlat(null)}>Annuler la modification</button>}
      </section>
      <section className="panel mb-5">
        <h2 className="mb-4 text-xl font-black">Mes plats</h2>
        <div className="grid gap-3">
          {data.plats.map((plat) => (
            <div className="flex flex-col gap-2 rounded-lg border border-slate-200 p-3 sm:flex-row sm:items-center sm:justify-between" key={plat.id}>
              <div>
                <strong>{plat.nom}</strong>
                <p className="text-sm text-slate-600">{plat.quantite} restant(s) - {Number(plat.prix).toFixed(3)} DT</p>
              </div>
              <div className="flex gap-2">
                <button className="btn-secondary" onClick={() => setEditingPlat(plat)}>Modifier</button>
                <button className="btn-secondary" onClick={() => data.deletePlat(plat.id)}>Supprimer</button>
              </div>
            </div>
          ))}
        </div>
      </section>
      <section className="panel">
        <h2 className="mb-4 text-xl font-black">Reservations recues</h2>
        <div className="grid gap-4 md:grid-cols-2">
          {data.reservations.map((reservation) => <ReservationCard key={reservation.id} reservation={reservation} onConfirm={data.confirmer} />)}
        </div>
      </section>
    </main>
  )
}

export function useDashboardData() {
  const [state, setState] = useState({ plats: [], restaurants: [], categories: [], allergenes: [], reservations: [], loading: true })

  useEffect(() => {
    load()
  }, [])

  async function load() {
    try {
      const [plats, restaurants, categories, allergenes, reservations] = await Promise.all([
        platAPI.findAll(),
        restaurantAPI.findAll(),
        categorieAPI.findAll(),
        allergeneAPI.findAll(),
        reservationAPI.all(),
      ])
      setState({ plats, restaurants, categories, allergenes, reservations, loading: false })
    } catch (error) {
      toast.error(getApiError(error))
      setState((current) => ({ ...current, loading: false }))
    }
  }

  async function createPlat(payload) {
    try {
      await platAPI.create(payload)
      toast.success('Plat ajoute')
      load()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  async function updatePlat(id, payload) {
    try {
      await platAPI.update(id, payload)
      toast.success('Plat modifie')
      load()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  async function deletePlat(id) {
    if (!window.confirm('Supprimer ce plat ?')) return
    try {
      await platAPI.remove(id)
      toast.success('Plat supprime')
      load()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  async function confirmer(id) {
    try {
      await reservationAPI.confirmer(id)
      toast.success('Reservation confirmee')
      load()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  return { ...state, load, createPlat, updatePlat, deletePlat, confirmer }
}
