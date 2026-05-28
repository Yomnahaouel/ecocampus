import { useEffect, useState } from 'react'
import toast from 'react-hot-toast'
import { allergeneAPI } from '../api/allergeneAPI'
import { categorieAPI } from '../api/categorieAPI'
import { getApiError } from '../api/axiosConfig'
import { restaurantAPI } from '../api/restaurantAPI'
import { userAPI } from '../api/userAPI'
import PlatForm from '../components/plats/PlatForm'
import ReservationCard from '../components/reservations/ReservationCard'
import { useDashboardData } from './DashboardCafeteriaPage'

export default function DashboardAdmin() {
  const data = useDashboardData()
  const [users, setUsers] = useState([])
  const [editingPlat, setEditingPlat] = useState(null)

  useEffect(() => { loadUsers() }, [])

  async function loadUsers() {
    try {
      setUsers(await userAPI.findAll())
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  async function deleteUser(id) {
    if (!window.confirm('Supprimer cet utilisateur ?')) return
    try {
      await userAPI.remove(id)
      toast.success('Utilisateur supprime')
      loadUsers()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  return (
    <main className="page">
      <h1 className="mb-5 text-3xl font-black">Dashboard admin</h1>
      <div className="mb-5 grid gap-4 sm:grid-cols-2 lg:grid-cols-5">
        <Stat label="Plats" value={data.plats.length} />
        <Stat label="Restaurants" value={data.restaurants.length} />
        <Stat label="Categories" value={data.categories.length} />
        <Stat label="Reservations" value={data.reservations.length} />
        <Stat label="Utilisateurs" value={users.length} />
      </div>
      <AdminCrud reload={data.load} users={users} deleteUser={deleteUser} />
      <section className="panel mb-5">
        <h2 className="mb-4 text-xl font-black">{editingPlat ? 'Modifier un plat' : 'Gestion des plats'}</h2>
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
        <div className="mt-5 grid gap-3">
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
        <h2 className="mb-4 text-xl font-black">Toutes les reservations</h2>
        <div className="grid gap-4 md:grid-cols-2">
          {data.reservations.map((reservation) => <ReservationCard key={reservation.id} reservation={reservation} onConfirm={data.confirmer} />)}
        </div>
      </section>
    </main>
  )
}

function AdminCrud({ reload, users, deleteUser }) {
  return (
    <div className="mb-5 grid gap-4 lg:grid-cols-3">
      <SmallCrud title="Categorie" fields={['nom', 'description']} api={categorieAPI} reload={reload} />
      <SmallCrud title="Allergene" fields={['nom', 'description']} api={allergeneAPI} reload={reload} />
      <SmallCrud title="Restaurant" fields={['nom', 'adresse', 'telephone']} api={restaurantAPI} reload={reload} />
      <section className="panel lg:col-span-3">
        <h2 className="mb-4 text-xl font-black">Utilisateurs</h2>
        <div className="grid gap-2 md:grid-cols-2">
          {users.map((user) => (
            <div className="rounded-lg border border-slate-200 p-3" key={user.id}>
              <strong>{user.prenom} {user.nom}</strong>
              <p className="text-sm text-slate-600">{user.email} - {user.role}</p>
              <button className="btn-secondary mt-2" onClick={() => deleteUser(user.id)}>Supprimer</button>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}

function SmallCrud({ title, fields, api, reload }) {
  const initial = Object.fromEntries(fields.map((field) => [field, '']))
  const [form, setForm] = useState(initial)
  const [items, setItems] = useState([])
  const [editing, setEditing] = useState(null)

  useEffect(() => {
    api.findAll().then(setItems).catch((error) => toast.error(getApiError(error)))
  }, [])

  async function loadItems() {
    try {
      setItems(await api.findAll())
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  async function submit(event) {
    event.preventDefault()
    try {
      if (editing) {
        await api.update(editing.id, form)
        toast.success(`${title} modifie`)
      } else {
        await api.create(form)
        toast.success(`${title} ajoute`)
      }
      setForm(initial)
      setEditing(null)
      loadItems()
      reload()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  async function remove(id) {
    if (!window.confirm(`Supprimer ${title} ?`)) return
    try {
      await api.remove(id)
      toast.success(`${title} supprime`)
      loadItems()
      reload()
    } catch (error) {
      toast.error(getApiError(error))
    }
  }

  function startEdit(item) {
    setEditing(item)
    setForm(Object.fromEntries(fields.map((field) => [field, item[field] || ''])))
  }

  return (
    <section className="panel">
      <h2 className="mb-4 text-xl font-black">{editing ? `Modifier ${title}` : `Ajouter ${title}`}</h2>
      <form className="grid gap-3" onSubmit={submit}>
        {fields.map((field) => (
          <label className="field" key={field}>
            <span className="field-label">{field}</span>
            <input className="field-input" value={form[field]} required={field === 'nom'} onChange={(event) => setForm({ ...form, [field]: event.target.value })} />
          </label>
        ))}
        <button className="btn-primary">{editing ? 'Modifier' : 'Ajouter'}</button>
        {editing && <button className="btn-secondary" type="button" onClick={() => { setEditing(null); setForm(initial) }}>Annuler</button>}
      </form>
      <div className="mt-4 grid gap-2">
        {items.map((item) => (
          <div className="rounded-lg border border-slate-200 p-3" key={item.id}>
            <strong>{item.nom}</strong>
            <p className="text-sm text-slate-600">{item.description || item.adresse || item.telephone}</p>
            <div className="mt-2 flex gap-2">
              <button className="btn-secondary" onClick={() => startEdit(item)}>Modifier</button>
              <button className="btn-secondary" onClick={() => remove(item.id)}>Supprimer</button>
            </div>
          </div>
        ))}
      </div>
    </section>
  )
}

function Stat({ label, value }) {
  return (
    <div className="rounded-lg border border-slate-200 bg-white p-4 shadow-sm">
      <strong className="text-3xl text-eco-700">{value}</strong>
      <p className="text-sm font-bold text-slate-500">{label}</p>
    </div>
  )
}
