package com.istic.ofbapi.service;

import com.istic.ofbapi.payload.LoginRequest;
import com.istic.ofbapi.payload.RegistrationRequest;

public interface AuthenticationService {

    String registration(RegistrationRequest request);

    String connexion(LoginRequest request);
}
