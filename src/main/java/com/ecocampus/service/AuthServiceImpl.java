package com.ecocampus.service;

import com.ecocampus.dto.request.LoginRequest;
import com.ecocampus.dto.request.RegisterRequest;
import com.ecocampus.dto.response.JwtResponse;
import com.ecocampus.dto.response.MessageResponse;
import com.ecocampus.dto.response.UserResponse;
import com.ecocampus.entity.User;
import com.ecocampus.entity.Profile;
import com.ecocampus.entity.enums.Role;
import com.ecocampus.mapper.UserMapper;
import com.ecocampus.repository.UserRepository;
import com.ecocampus.repository.ProfileRepository;
import com.ecocampus.config.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        return new JwtResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                user.getNom(),
                user.getPrenom(),
                user.getRole().name()
        );
    }

    @Override
    @Transactional
    public MessageResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new MessageResponse("Erreur: Cet email est déjà utilisé!");
        }

        // Création de l'utilisateur
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ETUDIANT); // Par défaut, rôle étudiant

        userRepository.save(user);

        // Création du profil
        Profile profile = new Profile();
        profile.setTelephone(request.getTelephone());
        profile.setDepartement(request.getDepartement());
        profile.setUser(user);

        profileRepository.save(profile);

        return new MessageResponse("Utilisateur inscrit avec succès!");
    }

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        UserResponse response = userMapper.toResponse(user);

        // TEMPORAIRE : On commente ces lignes jusqu'à ce que Profile fonctionne
    /*
    if (user.getProfile() != null) {
        response.setTelephone(user.getProfile().getTelephone());
        response.setDepartement(user.getProfile().getDepartement());
    }
    */

        return response;

    }
}