package com.springsecurity.dto;

import com.springsecurity.entity.Roles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SignupRequest {

    @NotBlank(message = "Full Name Required. ")
    private String fullname;

    @NotBlank(message = "Email Required..")
    private String email;

    @NotBlank(message = "Username Required")
    private String username;

    @NotBlank(message = "Password Required")
    @Size(min = 5,max = 15, message = "Password should be in the length 5-15")
    private String password;

    @NotEmpty(message = "Roles should be mentioned..")
    private Set<String> roles;

}
