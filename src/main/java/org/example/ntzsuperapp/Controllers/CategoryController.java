package org.example.ntzsuperapp.Controllers;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.CategoryToCreateDTO;
import org.example.ntzsuperapp.DTO.CategoryToUpdateDTO;
import org.example.ntzsuperapp.Entity.Category;
import org.example.ntzsuperapp.Services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryToCreateDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(dto));
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryToUpdateDTO dto){
        return ResponseEntity.ok(categoryService.updateCategory(dto, id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
