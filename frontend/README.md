# EcoCampus Frontend

Frontend React + Vite pour le backend EcoCampus.

Technologies utilisees :

- React 18
- React Router DOM
- Axios
- TailwindCSS
- React Hot Toast
- Context API pour l'authentification

## Execution

```bash
npm install
npm run dev
```

Ouvrir ensuite `http://localhost:5173`.

Le backend doit etre lance sur `http://localhost:8080`.

## Structure

- `src/api` : appels API Axios
- `src/components/common` : Navbar, ProtectedRoute, FilterBar, LoadingSpinner
- `src/components/plats` : PlatCard, PlatForm
- `src/components/reservations` : ReservationCard
- `src/contexts` : AuthContext
- `src/pages` : pages principales
