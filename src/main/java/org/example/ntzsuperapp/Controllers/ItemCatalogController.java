package org.example.ntzsuperapp.Controllers;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.ItemCatalogToCreateDTO;
import org.example.ntzsuperapp.DTO.ItemCatalogToUpdateDTO;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Services.ItemCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-catalog")
@RequiredArgsConstructor
public class ItemCatalogController {
    private final ItemCatalogService itemCatalogService;

    @GetMapping
    public ResponseEntity<List<ItemCatalog>> getAllItemCatalogs(){
        return ResponseEntity.ok(itemCatalogService.getAllCatalogs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ItemCatalog> getItemCatalogById(@PathVariable Long id){
        return ResponseEntity.ok(itemCatalogService.getById(id));
    }
    @PostMapping
    public ResponseEntity<ItemCatalog> createItemCatalog(@RequestBody ItemCatalogToCreateDTO dto){
        return ResponseEntity.ok(itemCatalogService.createItemCatalog(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ItemCatalog> updateItemCatalog(@PathVariable Long id, @RequestBody ItemCatalogToUpdateDTO dto){
        return ResponseEntity.ok(itemCatalogService.updateItemCatalog(id, dto));
    }
}
