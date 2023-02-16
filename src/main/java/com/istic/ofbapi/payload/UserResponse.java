package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
