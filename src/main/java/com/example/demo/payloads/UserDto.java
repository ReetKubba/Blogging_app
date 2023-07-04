package com.example.demo.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Username must be of min 4 charecters")
    private String name;
    @Email(message = "Email Address not valid !!")
    private String email;
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 charecters")
    private String password;
    @NonNull
    private String about;
    private Set<RolesDto> roles = new HashSet<>();
}
