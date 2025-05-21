package org.example.ntzsuperapp.Services;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.ItemCatalogToCreateDTO;
import org.example.ntzsuperapp.DTO.ItemCatalogToUpdateDTO;
import org.example.ntzsuperapp.Entity.Category;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.ItemCatalogRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCatalogService {
    private final ItemCatalogRepo itemCatalogRepo;
    private final CategoryService categoryService;
    private final UserRepo userRepo;

    public List<ItemCatalog> getAllCatalogs() {
        return itemCatalogRepo.findAll();
    }

    public List<ItemCatalog> getAllCatalogsByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));
        return itemCatalogRepo.findAllByCatalogOwner_id(owner.getId());
    }

    public ItemCatalog getById(Long id) {
        return itemCatalogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));
    }
    public ItemCatalog createItemCatalog(ItemCatalogToCreateDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));

        Category category = categoryService.getCategoryById(dto.getCategoryId());

        ItemCatalog itemCatalog = new ItemCatalog();
        itemCatalog.setRuName(dto.getRuName());
        itemCatalog.setEngName(dto.getEngName());
        itemCatalog.setPrice(dto.getPrice());
        itemCatalog.setColor(dto.getColor());
        itemCatalog.setWeight(dto.getWeight());
        itemCatalog.setCategory(category);
        itemCatalog.setCatalogOwner(owner);
        return itemCatalogRepo.save(itemCatalog);
    }

    public ItemCatalog updateItemCatalog(Long id, ItemCatalogToUpdateDTO dto){
        ItemCatalog itemCatalog = itemCatalogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item catalog not found" + id));

        itemCatalog.setRuName(dto.getRuName());
        itemCatalog.setEngName(dto.getEngName());
        itemCatalog.setPrice(dto.getPrice());
        itemCatalog.setColor(dto.getColor());
        itemCatalog.setWeight(dto.getWeight());

        if (dto.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(dto.getCategoryId());
            itemCatalog.setCategory(category);
        }

        return itemCatalogRepo.save(itemCatalog);
    }

    public void delete(Long id) {
        itemCatalogRepo.deleteById(id);
    }
}
