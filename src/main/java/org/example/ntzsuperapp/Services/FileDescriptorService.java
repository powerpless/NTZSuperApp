package org.example.ntzsuperapp.Services;

import org.example.ntzsuperapp.DTO.FileDTO;
import org.example.ntzsuperapp.Entity.FileDescriptor;
import org.example.ntzsuperapp.Repo.FileDescriptorRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileDescriptorService {

    private final FileDescriptorRepo fileDescriptorRepo;

    public FileDescriptorService(FileDescriptorRepo fileDescriptorRepo) {
        this.fileDescriptorRepo = fileDescriptorRepo;
    }


    public FileDescriptor saveFile(FileDTO file) throws IOException {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setName(file.getName());
        fileDescriptor.setSize(file.getSize());
        fileDescriptor.setType(file.getType());
        fileDescriptor.setBytes(file.getBytes());
        return fileDescriptorRepo.save(fileDescriptor);
    }

    public Optional<FileDescriptor> getFile(Long id) {
        return fileDescriptorRepo.findById(id);
    }
}
