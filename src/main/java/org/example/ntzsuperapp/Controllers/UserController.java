package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.DTO.AvatarUploadResponse;
import org.example.ntzsuperapp.DTO.FileDTO;
import org.example.ntzsuperapp.DTO.UsernameUpdateDTO;
import org.example.ntzsuperapp.Entity.FileDescriptor;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.PersonRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.example.ntzsuperapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Optional;
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

    @PostMapping("/me/avatar/upload")
    public ResponseEntity<?> uploadAvatar(@RequestBody FileDTO file, Authentication authentication) {
        String username = authentication.getName();

        if (!file.getType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("Можно загружать только изображения.");
        }

        try {
            FileDescriptor saved = userService.saveAvatarForUser(username, file);
            return ResponseEntity.ok("Аватар успешно загружен. ID файла: " + saved.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    @GetMapping("/me/avatar")
    public ResponseEntity<byte[]> downloadAvatar(Authentication authentication) {
        String username = authentication.getName();

        Optional<FileDescriptor> avatarOpt = userService.getAvatarForUser(username);
        if (avatarOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FileDescriptor avatar = avatarOpt.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + avatar.getName() + "\"")
                .contentType(MediaType.parseMediaType(avatar.getType()))
                .body(avatar.getBytes());
    }

}
