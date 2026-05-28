import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { roleHome, useAuth } from '../contexts/AuthContext'

export default function LoginPage() {
  const [form, setForm] = useState({ email: '', password: '' })
  const [loading, setLoading] = useState(false)
  const { login } = useAuth()
  const navigate = useNavigate()

  async function submit(event) {
    event.preventDefault()
    setLoading(true)
    try {
      const data = await login(form)
      navigate(roleHome(data.role))
    } finally {
      setLoading(false)
    }
  }

  return (
    <main className="page">
      <section className="panel max-w-2xl">
        <h1 className="mb-5 text-3xl font-black">Connexion</h1>
        <form className="grid gap-4" onSubmit={submit}>
          <Field label="Email" value={form.email} onChange={(email) => setForm({ ...form, email })} />
          <Field label="Mot de passe" type="password" value={form.password} onChange={(password) => setForm({ ...form, password })} />
          <button className="btn-primary" disabled={loading}>{loading ? 'Connexion...' : 'Se connecter'}</button>
        </form>
      </section>
    </main>
  )
}

function Field({ label, value, onChange, type = 'text' }) {
  return (
    <label className="field">
      <span className="field-label">{label}</span>
      <input className="field-input" type={type} value={value} required onChange={(event) => onChange(event.target.value)} />
    </label>
  )
}
