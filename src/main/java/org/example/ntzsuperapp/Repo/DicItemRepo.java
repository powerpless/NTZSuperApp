package org.example.ntzsuperapp.Repo;


import jakarta.persistence.Entity;
import org.example.ntzsuperapp.Entity.DicItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DicItemRepo extends JpaRepository<DicItem, Long> {
    List<DicItem> findAllDicItemsByHasBeenDeletedFalseAndItemCatalogId(Long itemCatalogId);
}
