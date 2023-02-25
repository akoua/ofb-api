package com.istic.ofbapi.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.istic.ofbapi.payload.LoginRequest;
import com.istic.ofbapi.payload.RegistrationRequest;
import com.istic.ofbapi.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private FirebaseAuth firebaseAuth;

    @Override
    public String registration(RegistrationRequest request) {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
        try {
            UserRecord userRecord = firebaseAuth.createUser(createRequest);
            userRecord.getCustomClaims();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String connexion(LoginRequest request) {
        return null;
    }
}
