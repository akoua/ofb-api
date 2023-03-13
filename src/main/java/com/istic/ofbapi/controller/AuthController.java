package com.istic.ofbapi.controller;

import com.istic.ofbapi.exception.AppException;
import com.istic.ofbapi.exception.OfbApiException;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.model.role.Role;
import com.istic.ofbapi.model.role.RoleName;
import com.istic.ofbapi.payload.ApiResponse;
import com.istic.ofbapi.payload.JwtAuthenticationResponse;
import com.istic.ofbapi.payload.LoginRequest;
import com.istic.ofbapi.payload.UserRequest;
import com.istic.ofbapi.repository.RoleRepository;
import com.istic.ofbapi.repository.UserRepository;
import com.istic.ofbapi.security.JwtTokenProvider;
import com.istic.ofbapi.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private static final String USER_ROLE_NOT_SET = "User role not set";

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/v1/auth/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map.Entry<String, User> jwtUser = jwtTokenProvider.generateToken(authentication).entrySet().iterator().next();

        log.info("User Id: " + ((UserPrincipal) authentication.getPrincipal()).getId());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtUser.getKey(), jwtUser.getValue()));
    }

    @PostMapping("/v1/auth/sign-up")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRequest.getUsername()))) {
            throw new OfbApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
            throw new OfbApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        String firstName = userRequest.getFirstName().toLowerCase();

        String lastName = userRequest.getLastName().toLowerCase();

        String username = userRequest.getUsername().toLowerCase();

        String email = userRequest.getEmail().toLowerCase();

        String password = passwordEncoder.encode(userRequest.getPassword());

        User user = new User(firstName, lastName, username, email, password);

        List<Role> roles = new ArrayList<>();

        if (userRepository.count() == 0) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_USER));
            roles.add(roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        } else {
            roles.add(roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
        }

        user.setRoles(roles);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
    }
}
