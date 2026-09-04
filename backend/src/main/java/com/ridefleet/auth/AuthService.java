package com.ridefleet.auth;

import com.ridefleet.common.BadRequestException;
import com.ridefleet.customer.CustomerProfile;
import com.ridefleet.customer.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        return new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), roles);
    }

    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getFullName(),
                registerRequest.getPhoneNumber()
        );

        Set<Role> roles = new HashSet<>();
        String roleStr = registerRequest.getRole() != null ? registerRequest.getRole().toUpperCase() : "CUSTOMER";
        Role userRole = roleRepository.findByName(roleStr)
                .orElseGet(() -> roleRepository.save(new Role(roleStr, "User role: " + roleStr)));
        roles.add(userRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // If registering as customer, create CustomerProfile
        if (roleStr.equals("CUSTOMER")) {
            CustomerProfile profile = new CustomerProfile();
            profile.setUser(savedUser);
            profile.setDriverLicenseNumber(registerRequest.getDriverLicenseNumber() != null ? 
                    registerRequest.getDriverLicenseNumber() : "DL-PENDING-" + System.currentTimeMillis());
            customerProfileRepository.save(profile);
        }

        return savedUser;
    }
}
