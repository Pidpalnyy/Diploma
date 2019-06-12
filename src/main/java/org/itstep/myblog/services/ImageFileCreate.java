package org.itstep.myblog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ImageFileCreate {
    private final String BASE_IMG_PATH = "images";

    public String fileCreate(MultipartFile file) throws IOException {
        File uploadDir = new File (BASE_IMG_PATH);
        if (!uploadDir.exists ()) {
            uploadDir.mkdir ();
        }
        String uuidFile = UUID.randomUUID ().toString ();
        String[] nameParts = file.getContentType ().split ("/");
        String resultFileName = uuidFile + "." + nameParts[nameParts.length - 1];
        file.getBytes ();
        File f = new File (BASE_IMG_PATH + "/" + resultFileName);
        FileOutputStream fos = new FileOutputStream (f);
        fos.write (file.getBytes ());
        return resultFileName;
    }
}
