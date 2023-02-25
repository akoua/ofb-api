package com.istic.ofbapi.security;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class GoogleFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            log.info("request: {}", request);
            String jwt = getJwtFromRequest(request);
            log.info("request jwt: {}", jwt);
//            new GoogleIdTokenVerifier(new IdTokenVerifier).verify(jwt);
//            FirebaseToken decodedToken = null;
//            decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt);
//            String uid = decodedToken.getUid();
//            log.info("request uid: {}", uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

      filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
