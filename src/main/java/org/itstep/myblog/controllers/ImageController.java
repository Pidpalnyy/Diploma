package org.itstep.myblog.controllers;

import lombok.RequiredArgsConstructor;
import org.itstep.myblog.entities.Image;
import org.itstep.myblog.repository.CategoryRepository;
import org.itstep.myblog.repository.ImageRepository;
import org.itstep.myblog.repository.ProductRepository;
import org.itstep.myblog.repository.UsersRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final String BASE_IMG_PATH = "images";

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
            File uploadDir = new File (BASE_IMG_PATH);
            if (!uploadDir.exists ()){
                uploadDir.mkdir ();
            }
            String uuidFile = UUID.randomUUID().toString();

            String[] nameParts = file.getContentType ().split ("/");
            String resultFileName = uuidFile + "." + nameParts[nameParts.length-1];

            file.getBytes();
            File f = new File (BASE_IMG_PATH+"/"+ resultFileName);
            FileOutputStream fos= new FileOutputStream (f);
            fos.write (file.getBytes());
            image.setImage (resultFileName);

            imageRepository.save (image);
        }


        return "redirect:/addImages";
    }

    @PostMapping("/api/image/delImage")
    public String delImage(Long id,HttpSession session){
        Image image = imageRepository.getById (id);
        String path = image.getImage ();
        delImageFile(path);

        imageRepository.deleteById (id);

        System.out.println (imageRepository.findAll ());


        return "redirect:/";
    }

    private void delImageFile(String path) {
        final File file = new File (BASE_IMG_PATH+"/"+path);
        if(file.delete ()){
            System.out.println(BASE_IMG_PATH + "/"+ path + " - файл удален");
        }else System.out.println(BASE_IMG_PATH + "/"+ path + " - фаил удалить не получилось");
    }


    @GetMapping("images/{image}")
    public ResponseEntity<byte[]> getImage(@PathVariable String image) throws Exception {
         File f = new File (BASE_IMG_PATH+"/"+ image);
        FileInputStream fis = new FileInputStream (f);

        HttpHeaders headers =  new HttpHeaders ();
        headers.set ("Content-Type", Files.probeContentType (f.toPath ()));

        return new ResponseEntity<> (
                fis.readAllBytes(),
                headers,
                HttpStatus.OK
        );
    }




}
