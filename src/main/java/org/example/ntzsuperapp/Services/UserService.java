package org.example.ntzsuperapp.Services;

import org.example.ntzsuperapp.DTO.FileDTO;
import org.example.ntzsuperapp.Entity.FileDescriptor;
import org.example.ntzsuperapp.Entity.Person;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.example.ntzsuperapp.Security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileDescriptorService fileDescriptorService;

    public User getUserById(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUsername(String username){
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public FileDescriptor saveAvatarForUser(String username, FileDTO fileDTO) throws IOException {
        User user = findByUsername(username);
        Person person = user.getPerson();

        if (person == null) {
            throw new RuntimeException("Пользователь не привязан к персоне");
        }

        FileDescriptor savedFile = fileDescriptorService.saveFile(fileDTO);

        person.setAvatar(savedFile);
        userRepo.save(user);

        return savedFile;
    }

    public Optional<FileDescriptor> getAvatarForUser(String username) {
        User user = findByUsername(username);
        Person person = user.getPerson();

        if (person == null || person.getAvatar() == null) {
            return Optional.empty();
        }

        return Optional.of(person.getAvatar());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User '%s' not found", username)
                ));

        return UserDetailsImp.build(user);
    }
}
