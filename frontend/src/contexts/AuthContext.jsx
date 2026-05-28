import { createContext, useContext, useEffect, useMemo, useState } from 'react'
import toast from 'react-hot-toast'
import { authAPI } from '../api/authAPI'
import { getApiError } from '../api/axiosConfig'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => readUser())
  const [token, setToken] = useState(() => localStorage.getItem('ecocampus_token') || '')
  const [loadingAuth, setLoadingAuth] = useState(Boolean(localStorage.getItem('ecocampus_token')))

  useEffect(() => {
    function handleLogout() {
      setUser(null)
      setToken('')
    }
    window.addEventListener('auth:logout', handleLogout)
    return () => window.removeEventListener('auth:logout', handleLogout)
  }, [])

  useEffect(() => {
    if (!token) {
      setLoadingAuth(false)
      return
    }
    authAPI.me()
      .then((data) => {
        setUser(data)
        localStorage.setItem('ecocampus_user', JSON.stringify(data))
      })
      .catch(() => logout())
      .finally(() => setLoadingAuth(false))
  }, [])

  async function login(credentials) {
    try {
      const data = await authAPI.login(credentials)
      localStorage.setItem('ecocampus_token', data.token)
      localStorage.setItem('ecocampus_user', JSON.stringify(data))
      setToken(data.token)
      setUser(data)
      toast.success(`Connecte comme ${roleLabel(data.role)}`)
      return data
    } catch (error) {
      toast.error(getApiError(error))
      throw error
    }
  }

  async function register(payload) {
    try {
      await authAPI.register(payload)
      toast.success('Inscription reussie')
    } catch (error) {
      toast.error(getApiError(error))
      throw error
    }
  }

  function logout() {
    localStorage.removeItem('ecocampus_token')
    localStorage.removeItem('ecocampus_user')
    setToken('')
    setUser(null)
    toast.success('Deconnexion reussie')
  }

  const value = useMemo(() => ({ user, token, loadingAuth, login, register, logout }), [user, token, loadingAuth])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  return useContext(AuthContext)
}

export function roleLabel(role) {
  if (role === 'ADMIN') return 'Administrateur'
  if (role === 'CAFETERIA_RESP') return 'Responsable cafeteria'
  return 'Etudiant'
}

export function roleHome(role) {
  if (role === 'ADMIN') return '/dashboard/admin'
  if (role === 'CAFETERIA_RESP') return '/dashboard/cafeteria'
  return '/plats'
}

function readUser() {
  try {
    return JSON.parse(localStorage.getItem('ecocampus_user'))
  } catch {
    return null
  }
}
