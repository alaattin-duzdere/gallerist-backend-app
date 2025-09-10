package com.example.gallerist.dto;

import com.example.gallerist.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private List<Role> roles;
}
