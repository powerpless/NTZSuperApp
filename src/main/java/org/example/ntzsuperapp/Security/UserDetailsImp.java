package org.example.ntzsuperapp.Security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.ntzsuperapp.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsImp implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;

    public static UserDetailsImp build(User user){
        return new UserDetailsImp(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
