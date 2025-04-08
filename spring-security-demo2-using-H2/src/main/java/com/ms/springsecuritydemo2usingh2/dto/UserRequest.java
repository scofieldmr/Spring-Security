package com.ms.springsecuritydemo2usingh2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {

    @NotBlank(message = "Username Required.")
    private String username;

    @NotBlank(message = "Password Required.")
    private String password;

    @NotBlank(message = "Email Required.")
    private String email;


    private String role;
}
