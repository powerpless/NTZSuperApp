package org.example.ntzsuperapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.ntzsuperapp.Entity.Role;
import org.example.ntzsuperapp.Entity.RoleName;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
