package edu.mum.mpp.coursework.mppsurvey.controller;

import edu.mum.mpp.coursework.mppsurvey.entity.AppRole;
import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.exception.AppException;
import edu.mum.mpp.coursework.mppsurvey.model.*;
import edu.mum.mpp.coursework.mppsurvey.repository.RoleRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserRepository;
import edu.mum.mpp.coursework.mppsurvey.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        AppUser user = new AppUser(signUpRequest.getUsername(),signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<AppRole> roles = new HashSet<>();
        AppRole userRole = roleRepository.findByName(AppRoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        roles.add(userRole);
        if("admin".equals(signUpRequest.getUsername())){
            AppRole adminRole = roleRepository.findByName(AppRoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("User Role not set."));
            roles.add(adminRole);
        }
        user.setRoles(roles);

        AppUser result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
