import { Route, Routes } from 'react-router-dom'
import { Toaster } from 'react-hot-toast'
import Navbar from './components/common/Navbar'
import ProtectedRoute from './components/common/ProtectedRoute'
import { AuthProvider } from './contexts/AuthContext'
import DashboardAdminPage from './pages/DashboardAdminPage'
import DashboardCafeteriaPage from './pages/DashboardCafeteriaPage'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import MesReservationsPage from './pages/MesReservationsPage'
import PlatDetailPage from './pages/PlatDetailPage'
import PlatsPage from './pages/PlatsPage'
import RegisterPage from './pages/RegisterPage'

export default function App() {
  return (
    <AuthProvider>
      <Navbar />
      <Toaster position="top-right" />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/plats" element={<ProtectedRoute roles={['ETUDIANT']}><PlatsPage /></ProtectedRoute>} />
        <Route path="/plats/:id" element={<ProtectedRoute roles={['ETUDIANT']}><PlatDetailPage /></ProtectedRoute>} />
        <Route path="/mes-reservations" element={<ProtectedRoute roles={['ETUDIANT']}><MesReservationsPage /></ProtectedRoute>} />
        <Route path="/dashboard/cafeteria" element={<ProtectedRoute roles={['CAFETERIA_RESP']}><DashboardCafeteriaPage /></ProtectedRoute>} />
        <Route path="/dashboard/admin" element={<ProtectedRoute roles={['ADMIN']}><DashboardAdminPage /></ProtectedRoute>} />
      </Routes>
    </AuthProvider>
  )
}
