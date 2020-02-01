package guru.springframework.webmvcrecipes.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageService {
    public void saveImageFile(Long id, MultipartFile imageFile);
}
