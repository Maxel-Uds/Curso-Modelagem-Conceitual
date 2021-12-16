package com.maxel.cursomc.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;
    @Value("${s3.bucket}")
    private String bucketName;

    public URI uplodaFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uplodaFile(inputStream, fileName, contentType);
        }
        catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload");
        }
    }

    public URI uplodaFile(InputStream inputStream, String fileName, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            s3Client.putObject(bucketName, fileName, inputStream, objectMetadata);

            return s3Client.getUrl(bucketName, fileName).toURI();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao converter a URL em URI");
        }
    }
}
