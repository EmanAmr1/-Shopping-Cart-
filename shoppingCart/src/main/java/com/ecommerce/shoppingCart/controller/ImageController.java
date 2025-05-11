package com.ecommerce.shoppingCart.controller;

import com.ecommerce.shoppingCart.dto.ImageDto;
import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.Image;
import com.ecommerce.shoppingCart.response.ApiResponse;
import com.ecommerce.shoppingCart.service.image.imageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final imageService imageservice;
    public ImageController(imageService imageservice) {
        this.imageservice = imageservice;
    }


    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImageById(@PathVariable Long imageId) {
        try {
            Image image = imageservice.getImageById(imageId);
            if (image != null) {
                imageservice.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("deleted success!", image));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage() ,null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("delete failed!", INTERNAL_SERVER_ERROR));
    }
    


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> addImage(@RequestBody List<MultipartFile> files,
                                                @RequestParam Long id) {
        try {
            List<ImageDto> imageDtos = imageservice.saveImage(files, id);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload failed!", e.getMessage()));
        }
    }


    @GetMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {

        Image i = imageservice.getImageById(imageId);

        ByteArrayResource resource = new ByteArrayResource(
                i.getImage().getBytes(1, (int) i.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(i.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "attachment; fileName=\"" + i.getFileName() + "\"")
                .body(resource);

    }


    @PutMapping("/image/{id}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestBody MultipartFile file, @PathVariable Long id) {
        try {
            Image image = imageservice.getImageById(id);
            if (image != null) {
                imageservice.updateImage(file, id);
                return ResponseEntity.ok(new ApiResponse("Updated success!", image));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage() ,null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));

    }


}
