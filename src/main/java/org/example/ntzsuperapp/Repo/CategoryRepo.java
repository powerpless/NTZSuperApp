package org.example.ntzsuperapp.Repo;


import org.example.ntzsuperapp.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
