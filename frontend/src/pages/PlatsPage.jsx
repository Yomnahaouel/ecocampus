import { useEffect, useMemo, useState } from 'react'
import toast from 'react-hot-toast'
import { allergeneAPI } from '../api/allergeneAPI'
import { categorieAPI } from '../api/categorieAPI'
import { getApiError } from '../api/axiosConfig'
import { platAPI } from '../api/platAPI'
import { reservationAPI } from '../api/reservationAPI'
import FilterBar from '../components/common/FilterBar'
import LoadingSpinner from '../components/common/LoadingSpinner'
import PlatCard from '../components/plats/PlatCard'
import { useAuth } from '../contexts/AuthContext'

export default function PlatsPage() {
  const [plats, setPlats] = useState([])
  const [categories, setCategories] = useState([])
  const [allergenes, setAllergenes] = useState([])
  const [loading, setLoading] = useState(true)
  const [filters, setFilters] = useState({ search: '', categorieId: '', prixMax: '', allergeneIds: [] })
  const { user } = useAuth()

  useEffect(() => {
    load()
  }, [])

  async function load() {
    try {
      const [nextPlats, nextCategories, nextAllergenes] = await Promise.all([
        platAPI.findDisponibles(),
        categorieAPI.findAll(),
        allergeneAPI.findAll(),
      ])
      setPlats(nextPlats)
      setCategories(nextCategories)
      setAllergenes(nextAllergenes)
    } catch (error) {
      toast.error(getApiError(error))
    } finally {
      setLoading(false)
    }
  }

  const filtered = useMemo(() => plats.filter((plat) => {
    const matchSearch = plat.nom.toLowerCase().includes(filters.search.toLowerCase())
    const matchCategorie = !filters.categorieId || String(plat.categorieId) === filters.categorieId
    const matchPrix = !filters.prixMax || Number(plat.prix) <= Number(filters.prixMax)
    const matchAllergenes = filters.allergeneIds.every((id) => {
      const allergene = allergenes.find((item) => String(item.id) === id)
      return !allergene || !plat.allergenes.includes(allergene.nom)
    })
    return matchSearch && matchCategorie && matchPrix && matchAllergenes
  }), [plats, filters, allergenes])

  async function reserve(platId) {
    try {
      if (!user) {
        toast.error('Connecte-toi pour reserver')
        return
      }
      await reservationAPI.create({ platId })
      toast.success('Reservation reussie')
      load()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  if (loading) return <LoadingSpinner />

  return (
    <main className="page">
      <h1 className="mb-5 text-3xl font-black">Plats disponibles</h1>
      <FilterBar categories={categories} allergenes={allergenes} filters={filters} setFilters={setFilters} />
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {filtered.map((plat) => <PlatCard key={plat.id} plat={plat} onReserve={reserve} />)}
      </div>
    </main>
  )
}

