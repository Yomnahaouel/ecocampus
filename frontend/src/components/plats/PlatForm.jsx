import { useEffect, useState } from 'react'

const emptyPlat = { nom: '', description: '', prix: '', quantite: '', photoUrl: '', categorieId: '', restaurantId: '', allergeneIds: [] }

export default function PlatForm({ categories, restaurants, allergenes, onSubmit, initialValue, submitLabel = 'Enregistrer le plat' }) {
  const [form, setForm] = useState(emptyPlat)

  useEffect(() => {
    if (!initialValue) {
      setForm(emptyPlat)
      return
    }
    setForm({
      nom: initialValue.nom || '',
      description: initialValue.description || '',
      prix: initialValue.prix ?? '',
      quantite: initialValue.quantite ?? '',
      photoUrl: initialValue.photoUrl || '',
      categorieId: initialValue.categorieId ? String(initialValue.categorieId) : '',
      restaurantId: initialValue.restaurantId ? String(initialValue.restaurantId) : '',
      allergeneIds: allergenes
        .filter((allergene) => initialValue.allergenes?.includes(allergene.nom))
        .map((allergene) => String(allergene.id)),
    })
  }, [initialValue, allergenes])

  function submit(event) {
    event.preventDefault()
    onSubmit({
      ...form,
      prix: Number(form.prix),
      quantite: Number(form.quantite),
      categorieId: Number(form.categorieId),
      restaurantId: Number(form.restaurantId),
      allergeneIds: form.allergeneIds.map(Number),
      disponible: Number(form.quantite) > 0,
    })
    if (!initialValue) {
      setForm(emptyPlat)
    }
  }

  return (
    <form className="grid gap-3 md:grid-cols-2" onSubmit={submit}>
      <Field label="Nom" value={form.nom} onChange={(nom) => setForm({ ...form, nom })} />
      <Field label="Prix" type="number" value={form.prix} onChange={(prix) => setForm({ ...form, prix })} />
      <Field label="Quantite" type="number" value={form.quantite} onChange={(quantite) => setForm({ ...form, quantite })} />
      <Field label="Photo URL" required={false} value={form.photoUrl} onChange={(photoUrl) => setForm({ ...form, photoUrl })} />
      <Field label="Description" required={false} value={form.description} onChange={(description) => setForm({ ...form, description })} />
      <Select label="Categorie" value={form.categorieId} items={categories} onChange={(categorieId) => setForm({ ...form, categorieId })} />
      <Select label="Restaurant" value={form.restaurantId} items={restaurants} onChange={(restaurantId) => setForm({ ...form, restaurantId })} />
      <label className="field">
        <span className="field-label">Allergenes</span>
        <select
          className="field-input min-h-28"
          multiple
          value={form.allergeneIds}
          onChange={(event) => setForm({ ...form, allergeneIds: Array.from(event.target.selectedOptions, (o) => o.value) })}
        >
          {allergenes.map((item) => <option key={item.id} value={item.id}>{item.nom}</option>)}
        </select>
      </label>
      <button className="btn-primary md:col-span-2">{submitLabel}</button>
    </form>
  )
}

function Field({ label, value, onChange, type = 'text', required = true }) {
  return (
    <label className="field">
      <span className="field-label">{label}</span>
      <input className="field-input" type={type} step={type === 'number' ? '0.001' : undefined} value={value} required={required} onChange={(event) => onChange(event.target.value)} />
    </label>
  )
}

function Select({ label, value, items, onChange }) {
  return (
    <label className="field">
      <span className="field-label">{label}</span>
      <select className="field-input" value={value} required onChange={(event) => onChange(event.target.value)}>
        <option value="">Choisir</option>
        {items.map((item) => <option key={item.id} value={item.id}>{item.nom}</option>)}
      </select>
    </label>
  )
}
