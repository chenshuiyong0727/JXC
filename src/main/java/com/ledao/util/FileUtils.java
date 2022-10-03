package com.ledao.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String uploadImage(String path, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        if(!fileName.matches("^.*(jpg|png|gif|PNG|webp|jpeg)$")){
            return null;
        }
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        if(height == 0 || width ==0){
            return null;
        }

        String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String picPath="/images/"+ dateDir.replace("/", "");
        String localPath = path +picPath;

        File picFile = new File(localPath);
        if(!picFile.exists()){
            picFile.mkdirs();
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String realPath = localPath + "/" + uuid + fileType;
        file.transferTo(new File(realPath));
        return picPath+ "/" + uuid + fileType;

    }
}

