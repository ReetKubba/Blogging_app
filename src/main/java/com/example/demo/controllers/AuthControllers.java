package com.example.demo.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Security.JwtTokenHelper;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.payloads.JwtAuthRequest;
import com.example.demo.payloads.JwtAuthResponse;
import com.example.demo.payloads.UserDto;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthControllers {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUser(mapper.map(userDetails, UserDto.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid username or password!!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registeredUser = userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping("/current-user/")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<>(mapper.map(user, UserDto.class), HttpStatus.OK);
    }
}
