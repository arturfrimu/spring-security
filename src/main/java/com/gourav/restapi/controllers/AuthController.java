package com.gourav.restapi.controllers;

import com.gourav.restapi.config.jwt.JwtUtils;
import com.gourav.restapi.config.services.UserDetailsImpl;
import com.gourav.restapi.controllers.payload.request.LoginRequest;
import com.gourav.restapi.controllers.payload.request.SignupRequest;
import com.gourav.restapi.controllers.payload.response.JwtResponse;
import com.gourav.restapi.controllers.payload.response.MessageResponse;
import com.gourav.restapi.models.postgress.ERole;
import com.gourav.restapi.models.postgress.RoleEntity;
import com.gourav.restapi.models.postgress.UserEntity;
import com.gourav.restapi.repositories.postgress.JpaRolesRepository;
import com.gourav.restapi.repositories.postgress.JpaUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String ERROR_MESSAGE = "Error: Role is not found.";

    private final AuthenticationManager authenticationManager;
    private final JpaUsersRepository userRepository;
    private final JpaRolesRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRoles();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
            user.setRole(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
                        user.setRole(adminRole);
                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
                        user.setRole(modRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
                        user.setRole(userRole);
                }
            });
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logoutUser(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        String token = headerAuth.substring(7);
        jwtUtils.invalidateToken(token);
        return ResponseEntity.ok(new MessageResponse("logout successful"));
    }
}
