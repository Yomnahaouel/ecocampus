import { Link, NavLink, useNavigate } from 'react-router-dom'
import { roleLabel, useAuth } from '../../contexts/AuthContext'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const linkClass = ({ isActive }) =>
    `rounded-lg px-3 py-2 text-sm font-bold ${isActive ? 'bg-white text-eco-900' : 'text-white hover:bg-white/10'}`

  function handleLogout() {
    logout()
    navigate('/')
  }

  return (
    <header className="bg-eco-900 text-white">
      <div className="mx-auto flex max-w-7xl flex-col gap-4 px-4 py-4 sm:px-6 lg:flex-row lg:items-center lg:justify-between lg:px-8">
        <Link to="/" className="text-lg font-black">EcoCampus Food Rescue</Link>
        <nav className="flex flex-wrap items-center gap-2">
          {user?.role === 'ETUDIANT' && <NavLink className={linkClass} to="/plats">Plats</NavLink>}
          {user?.role === 'ETUDIANT' && <NavLink className={linkClass} to="/mes-reservations">Mes reservations</NavLink>}
          {user?.role === 'CAFETERIA_RESP' && <NavLink className={linkClass} to="/dashboard/cafeteria">Cafeteria</NavLink>}
          {user?.role === 'ADMIN' && <NavLink className={linkClass} to="/dashboard/admin">Admin</NavLink>}
          {user ? (
            <>
              <span className="rounded-lg bg-white/10 px-3 py-2 text-sm">{user.prenom} {user.nom} - {roleLabel(user.role)}</span>
              <button className="rounded-lg bg-white px-3 py-2 text-sm font-bold text-eco-900" onClick={handleLogout}>Deconnexion</button>
            </>
          ) : (
            <>
              <NavLink className={linkClass} to="/login">Connexion</NavLink>
              <NavLink className={linkClass} to="/register">Inscription</NavLink>
            </>
          )}
        </nav>
      </div>
    </header>
  )
}
