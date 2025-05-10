package com.ecommerce.shoppingCart.service.image;

import com.ecommerce.shoppingCart.dto.ImageDto;
import com.ecommerce.shoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface iImageService {

    Image getImageById(Long id);
    void  deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
