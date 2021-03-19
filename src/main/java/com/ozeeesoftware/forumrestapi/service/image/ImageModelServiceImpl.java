package com.ozeeesoftware.forumrestapi.service.image;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageModelServiceImpl {

    ResponseEntity storeImage(MultipartFile image);

    Resource loadFileAsResource(String fileName);

    ResponseEntity<Object> addProfileImage(MultipartFile image, long userId);
}
