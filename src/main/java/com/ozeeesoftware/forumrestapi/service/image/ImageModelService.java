package com.ozeeesoftware.forumrestapi.service.image;

import com.ozeeesoftware.forumrestapi.config.ImageStorageProperties;
import com.ozeeesoftware.forumrestapi.exception.FileNotFoundException;
import com.ozeeesoftware.forumrestapi.model.image.ImageModel;
import com.ozeeesoftware.forumrestapi.model.user.User;
import com.ozeeesoftware.forumrestapi.repository.ImageModelRepository;
import com.ozeeesoftware.forumrestapi.repository.PostRepository;
import com.ozeeesoftware.forumrestapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ImageModelService implements ImageModelServiceImpl{

    private final Path imageStorageLocation;


    @Autowired
    private ImageModelRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    public ImageModelService(ImageStorageProperties imageStorageProperties) {
        String imageUploadPath = imageStorageProperties.getUploadDir();
        this.imageStorageLocation = Paths.get(imageUploadPath)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.imageStorageLocation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<Object> storeImage(MultipartFile image){
        String randomFileName= UUID.randomUUID().toString();
        String extension="."+ FilenameUtils.getExtension(image.getOriginalFilename());
        String newFileName=randomFileName+extension;
        String finalFileName= StringUtils.cleanPath(newFileName);

        try {
            if(finalFileName.contains("..")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

            if(!extension.matches(".png|.jpeg|.jpg")) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            File folder = new File(String.valueOf(this.imageStorageLocation));


            checkFolderExists(finalFileName, folder);
            Path targetLocation = this.imageStorageLocation.resolve(finalFileName);
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = "/api/files/downloadFile/" + finalFileName;

            if (extension.matches(".png|.jpeg|.jpg")) {
                ImageModel tmpFile = new ImageModel();
                tmpFile.setName(finalFileName);
                tmpFile.setUrl(fileDownloadUri);
                return new ResponseEntity<>(imageRepository.save(tmpFile), HttpStatus.OK);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void checkFolderExists(String fileName, File folder) {
        if (!folder.exists()) {
            if (folder.mkdir()) {
                log.info("Assets folder created for fileName: {} ", fileName);
            }
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        log.info("loadImageAsResource request came ");
        try {
            Path targetLocation = this.imageStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found "+fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }


    @Override
    public ResponseEntity<Object> addProfileImage(MultipartFile image, long userId){
        String randomFileName= UUID.randomUUID().toString();
        String extension="."+ FilenameUtils.getExtension(image.getOriginalFilename());
        String newFileName=randomFileName+extension;
        String finalFileName= StringUtils.cleanPath(newFileName);

        try {
            if(finalFileName.contains("..")) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

            if(!extension.matches(".png|.jpeg|.jpg")) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            File folder = new File(String.valueOf(this.imageStorageLocation));


            checkFolderExists(finalFileName, folder);
            Path targetLocation = this.imageStorageLocation.resolve(finalFileName);
            Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = "/api/files/downloadFile/" + finalFileName;

            if (extension.matches(".png|.jpeg|.jpg")) {
                ImageModel tmpFile = new ImageModel();
                tmpFile.setName(finalFileName);
                tmpFile.setUrl(fileDownloadUri);
                Optional<User> existingUser=userRepository.findById(userId);
                existingUser.get().setProfileImage(tmpFile);
                return new ResponseEntity<>(imageRepository.save(tmpFile), HttpStatus.OK);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }




}
