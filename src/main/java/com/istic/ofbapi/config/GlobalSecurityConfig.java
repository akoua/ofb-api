package com.istic.ofbapi.config;

import com.istic.ofbapi.security.FirebaseFilter;
import com.istic.ofbapi.security.GoogleFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;

@Configuration
@RequiredArgsConstructor
@AllArgsConstructor
public class GlobalSecurityConfig extends WebSecurityConfigurerAdapter {

    //This works only for firebae username:password authentication
    JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(

//            "https://www.googleapis.com/oauth2/v3/certs",
//            "https://oauth2.googleapis.com/token",
//            "https://accounts.google.com/",
            "https://securetoken.google.com/ofbmobÒileapp"
    );
    @Autowired
    private FirebaseFilter firebaseFilter;
    @Autowired
    private GoogleFilter googleFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.POST, "/", "/api/v1/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api", "/api/v1/users/checkUsernameAvailability", "/api/v1/users/checkEmailAvailability").permitAll()
                .anyRequest().authenticated();
//        http.oauth2ResourceServer().jwt();

//        http.oauth2ResourceServer(oauth2 -> oauth2.authenticationManagerResolver(authenticationManagerResolver));
//        http.addFilterBefore(googleFilter, UsernamePasswordAuthenticationFilter.class);

    }

//    @Bean
//    JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
//                JwtDecoders.fromOidcIssuerLocation("https://accounts.google.com");
//
//        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator("209770400012-tt0ht4fijj3rbe64buhip9u6hldlqcmt.apps.googleusercontent.com");
//        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer("accounts.google.com");
//        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>
//                (withIssuer, audienceValidator);
//
//        jwtDecoder.setJwtValidator(withAudience);
//
//        return jwtDecoder;
//    }


}
