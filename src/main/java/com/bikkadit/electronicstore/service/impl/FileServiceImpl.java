package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.exception.BadApiRequestException;
import com.bikkadit.electronicstore.service.FileServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileServiceI {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
      String originalFilename=file.getOriginalFilename();

      logger.info("filename :{}",originalFilename);
      String filename= UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=filename + extension;
        String fullPathWithName=path + File.separator+fileNameWithExtension;
       logger.info("full image path :{}",fullPathWithName);
        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg"))
        {
            //file save
            logger.info("file extension is ;{}",extension);
            File folder=new File(path);

            if(!folder.exists()){
                //create folder
               folder.mkdirs();
            }
            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithName));
            return fileNameWithExtension;

        }else{
            throw new BadApiRequestException("File with this"+extension +"not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath= path +name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
