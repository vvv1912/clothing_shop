package com.shop.clothing.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface IFileService {
    String uploadFile(MultipartFile file);
//    void deleteFiles(Collection<String> urls);
    void deleteFile(String url);

}
