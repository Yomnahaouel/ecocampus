package com.ecocampus.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    @JsonAlias({"motDePasse", "motdepasse"})
    private String password;

    @NotBlank(message = "Le nom est obligatoire")
    @JsonAlias({"name", "lastName", "lastname"})
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @JsonAlias({"firstName", "firstname"})
    private String prenom;

    @JsonAlias({"phone", "tel"})
    private String telephone;

    @JsonAlias({"department"})
    private String departement;
}
