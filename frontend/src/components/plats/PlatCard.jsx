import { Link } from 'react-router-dom'

export default function PlatCard({ plat, onReserve }) {
  return (
    <article className="overflow-hidden rounded-lg border border-slate-200 bg-white shadow-sm">
      <img className="h-44 w-full object-cover" src={plat.photoUrl || fallbackImage()} alt={plat.nom} />
      <div className="grid gap-2 p-4">
        <div className="flex items-start justify-between gap-3">
          <h3 className="text-lg font-black text-slate-900">
            <Link to={`/plats/${plat.id}`} className="hover:text-eco-600">{plat.nom}</Link>
          </h3>
          <strong className="text-eco-700">{Number(plat.prix).toFixed(3)} DT</strong>
        </div>
        <p className="line-clamp-2 text-sm text-slate-600">{plat.description}</p>
        <div className="flex flex-wrap gap-2 text-xs font-bold text-slate-500">
          <span>Restaurant : {plat.restaurantNom || 'Non indique'}</span>
          <span>Categorie : {plat.categorieNom || 'Non indiquee'}</span>
          <span>{plat.quantite} restant(s)</span>
        </div>
        {plat.allergenes?.length > 0 && <p className="text-xs text-amber-700">Allergenes : {plat.allergenes.join(', ')}</p>}
        <button className="btn-primary mt-2" onClick={() => onReserve(plat.id)}>Reserver</button>
      </div>
    </article>
  )
}

function fallbackImage() {
  return 'https://images.unsplash.com/photo-1498837167922-ddd27525d352?auto=format&fit=crop&w=900&q=80'
}
