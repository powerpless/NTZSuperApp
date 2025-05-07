package org.example.ntzsuperapp.Services;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.ItemToCreateDTO;
import org.example.ntzsuperapp.DTO.ItemToUpdateDTO;
import org.example.ntzsuperapp.Entity.DicItem;
import org.example.ntzsuperapp.Entity.ItemCatalog;
import org.example.ntzsuperapp.Entity.User;
import org.example.ntzsuperapp.Repo.DicItemRepo;
import org.example.ntzsuperapp.Repo.ItemCatalogRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DicItemService {
    private final DicItemRepo dicItemRepo;
    private final UserRepo userRepo;
    private final ItemCatalogRepo itemCatalogRepo;


    public DicItem addItem(ItemToCreateDTO item, Long userId){
        ItemCatalog itemCatalog = itemCatalogRepo.findById(item.getItemCatalogId())
                .orElseThrow(() -> new RuntimeException("Item catalog not found" + item.getItemCatalogId()));
        User owner = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found" + userId));
        DicItem dicItem = new DicItem();
        dicItem.setName(item.getName());
        dicItem.setPhotoUrl(item.getPhotoUrl());
        dicItem.setItemCatalog(itemCatalog);
        dicItem.setOwner(owner);
        return dicItemRepo.save(dicItem);
    }

    public void removeItem(Long id){
        DicItem item = dicItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item wasn't found"));

        item.setHasBeenDeleted(true);
        dicItemRepo.save(item);
    }

    public DicItem updateItem(Long id, ItemToUpdateDTO dto){
        DicItem item = dicItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item wasn't found"));

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Item can't be empty");
        }

        item.setName(dto.getName());
        item.setPhotoUrl(dto.getPhotoUrl());
        return dicItemRepo.save(item);
    }

    public DicItem getItemById(Long id) {
        return dicItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
