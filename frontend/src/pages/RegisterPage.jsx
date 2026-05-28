import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'

const initialForm = {
  email: '',
  password: '',
  nom: '',
  prenom: '',
  telephone: '',
  departement: '',
  restaurantNom: '',
  role: 'ETUDIANT',
}

export default function RegisterPage() {
  const [form, setForm] = useState(initialForm)
  const [loading, setLoading] = useState(false)
  const { register } = useAuth()
  const navigate = useNavigate()

  async function submit(event) {
    event.preventDefault()
    setLoading(true)
    try {
      const payload = { ...form }
      if (payload.role !== 'CAFETERIA_RESP') {
        delete payload.restaurantNom
      }
      await register(payload)
      navigate('/login')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main className="page">
      <section className="panel">
        <h1 className="mb-5 text-3xl font-black">Inscription</h1>
        <form className="grid gap-4 md:grid-cols-2" onSubmit={submit}>
          <label className="field md:col-span-2">
            <span className="field-label">Profil</span>
            <select className="field-input" value={form.role} onChange={(event) => setForm({ ...form, role: event.target.value })}>
              <option value="ETUDIANT">Etudiant</option>
              <option value="CAFETERIA_RESP">Responsable de cafeteria</option>
            </select>
          </label>
          {Object.keys(initialForm).map((key) => {
            if (key === 'role') return null
            if (key === 'restaurantNom' && form.role !== 'CAFETERIA_RESP') return null
            return (
            <label className="field" key={key}>
              <span className="field-label">{labelFor(key)}</span>
              <input
                className="field-input"
                type={typeFor(key)}
                minLength={key === 'password' ? 6 : undefined}
                value={form[key]}
                required={!['telephone', 'departement'].includes(key)}
                onChange={(event) => setForm({ ...form, [key]: event.target.value })}
              />
            </label>
            )
          })}
          <button className="btn-primary md:col-span-2" disabled={loading}>{loading ? 'Creation...' : 'Creer mon compte'}</button>
        </form>
      </section>
    </main>
  )
}

function labelFor(key) {
  return {
    email: 'Email',
    password: 'Mot de passe',
    nom: 'Nom',
    prenom: 'Prenom',
    telephone: 'Telephone',
    departement: 'Departement',
    restaurantNom: 'Nom du restaurant',
    role: 'Profil',
  }[key]
}

function typeFor(key) {
  if (key === 'password') return 'password'
  if (key === 'email') return 'email'
  if (key === 'telephone') return 'tel'
  return 'text'
}
