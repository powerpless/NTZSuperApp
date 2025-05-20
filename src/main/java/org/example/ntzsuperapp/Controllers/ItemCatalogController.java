package org.example.ntzsuperapp.Controllers;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.ItemCatalogToCreateDTO;
import org.example.ntzsuperapp.DTO.ItemCatalogToUpdateDTO;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Services.ItemCatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-catalogs")
@RequiredArgsConstructor
public class ItemCatalogController {
    private final ItemCatalogService itemCatalogService;

    @GetMapping
    public ResponseEntity<List<ItemCatalog>> getAllItemCatalogs(){
        return ResponseEntity.ok(itemCatalogService.getAllCatalogs());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ItemCatalog>> getCatalogsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(itemCatalogService.getAllCatalogsByUser(userId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ItemCatalog> getItemCatalogById(@PathVariable Long id){
        return ResponseEntity.ok(itemCatalogService.getById(id));
    }
    @PostMapping
    public ResponseEntity<ItemCatalog> createItemCatalog(@RequestBody ItemCatalogToCreateDTO dto){
        ItemCatalog created = itemCatalogService.createItemCatalog(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ItemCatalog> updateItemCatalog(@PathVariable Long id, @RequestBody ItemCatalogToUpdateDTO dto){
        return ResponseEntity.ok(itemCatalogService.updateItemCatalog(id, dto));
    }
}
