package org.example.ntzsuperapp.Services;

import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    private final UserRepo userRepo;

    public AbstractService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));
    }
}
