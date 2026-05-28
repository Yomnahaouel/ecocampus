package com.ecocampus.service;

import com.ecocampus.dto.request.LoginRequest;
import com.ecocampus.dto.request.RegisterRequest;
import com.ecocampus.dto.response.JwtResponse;
import com.ecocampus.dto.response.MessageResponse;
import com.ecocampus.dto.response.UserResponse;
import com.ecocampus.entity.Profile;
import com.ecocampus.entity.Restaurant;
import com.ecocampus.entity.User;
import com.ecocampus.entity.enums.Role;
import com.ecocampus.mapper.UserMapper;
import com.ecocampus.repository.RestaurantRepository;
import com.ecocampus.repository.UserRepository;
import com.ecocampus.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        String email = normalizeEmail(request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(principal);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable."));

        return new JwtResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getNom(),
                user.getPrenom(),
                user.getRole().name()
        );
    }

    @Override
    @Transactional
    public MessageResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email est deja utilise.");
        }

        User user = userMapper.toEntity(request);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(resolveRegisterRole(request.getRole()));

        Profile profile = new Profile();
        profile.setTelephone(request.getTelephone());
        profile.setDepartement(request.getDepartement());
        profile.setAdresse(request.getAdresse());
        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);
        createRestaurantForCafeteria(request, user.getRole());
        return new MessageResponse("Inscription reussie.");
    }

    @Override
    public UserResponse currentUser(String email) {
        User user = userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable."));
        return userMapper.toResponse(user);
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }

    private Role resolveRegisterRole(Role requestedRole) {
        if (requestedRole == null || requestedRole == Role.ETUDIANT) {
            return Role.ETUDIANT;
        }
        if (requestedRole == Role.CAFETERIA_RESP) {
            return Role.CAFETERIA_RESP;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'inscription administrateur est interdite.");
    }

    private void createRestaurantForCafeteria(RegisterRequest request, Role role) {
        if (role != Role.CAFETERIA_RESP) {
            return;
        }

        String restaurantNom = request.getRestaurantNom() == null ? "" : request.getRestaurantNom().trim();
        if (restaurantNom.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom du restaurant est obligatoire.");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setNom(restaurantNom);
        restaurant.setAdresse(request.getAdresse());
        restaurant.setTelephone(request.getTelephone());
        restaurantRepository.save(restaurant);
    }
}
