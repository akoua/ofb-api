package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.RegistrationRequest;
import com.istic.ofbapi.service.impl.AuthenticationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private static final String USER_ROLE_NOT_SET = "User role not set";

//    private final AuthenticationManager authenticationManager;
//
//    private final UserRepository userRepository;
//
//    private final RoleRepository roleRepository;
//
//    private final PasswordEncoder passwordEncoder;

//    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationServiceImpl authenticationService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> registratio(OAuth2AuthenticationToken token) {
        return ResponseEntity.ok(token.getPrincipal().getAttributes());
    }

    @PostMapping("v1/auth/registration")
    public ResponseEntity<String> registrationWithFirebase(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registration(request));
    }

    @GetMapping(path = "/test")
    public String test(Principal principal) {
        log.info("Principal: {}", principal);
//        JwtAuthenticationToken
        return principal.getName();
    }

    @GetMapping(path = "/test2")
    public String test2(JwtAuthenticationToken principal) {
        log.info("Principal: {}", principal);
        log.info("Principal: {}", principal.getTokenAttributes());
        return principal.getDetails().toString();
    }

//    @PostMapping("/v1/auth/sign-in")
//    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtTokenProvider.generateToken(authentication);
//        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//    }
//
//    @PostMapping("/v1/auth/sign-up")
//    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
//        if (Boolean.TRUE.equals(userRepository.existsByUsername(userRequest.getUsername()))) {
//            throw new OfbApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
//        }
//
//        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
//            throw new OfbApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
//        }
//
//        String firstName = userRequest.getFirstName().toLowerCase();
//
//        String lastName = userRequest.getLastName().toLowerCase();
//
//        String username = userRequest.getUsername().toLowerCase();
//
//        String email = userRequest.getEmail().toLowerCase();
//
//        String password = passwordEncoder.encode(userRequest.getPassword());
//
//        User user = new User(firstName, lastName, username, email, password);
//
//        List<Role> roles = new ArrayList<>();
//
//        if (userRepository.count() == 0) {
//            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
//            roleRepository.save(new Role(RoleName.ROLE_USER));
//            roles.add(roleRepository.findByName(RoleName.ROLE_USER)
//                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
//            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
//                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
//        } else {
//            roles.add(roleRepository.findByName(RoleName.ROLE_USER)
//                    .orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
//        }
//
//        user.setRoles(roles);
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
//                .buildAndExpand(result.getId()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
//    }
}
