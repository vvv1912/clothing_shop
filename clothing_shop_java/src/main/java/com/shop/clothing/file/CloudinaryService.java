package com.shop.clothing.file;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class CloudinaryService implements IFileService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(CloudinaryConfig cloudinaryConfig) {
        this.cloudinary = new Cloudinary(cloudinaryConfig.getConfig());
    }

    public String uploadFile(MultipartFile file) {
        try {
            var fileName = file.getOriginalFilename();
            assert fileName != null;
            var newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            var params = ObjectUtils.asMap(
                    "public_id", "clothing/" + newFileName,
                    "overwrite", true,
                    "resource_type", "image"
            );
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
//https://res.cloudinary.com/dkvga054t/image/upload/v1696932430/clothing/2a8ca9a4-1c8b-4fed-963a-fbdb5e2aa477.jpg.jpg
    @Override
    public void deleteFile(String url) {
        try {
            var publicId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            cloudinary.uploader().destroy("clothing/" + publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
