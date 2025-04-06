package com.BlogApp.demo.demo.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(String imagePath, MultipartFile image) throws IOException;
}
