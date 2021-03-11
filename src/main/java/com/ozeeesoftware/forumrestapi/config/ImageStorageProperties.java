package com.ozeeesoftware.forumrestapi.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class ImageStorageProperties {

    private String uploadDir;

    public ImageStorageProperties() {
    }

    public ImageStorageProperties(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
