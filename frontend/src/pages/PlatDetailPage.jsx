import { useEffect, useState } from 'react'
import toast from 'react-hot-toast'
import { useParams } from 'react-router-dom'
import { getApiError } from '../api/axiosConfig'
import { platAPI } from '../api/platAPI'
import { reservationAPI } from '../api/reservationAPI'
import LoadingSpinner from '../components/common/LoadingSpinner'
import { useAuth } from '../contexts/AuthContext'

export default function PlatDetailPage() {
  const { id } = useParams()
  const [plat, setPlat] = useState(null)
  const [loading, setLoading] = useState(true)
  const { user } = useAuth()

  useEffect(() => {
    platAPI.findById(id)
      .then(setPlat)
      .catch((error) => toast.error(getApiError(error)))
      .finally(() => setLoading(false))
  }, [id])

  async function reserve() {
    try {
      if (!user) {
        toast.error('Connecte-toi pour reserver')
        return
      }
      await reservationAPI.create({ platId: Number(id) })
      toast.success('Reservation reussie')
      setPlat(await platAPI.findById(id))
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  if (loading) return <LoadingSpinner />
  if (!plat) return null

  return (
    <main className="page">
      <article className="grid gap-6 rounded-lg border border-slate-200 bg-white p-5 shadow-sm lg:grid-cols-2">
        <img className="aspect-[4/3] w-full rounded-lg object-cover" src={plat.photoUrl || fallbackImage()} alt={plat.nom} />
        <div className="grid content-start gap-4">
          <h1 className="text-3xl font-black">{plat.nom}</h1>
          <p className="text-slate-600">{plat.description}</p>
          <strong className="text-2xl text-eco-700">{Number(plat.prix).toFixed(3)} DT</strong>
          <p>{plat.quantite} quantite(s) disponible(s)</p>
          <div className="grid gap-1 text-sm text-slate-600">
            <p><span className="font-bold text-slate-800">Restaurant :</span> {plat.restaurantNom || 'Non indique'}</p>
            <p><span className="font-bold text-slate-800">Categorie :</span> {plat.categorieNom || 'Non indiquee'}</p>
          </div>
          <p className="text-sm text-amber-700">Allergenes : {plat.allergenes.length ? plat.allergenes.join(', ') : 'Aucun'}</p>
          <button className="btn-primary w-fit" onClick={reserve}>Reserver</button>
        </div>
      </article>
    </main>
  )
}

function fallbackImage() {
  return 'https://images.unsplash.com/photo-1498837167922-ddd27525d352?auto=format&fit=crop&w=900&q=80'
}
