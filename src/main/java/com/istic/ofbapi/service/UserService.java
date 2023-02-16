package com.istic.ofbapi.service;

import com.istic.ofbapi.payload.ApiResponse;
import com.istic.ofbapi.payload.UserIdentityAvailability;
import com.istic.ofbapi.payload.UserRequest;
import com.istic.ofbapi.payload.UserResponse;
import com.istic.ofbapi.security.UserPrincipal;

public interface UserService {

    UserIdentityAvailability checkUsernameAvailability(String username);

    UserIdentityAvailability checkEmailAvailability(String email);

    UserResponse readUser(Long id);

    UserResponse updateUser(UserRequest newUser, Long id, UserPrincipal currentUser);

    ApiResponse deleteUser(Long id, UserPrincipal currentUser);

}
