package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.Components.JwtCore;
import org.example.ntzsuperapp.DTO.Login;
import org.example.ntzsuperapp.DTO.SignInRequest;
import org.example.ntzsuperapp.DTO.SignUpRequest;
import org.example.ntzsuperapp.Entity.Person;
import org.example.ntzsuperapp.Entity.Role;
import org.example.ntzsuperapp.Entity.RoleName;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.RoleRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    private UserRepo userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;
    private RoleRepo roleRepository;

    @Autowired
    public void setUserRepository(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/register")
    ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        if(userRepository.existsUserByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        if(userRepository.existsUserByEmail(signUpRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different email");
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(userRole);

        Person person = new Person();
        person.setFirstName(signUpRequest.getFirstName());
        person.setLastName(signUpRequest.getLastName());
        person.setMiddleName(signUpRequest.getMiddleName());
        person.setNickName(signUpRequest.getNickName());

        user.setPerson(person);

        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return ResponseEntity.ok("Success!");
    }

    @PostMapping("/login")
    ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtCore.generateToken(authentication);
            long expTime =  jwtCore.getExpirationTime();
            String username = authentication.getName();

            return ResponseEntity.ok(new Login(true,jwt, expTime, username, "Login successful"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Login(false, null, 0, null, "Invalid username or password"));
        }

    }

    @Autowired
    public void setRoleRepository(RoleRepo roleRepository) {
        this.roleRepository = roleRepository;
    }
}
