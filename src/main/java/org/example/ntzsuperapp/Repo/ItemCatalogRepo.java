package org.example.ntzsuperapp.Repo;


import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCatalogRepo extends JpaRepository<ItemCatalog, Long> {
    List<ItemCatalog> findItemCatalogsByCategoryId(Long categoryId);
}
