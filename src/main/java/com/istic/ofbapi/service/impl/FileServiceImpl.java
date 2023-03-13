package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return fileName;
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path path = Paths.get(uploadDir + fileName);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
