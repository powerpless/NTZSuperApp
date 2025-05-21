package org.example.ntzsuperapp.Services;


import lombok.RequiredArgsConstructor;
import org.example.ntzsuperapp.DTO.ItemToCreateDTO;
import org.example.ntzsuperapp.DTO.ItemToUpdateDTO;
import org.example.ntzsuperapp.Entity.*;
import org.example.ntzsuperapp.Repo.DicItemRepo;
import org.example.ntzsuperapp.Repo.FileDescriptorRepo;
import org.example.ntzsuperapp.Repo.ItemCatalogRepo;
import org.example.ntzsuperapp.Repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DicItemService {
    private final DicItemRepo dicItemRepo;
    private final UserRepo userRepo;
    private final ItemCatalogRepo itemCatalogRepo;
    private final FileDescriptorRepo fileDescriptorRepo;


    public DicItem addItem(ItemToCreateDTO item){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));
        ItemCatalog itemCatalog = itemCatalogRepo.findById(item.getItemCatalogId())
                .orElseThrow(() -> new RuntimeException("Item catalog not found" + item.getItemCatalogId()));


        List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/webp");

        if (item.getPhotoType() != null && item.getPhotoType().toLowerCase().equals("application/zip")) {
            throw new IllegalArgumentException("ZIP files are not allowed as images.");
        }
        if (item.getPhotoType() == null || !allowedTypes.contains(item.getPhotoType().toLowerCase())) {
            throw new IllegalArgumentException("Unsupported file type. Allowed types: JPEG, PNG, WEBP.");
        }

        // 15
        if (item.getPhotoBytes() == null || item.getPhotoBytes().length > 15 * 1024 * 1024) {
            throw new IllegalArgumentException("File is too large. Max allowed size is 15MB.");
        }


        FileDescriptor photo = null;
        if(item.getPhotoBytes() != null && item.getPhotoType()!= null){
            photo = new FileDescriptor();
            photo.setType(item.getPhotoType());
            photo.setBytes(item.getPhotoBytes());
            photo = fileDescriptorRepo.save(photo);
        }


        DicItem dicItem = new DicItem();
        dicItem.setName(item.getName());
        dicItem.setItemCatalog(itemCatalog);
        dicItem.setOwner(owner);
        dicItem.setPhoto(photo);

        if(item.getAttributes() != null){
            List<ItemAttribute> attributes = item.getAttributes().stream()
                    .map(dto -> {
                        ItemAttribute attribute = new ItemAttribute();
                        attribute.setKey(dto.getKey());
                        attribute.setValue(dto.getValue());
                        attribute.setDicItem(dicItem);
                        return attribute;
                    }).toList();
            dicItem.setAttributes(attributes);
        }
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

        if ("application/zip".equalsIgnoreCase(dto.getPhotoType())) {
            throw new IllegalArgumentException("ZIP files are not allowed as images.");
        }

        if (dto.getPhotoBytes() != null && dto.getPhotoType() != null) {
            List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/webp");

            if (!allowedTypes.contains(dto.getPhotoType().toLowerCase())) {
                throw new IllegalArgumentException("Unsupported file type. Allowed: JPEG, PNG, WEBP.");
            }

            if (dto.getPhotoBytes().length > 15 * 1024 * 1024) {
                throw new IllegalArgumentException("File is too large. Max allowed size is 15MB.");
            }

            FileDescriptor newPhoto = new FileDescriptor();
            newPhoto.setBytes(dto.getPhotoBytes());
            newPhoto.setType(dto.getPhotoType());
            newPhoto = fileDescriptorRepo.save(newPhoto);
            item.setPhoto(newPhoto);
        } else if (dto.getPhotoId() != null) {
            FileDescriptor existingPhoto = fileDescriptorRepo.findById(dto.getPhotoId())
                    .orElseThrow(() -> new RuntimeException("Photo not found with ID: " + dto.getPhotoId()));
            item.setPhoto(existingPhoto);
        }


        if (dto.getAttributes() != null) {
            item.getAttributes().clear();
            List<ItemAttribute> attributes = dto.getAttributes().stream().map(dtoAttr -> {
                ItemAttribute attr = new ItemAttribute();
                attr.setKey(dtoAttr.getKey());
                attr.setValue(dtoAttr.getValue());
                attr.setDicItem(item);
                return attr;
            }).toList();
            item.getAttributes().addAll(attributes);
        }

        return dicItemRepo.save(item);
    }

    public DicItem getItemById(Long id) {
        return dicItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }
    public List<DicItem> getMyItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        return dicItemRepo.findAllByOwnerAndHasBeenDeletedFalse(owner);
    }
}
