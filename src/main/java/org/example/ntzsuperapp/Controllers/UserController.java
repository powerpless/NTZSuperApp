package org.example.ntzsuperapp.Controllers;

import org.example.ntzsuperapp.DTO.UsernameUpdateDTO;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.PersonRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.example.ntzsuperapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private UserRepo userRepo;


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

}
