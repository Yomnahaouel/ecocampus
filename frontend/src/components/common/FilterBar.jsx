export default function FilterBar({ categories, allergenes, filters, setFilters }) {
  return (
    <div className="mb-5 grid gap-3 rounded-lg border border-slate-200 bg-white p-4 shadow-sm md:grid-cols-4">
      <input
        className="field-input"
        placeholder="Rechercher par nom"
        value={filters.search}
        onChange={(event) => setFilters({ ...filters, search: event.target.value })}
      />
      <select className="field-input" value={filters.categorieId} onChange={(event) => setFilters({ ...filters, categorieId: event.target.value })}>
        <option value="">Toutes categories</option>
        {categories.map((item) => <option key={item.id} value={item.id}>{item.nom}</option>)}
      </select>
      <input
        className="field-input"
        type="number"
        placeholder="Prix maximum"
        value={filters.prixMax}
        onChange={(event) => setFilters({ ...filters, prixMax: event.target.value })}
      />
      <select
        className="field-input"
        multiple
        value={filters.allergeneIds}
        onChange={(event) => setFilters({ ...filters, allergeneIds: Array.from(event.target.selectedOptions, (o) => o.value) })}
      >
        {allergenes.map((item) => <option key={item.id} value={item.id}>Sans {item.nom}</option>)}
      </select>
    </div>
  )
}
