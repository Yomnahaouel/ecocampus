# EcoCampus - Projet complet

Ce dossier contient une version propre et executable du projet EcoCampus.

- `backend` : Spring Boot REST API avec JPA, Validation, Security JWT, MapStruct et H2.
- `frontend` : React + Vite + TailwindCSS + Axios qui consomme l'API.

## Fonctionnalites principales

- Accueil public avec presentation anti-gaspillage.
- Connexion avec trois roles : etudiant, responsable cafeteria et admin.
- Inscription avec choix du profil : etudiant ou responsable cafeteria.
- Le compte admin n'est pas accessible via l'inscription classique ; il est initialise par defaut.
- Plats disponibles avec recherche, filtre categorie, filtre prix et allergenes a eviter.
- Detail d'un plat.
- Reservation et annulation d'une reservation en attente.
- Dashboard cafeteria : ajout/suppression de plats, suivi et confirmation des reservations.
- Dashboard admin : gestion categories, allergenes, restaurants, plats, reservations et utilisateurs.

## Demarrage rapide

1. Ouvrir `backend` dans IntelliJ IDEA Community.
2. Lancer `EcoCampusApplication`.
3. Ouvrir un terminal dans `frontend`.
4. Installer et lancer :

```bash
npm install
npm run dev
```

5. Ouvrir l'adresse affichee par Vite, normalement `http://localhost:5173`.

## Compte par defaut

- Admin : `admin@ecocampus.com` / `admin123`

Les comptes etudiant et responsable cafeteria doivent etre crees depuis l'ecran d'inscription. Pour un responsable cafeteria, le formulaire demande aussi le nom du restaurant.
Il n'y a pas de restaurant ni de plat d'exemple par defaut ; ils doivent etre ajoutes par l'admin ou les responsables cafeteria.
