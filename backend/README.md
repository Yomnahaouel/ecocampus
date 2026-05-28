# EcoCampus Backend

Backend Spring Boot pour le projet EcoCampus.

## Contenu du cours utilise

- Injection de dependance avec constructeurs.
- Configuration via `application.properties`.
- REST API avec `@RestController`.
- Spring Data JPA avec entites et repositories.
- Validation avec `jakarta.validation`.
- Relations JPA : `OneToOne`, `OneToMany`, `ManyToOne`.
- JWT Authentication avec Spring Security.
- MapStruct pour convertir entites vers DTO.

## Execution dans IntelliJ Community

1. Ouvrir IntelliJ IDEA Community.
2. File > Open > choisir ce dossier `backend`.
3. Attendre l'import Maven.
4. Verifier que le JDK est Java 17 ou plus.
5. Ouvrir `src/main/java/com/ecocampus/EcoCampusApplication.java`.
6. Cliquer sur Run.

API : `http://localhost:8080`

Console H2 : `http://localhost:8080/h2-console`

- JDBC URL : `jdbc:h2:mem:ecocampus`
- User : `sa`
- Password : vide

## Endpoints utiles

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/plats`
- `GET /api/plats/disponibles`
- `GET /api/plats/{id}`
- `GET /api/plats/search?nom=...`
- `GET /api/plats/categorie/{id}`
- `GET /api/restaurants`
- `GET /api/categories`
- `GET /api/allergenes`
- `POST /api/reservations`
- `GET /api/reservations/user`
- `PUT /api/reservations/{id}/annuler`
- `PUT /api/reservations/{id}/confirmer`
- `GET /api/reservations`
- `GET /api/users`
