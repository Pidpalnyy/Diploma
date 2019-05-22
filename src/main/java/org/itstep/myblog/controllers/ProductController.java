package org.itstep.myblog.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.itstep.myblog.dto.GetProductsResponse;
import org.itstep.myblog.dto.ProductDTO;
import org.itstep.myblog.entities.Category;
import org.itstep.myblog.entities.Product;
import org.itstep.myblog.entities.User;
import org.itstep.myblog.repository.CategoryRepository;
import org.itstep.myblog.repository.ProductRepository;
import org.itstep.myblog.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final String BASE_IMG_PATH = "images";

    Gson gson = new Gson ();


    @GetMapping("/api/product/all")
    public GetProductsResponse getProduct() throws IOException, ServletException {

        List<Product> products = productRepository.findAll ();

        List<ProductDTO> dtoList = products.stream ().map(p->new ProductDTO ()).collect(Collectors.toList());
        GetProductsResponse response = new GetProductsResponse ("ok","",dtoList);
        return response;
    }

    @PostMapping("/api/product/addProduct")
    public String addProduct(
            @RequestParam("page") MultipartFile file,
            String category,
            String name,
            String text,
            Double quantity,
            Double price,
            HttpSession session)
            throws IOException {

        Product product = new Product ();

        if (name.length ()!=0){
            product.setName (name);
        }
        if (price!=null){
            product.setPrice (price);
        }
        if (quantity!=null){
            product.setQuantity (quantity);
        }
        if (text.length ()!=0){
            product.setText (text);
        }
        if (file.isEmpty ()){
            System.out.println (file);
        }
        else {
            File uploadDir = new File (BASE_IMG_PATH);
            if (!uploadDir.exists ()){
                uploadDir.mkdir ();
            }
            String uuidFile = UUID.randomUUID ().toString ();

            String[] nameParts = file.getContentType ().split ("/");
            String resultFileName = uuidFile + "." + nameParts[nameParts.length-1];

            file.getBytes();
            File f = new File (BASE_IMG_PATH+"/"+ resultFileName);
            FileOutputStream fos= new FileOutputStream (f);
            fos.write (file.getBytes());
            product.setPage (resultFileName);

        }

        Category c = categoryRepository.getByName(category);
        product.setCategory (c);


        productRepository.save (product);

        return "redirect:/addProduct";
    }

}
