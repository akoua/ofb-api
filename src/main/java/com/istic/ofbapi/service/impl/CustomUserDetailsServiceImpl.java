//package com.istic.ofbapi.service.impl;
//
//import com.istic.ofbapi.model.User;
//import com.istic.ofbapi.repository.UserRepository;
//import com.istic.ofbapi.security.UserPrincipal;
//import com.istic.ofbapi.service.CustomUserDetailsService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@AllArgsConstructor
//public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String usernameOrEmail) {
//        User user = userRepository.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
//        return UserPrincipal.create(user);
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));
//        return UserPrincipal.create(user);
//    }
//}
