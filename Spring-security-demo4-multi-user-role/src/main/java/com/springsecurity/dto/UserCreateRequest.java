package com.springsecurity.dto;

import com.springsecurity.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateRequest {

    @NotBlank(message = "First Name Required.")
    private String firstname;

    @NotBlank(message = "Last Name Required.")
    private String lastname;

    @NotBlank(message = "Email Required.")
    private String email;

    @NotBlank(message = "Username Required.")
    private String username;

    @NotBlank(message = "Password Required.")
    private String password;

    @NotEmpty
    private Set<@Valid RoleDto> roles = new HashSet<>();
}
