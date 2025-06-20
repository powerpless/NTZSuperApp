package org.example.ntzsuperapp.Services;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.CategoryToCreateDTO;
import org.example.ntzsuperapp.DTO.CategoryToUpdateDTO;
import org.example.ntzsuperapp.Entity.Category;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.CategoryRepo;
import org.example.ntzsuperapp.Repo.ItemCatalogRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final ItemCatalogRepo itemCatalogRepo;

    public Category createCategory(CategoryToCreateDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));

        Category category = new Category();
        category.setRuName(dto.getRuName());
        category.setEngName(dto.getEngName());
        category.setCategoryOwner(owner);
        return categoryRepo.save(category);
    }
    public void deleteCategory(Long id){
        Category category = getCategoryById(id);

        List<ItemCatalog> catalogs = itemCatalogRepo.findAllByCategoryId(id);
        for(ItemCatalog catalog: catalogs){
            catalog.setCategory(null);
        }
        itemCatalogRepo.saveAll(catalogs);
        categoryRepo.delete(category);
    }

    public Category getCategoryById(Long id){
        return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found " + id));
    }
    public Category updateCategory(CategoryToUpdateDTO dto, Long id){
        Category category = getCategoryById(id);
        category.setRuName(dto.getRuName());
        category.setEngName(dto.getEngName());
        return categoryRepo.save(category);
    }
    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }

    public List<Category> getAllCategoriesByCurrentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepo.findUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found" + username));

        return categoryRepo.findAllCategoriesByCategoryOwnerId(user.getId());
    }
}
