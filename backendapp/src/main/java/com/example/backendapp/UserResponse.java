package com.example.backendapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long userId;
    private String userName;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean activeStatus;
}
