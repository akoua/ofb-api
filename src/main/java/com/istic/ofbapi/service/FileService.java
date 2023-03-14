package com.istic.ofbapi.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void init();

    String uploadFile(MultipartFile file);

    Resource downloadFile(String fileName);
}
