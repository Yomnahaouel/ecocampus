import { Link } from 'react-router-dom'

export default function HomePage() {
  return (
    <main className="min-h-[calc(100vh-72px)] bg-[linear-gradient(90deg,rgba(6,78,59,.94),rgba(6,78,59,.60)),url('https://images.unsplash.com/photo-1498837167922-ddd27525d352?auto=format&fit=crop&w=1600&q=80')] bg-cover bg-center">
      <section className="mx-auto grid max-w-7xl px-4 py-20 text-white sm:px-6 lg:px-8">
        <div className="max-w-3xl">
          <p className="mb-3 text-sm font-black uppercase text-eco-100">Anti-gaspillage universitaire</p>
          <h1 className="text-4xl font-black leading-tight sm:text-6xl">Sauver les repas disponibles sur le campus.</h1>
          <p className="mt-5 max-w-2xl text-lg leading-8 text-eco-50">
            EcoCampus Food Rescue permet aux etudiants de reserver les plats restants, aux cafeterias
            de publier leurs quantites disponibles et aux administrateurs de suivre toute l'activite.
          </p>
          <div className="mt-8 flex flex-wrap gap-3">
            <Link className="btn-primary" to="/plats">Voir les plats</Link>
            <Link className="btn-secondary" to="/register">Creer un compte</Link>
          </div>
        </div>
      </section>
    </main>
  )
}

