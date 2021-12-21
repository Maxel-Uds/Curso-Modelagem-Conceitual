package com.maxel.cursomc.service;

import com.maxel.cursomc.service.exceptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!"png".equals(extension) && !"jpg".equals(extension)) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }

        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            if("png".equals(extension)) {
                img = imgToJpg(img);
            }

            return img;
        } catch (IOException e) {
           throw new FileException("Erro ao ler arquivo");
        }
    }

    //Formata imagem
    public BufferedImage imgToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    //Nomeia imagem
    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(img, extension, byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    //Corta imagem
    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
        return Scalr.crop(sourceImg, (sourceImg.getWidth()/2 - min/2), (sourceImg.getHeight()/2 - min/2), min, min);
    }

    //Redimensiona imagem
    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
    }
}
