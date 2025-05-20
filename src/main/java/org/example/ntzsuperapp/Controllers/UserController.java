package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.DTO.AvatarUploadResponse;
import org.example.ntzsuperapp.DTO.UsernameUpdateDTO;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.PersonRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.example.ntzsuperapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @GetMapping("/me/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/jwt/me")
    public ResponseEntity<User> getCurrentUserByJwt(Authentication authentication){
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> changePersonalData(@PathVariable Long id, @RequestBody UsernameUpdateDTO request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPerson() == null) {
            throw new RuntimeException("User does not have a linked person");
        }

        user.getPerson().setNickName(request.getNewNickname());

        personRepo.save(user.getPerson());
        userRepo.save(user);

        return ResponseEntity.ok("Nickname was updated");
    }

    @PutMapping("/users/avatar")
    public ResponseEntity<AvatarUploadResponse> updateAvatar(@RequestParam("avatar")MultipartFile avatarFile, Authentication authentication){
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AvatarUploadResponse("User not found", null));
        }

        if (!avatarFile.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String filename = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
        Path uploadPath = Paths.get("uploads").resolve(filename);

        try {
            Files.createDirectories(uploadPath.getParent());
            avatarFile.transferTo(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save avatar", e);
        }

        user.getPerson().setAvatarUrl("/uploads/" + filename);
        userRepo.save(user);

        return ResponseEntity.ok(new AvatarUploadResponse(
                "Avatar updated successfully", "/uploads/" + filename
        ));
    }
}
