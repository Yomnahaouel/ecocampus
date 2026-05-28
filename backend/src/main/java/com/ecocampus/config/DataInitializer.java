package com.ecocampus.config;

import com.ecocampus.entity.Categorie;
import com.ecocampus.entity.Allergene;
import com.ecocampus.entity.Profile;
import com.ecocampus.entity.User;
import com.ecocampus.entity.enums.Role;
import com.ecocampus.repository.CategorieRepository;
import com.ecocampus.repository.AllergeneRepository;
import com.ecocampus.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
            UserRepository userRepository,
            CategorieRepository categorieRepository,
            AllergeneRepository allergeneRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            createUser(userRepository, passwordEncoder, "admin@ecocampus.com", "admin123", "Admin", "EcoCampus", Role.ADMIN);

            createCategorie(categorieRepository, "Sandwich", "Sandwichs et snacks rapides");
            createCategorie(categorieRepository, "Plat chaud", "Repas chauds du jour");
            createCategorie(categorieRepository, "Dessert", "Desserts et fruits");
            createCategorie(categorieRepository, "Boisson", "Boissons disponibles");

            createAllergene(allergeneRepository, "Gluten", "Ble, orge, seigle");
            createAllergene(allergeneRepository, "Lait", "Produits laitiers");
        };
    }

    private void createUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String email,
            String password,
            String nom,
            String prenom,
            Role role
    ) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setRole(role);

        Profile profile = new Profile();
        profile.setDepartement("Informatique");
        profile.setTelephone("22000000");
        profile.setAdresse("EcoCampus");
        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);
    }

    private Allergene createAllergene(AllergeneRepository allergeneRepository, String nom, String description) {
        Allergene allergene = new Allergene();
        allergene.setNom(nom);
        allergene.setDescription(description);
        return allergeneRepository.save(allergene);
    }

    private Categorie createCategorie(CategorieRepository categorieRepository, String nom, String description) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        categorie.setDescription(description);
        return categorieRepository.save(categorie);
    }
}
