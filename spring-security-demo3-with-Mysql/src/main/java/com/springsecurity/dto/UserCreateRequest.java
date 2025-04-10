package com.springsecurity.dto;

import com.springsecurity.entity.ERoles;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Username Required")
    private String username;

    @NotBlank(message = "Password Required")
    private String password;

    @NotBlank(message = "Email Required")
    private String email;

    @NotBlank(message = "Role Required")
    private String role;

}
