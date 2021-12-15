package com.maxel.cursomc.service;

import com.amazonaws.AmazonClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3Client;
    @Value("${s3.bucket}")
    private String bucketName;

    public void uplodaFile(String localFilePAth) {
        try {
            File file = new File(localFilePAth);
            s3Client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
        }
        catch(AmazonServiceException e) {
            LOG.info("Amazon Exception: " + e.getMessage());
            LOG.info("Status code: " + e.getErrorCode());
        }
        catch(AmazonClientException e) {
            LOG.info("Amazon Client Exception: " + e.getMessage());
        }
    }
}
