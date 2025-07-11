package org.example.ntzsuperapp.Controllers;



import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.ItemToCreateDTO;
import org.example.ntzsuperapp.DTO.ItemToUpdateDTO;
import org.example.ntzsuperapp.Entity.DicItem;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Services.DicItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dic-items")
@RequiredArgsConstructor
public class DicItemController {
    private final DicItemService dicItemService;

    @GetMapping("/{id}")
    public ResponseEntity<DicItem> getDicItemById(@PathVariable Long id){
        return ResponseEntity.ok(dicItemService.getItemById(id));
    }
    @PostMapping("/me")
    public ResponseEntity<DicItem> addDicItem(@RequestBody ItemToCreateDTO dto){
        DicItem dicItem = dicItemService.addItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dicItem);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDicItem(@PathVariable Long id){
        dicItemService.removeItem(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<DicItem> updateDicIteam(@PathVariable Long id, @RequestBody ItemToUpdateDTO dto){
        return ResponseEntity.ok(dicItemService.updateItem(id, dto));
    }
    @GetMapping("/me")
    public ResponseEntity<List<DicItem>> getMyDicItems() {
        return ResponseEntity.ok(dicItemService.getMyItems());
    }
}
