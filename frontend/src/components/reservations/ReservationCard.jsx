export default function ReservationCard({ reservation, onCancel, onConfirm }) {
  return (
    <article className="rounded-lg border border-slate-200 bg-white p-4 shadow-sm">
      <div className="flex flex-col gap-3 sm:flex-row sm:items-start sm:justify-between">
        <div>
          <h3 className="font-black">{reservation.platNom}</h3>
          <p className="text-sm text-slate-600">{reservation.restaurantNom}</p>
          <p className="text-sm font-bold text-eco-700">{Number(reservation.platPrix || 0).toFixed(3)} DT</p>
          <p className="text-xs text-slate-500">{formatDate(reservation.dateReservation)}</p>
          <p className="text-xs text-slate-500">{reservation.userEmail}</p>
        </div>
        <span className={statusClass(reservation.status)}>{reservation.status}</span>
      </div>
      <div className="mt-4 flex gap-2">
        {reservation.status === 'EN_ATTENTE' && onCancel && <button className="btn-secondary" onClick={() => onCancel(reservation.id)}>Annuler</button>}
        {reservation.status === 'EN_ATTENTE' && onConfirm && <button className="btn-primary" onClick={() => onConfirm(reservation.id)}>Confirmer</button>}
      </div>
    </article>
  )
}

function formatDate(value) {
  if (!value) return ''
  return new Intl.DateTimeFormat('fr-FR', {
    dateStyle: 'short',
    timeStyle: 'short',
  }).format(new Date(value))
}

function statusClass(status) {
  if (status === 'CONFIRMEE') return 'rounded-full bg-eco-100 px-3 py-1 text-xs font-black text-eco-700'
  if (status === 'ANNULEE') return 'rounded-full bg-red-100 px-3 py-1 text-xs font-black text-red-700'
  return 'rounded-full bg-amber-100 px-3 py-1 text-xs font-black text-amber-700'
}
