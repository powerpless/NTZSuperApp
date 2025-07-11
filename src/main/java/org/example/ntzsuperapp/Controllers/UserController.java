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



    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUserByJwt(Authentication authentication){
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


    @PutMapping("/update")
    public ResponseEntity<String> changePersonalData(Authentication authentication, @RequestBody UsernameUpdateDTO request) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user.getPerson() == null) {
            throw new RuntimeException("User does not have a linked person");
        }

        user.getPerson().setNickName(request.getNewNickname());

        personRepo.save(user.getPerson());
        userRepo.save(user);

        return ResponseEntity.ok("Nickname was updated");
    }

    @PostMapping("/avatar")
    public ResponseEntity<FileDescriptor> uploadUserAvatar(
            @RequestBody FileDTO fileDTO,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            FileDescriptor savedAvatar = userService.saveAvatarForUser(username, fileDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAvatar);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
