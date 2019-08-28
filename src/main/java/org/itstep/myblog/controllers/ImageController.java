package org.itstep.myblog.controllers;

import lombok.RequiredArgsConstructor;
import org.itstep.myblog.entities.Image;
import org.itstep.myblog.repository.ImageRepository;
import org.itstep.myblog.services.ImageFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final String BASE_IMG_PATH = "images";
    private final ImageFileService imageFileCreate;

    @PostMapping("/api/image/addImages")
    public String addImages(
            @RequestParam("image") MultipartFile file, HttpSession session)
            throws IOException {
        if (file.isEmpty ()){
            session.setAttribute("error","Чтобы загрузить фаил в галерею, для начала его нужно выбрать!");
            return "redirect:/addImages";
        }
        else {
            Image image = new Image ();
            image.setImage (imageFileCreate.fileCreate (file));
            imageRepository.save (image);
        }
        return "redirect:/addImages";
    }
    @PostMapping("/api/image/delImage")
    public String delImage(Long id,HttpSession session){
        Image image = imageRepository.getById (id);
        String path = image.getImage ();
        imageFileCreate.delImageFile(path);
        imageRepository.deleteById (id);
        return "redirect:/";
    }
    @GetMapping("images/{image}")
    public ResponseEntity<byte[]> getImage(@PathVariable String image) throws Exception {
         File f = new File (BASE_IMG_PATH+"/"+ image);
        FileInputStream fis = new FileInputStream (f);
        HttpHeaders headers =  new HttpHeaders ();
        headers.set ("Content-Type", Files.probeContentType (f.toPath ()));
        byte[] imageBytes = fis.readAllBytes();
        fis.close ();
        return new ResponseEntity<> (
                imageBytes,
                headers,
                HttpStatus.OK
        );
    }
}
