package org.example.ntzsuperapp.Repo;

import org.example.ntzsuperapp.Entity.FileDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDescriptorRepo extends JpaRepository<FileDescriptor, Long> {
}
