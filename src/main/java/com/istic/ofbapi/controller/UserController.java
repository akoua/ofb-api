package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.ApiResponse;
import com.istic.ofbapi.payload.UserIdentityAvailability;
import com.istic.ofbapi.payload.UserRequest;
import com.istic.ofbapi.payload.UserResponse;
import com.istic.ofbapi.security.CurrentUser;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/v1/users/checkUsernameAvailability")
    public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(@RequestParam(value = "username") String username) {
        UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/v1/users//checkEmailAvailability")
    public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(@RequestParam(value = "email") String email) {
        UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);
        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/v1/users//{id}")
    public ResponseEntity<UserResponse> readUser(@PathVariable(value = "id") Long id) {
        UserResponse user = userService.readUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/v1/users//{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest newUser,
                                                   @PathVariable(value = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        UserResponse updatedUser = userService.updateUser(newUser, id, currentUser);

        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/v1/users//{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "id") Long id,
                                                  @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = userService.deleteUser(id, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
