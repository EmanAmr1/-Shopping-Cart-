package com.ecommerce.shoppingCart.service.image;

import com.ecommerce.shoppingCart.dto.ImageDto;
import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.Image;
import com.ecommerce.shoppingCart.model.Product;
import com.ecommerce.shoppingCart.repository.imageRepository;
import com.ecommerce.shoppingCart.service.product.productService;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class imageService implements iImageService {

    private final imageRepository imageRepo;
    private final productService prodService;

    public imageService(imageRepository imageRepo, productService prodService) {
        this.imageRepo = imageRepo;
        this.prodService = prodService;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Image Not Found !");
                }
        );
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,
                () -> {
                    throw new ResourceNotFoundException("Image Not Found !");
                });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product p = prodService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(p);

                Image savedImage= imageRepo.save(image); // to get id after saving into db


                String downloadUrl= "/api/v1/images/download/" + savedImage.getId(); //get id of saved image
                savedImage.setDownloadUrl(downloadUrl);
                savedImage=imageRepo.save(savedImage);


                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image i = getImageById(imageId);
        try {
            i.setFileName(file.getOriginalFilename());
            i.setFileType(file.getContentType());
            i.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(i);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
