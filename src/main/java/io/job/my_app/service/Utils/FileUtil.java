package io.job.my_app.service.Utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUtil {

    public static boolean isPDF(MultipartFile file) {
        return file.getContentType().equals("application/pdf");
    }

    public static String saveFile(MultipartFile file, String targetLocation) throws IOException {
        file.transferTo(new java.io.File(targetLocation));
        return targetLocation;
    }
}
